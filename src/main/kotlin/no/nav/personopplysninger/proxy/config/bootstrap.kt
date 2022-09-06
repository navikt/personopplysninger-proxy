package no.nav.personopplysninger.proxy.config

import io.ktor.client.HttpClient
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.metrics.micrometer.MicrometerMetrics
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.request.httpMethod
import io.ktor.server.request.path
import io.ktor.server.routing.routing
import no.nav.personopplysninger.proxy.health.health
import no.nav.personopplysninger.proxy.routes.inst2Routing
import no.nav.personopplysninger.proxy.routes.medlRouting
import no.nav.personopplysninger.proxy.routes.tpsProxyRouting
import no.nav.tms.token.support.authentication.installer.installAuthenticators

fun Application.mainModule(appContext: ApplicationContext = ApplicationContext()) {
    val environment = Environment()

    install(DefaultHeaders)

    install(CORS) {
        allowHost(environment.corsAllowedOrigins)
        allowCredentials = true
        allowHeader(HttpHeaders.ContentType)
    }

    install(MicrometerMetrics) {
        registry = appContext.appMicrometerRegistry
    }

    install(ContentNegotiation) {
        json(jsonConfig())
    }

    install(CallLogging) {
        filter { call -> !call.request.path().contains("internal") }
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val path = call.request.path()
            "$status - $httpMethod $path"
        }
    }

    installAuthenticators {
        installTokenXAuth {
            setAsDefault = true
        }
    }

    routing {
        health(appContext.appMicrometerRegistry)
        authenticate {
            tpsProxyRouting(appContext.httpClient, environment, appContext.tokenxService)
            medlRouting(appContext.httpClient, environment, appContext.tokenxService)
            inst2Routing(appContext.httpClient, environment, appContext.tokenxService)
        }
    }

    configureShutdownHook(appContext.httpClient)
}

private fun Application.configureShutdownHook(httpClient: HttpClient) {
    environment.monitor.subscribe(ApplicationStopping) {
        httpClient.close()
    }
}
