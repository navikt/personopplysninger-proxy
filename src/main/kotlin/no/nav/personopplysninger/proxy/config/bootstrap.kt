package no.nav.personopplysninger.proxy.config

import io.ktor.client.HttpClient
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.routing.routing
import no.nav.personopplysninger.proxy.health.healthApi
import no.nav.personopplysninger.proxy.routes.inst2Routing
import no.nav.personopplysninger.proxy.routes.medlRouting
import no.nav.personopplysninger.proxy.routes.sporingsloggRouting
import no.nav.personopplysninger.proxy.routes.tpsProxyRouting
import no.nav.tms.token.support.authentication.installer.AuthenticatorConfig

fun Application.mainModule(appContext: ApplicationContext = ApplicationContext()) {
    val environment = Environment()

    install(DefaultHeaders)

    install(CORS) {
        allowHost(environment.corsAllowedOrigins)
        allowCredentials = true
        allowHeader(HttpHeaders.ContentType)
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
        healthApi(appContext.healthService)
        authenticate {
            sporingsloggRouting(appContext.httpClient, environment)
            tpsProxyRouting(appContext.httpClient, environment, appContext.tokenxService)
            medlRouting(appContext.httpClient, environment, appContext.tokenxService)
            inst2Routing(appContext.httpClient, environment, appContext.tokenxService)
        }
    }

    configureShutdownHook(appContext.httpClient)
}

fun installAuthenticators(configure: AuthenticatorConfig.() -> Unit) {
    // TODO: Bruk ny versjon av tms-ktor-token-support etter migrering
}

private fun Application.configureShutdownHook(httpClient: HttpClient) {
    environment.monitor.subscribe(ApplicationStopping) {
        httpClient.close()
    }
}
