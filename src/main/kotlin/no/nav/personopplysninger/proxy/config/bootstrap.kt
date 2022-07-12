package no.nav.personopplysninger.proxy.config

import io.ktor.application.Application
import io.ktor.application.ApplicationStopping
import io.ktor.application.install
import io.ktor.auth.authenticate
import io.ktor.client.HttpClient
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.http.HttpHeaders
import io.ktor.routing.routing
import io.ktor.serialization.json
import no.nav.personopplysninger.proxy.health.healthApi
import no.nav.personopplysninger.proxy.routes.inst2Routing
import no.nav.personopplysninger.proxy.routes.medlRouting
import no.nav.personopplysninger.proxy.routes.sporingsloggRouting
import no.nav.personopplysninger.proxy.routes.tpsProxyRouting
import no.nav.tms.token.support.authentication.installer.installAuthenticators

fun Application.mainModule(appContext: ApplicationContext = ApplicationContext()) {
    val environment = Environment()

    install(DefaultHeaders)

    install(CORS) {
        host(environment.corsAllowedOrigins)
        allowCredentials = true
        header(HttpHeaders.ContentType)
    }

    install(ContentNegotiation) {
        json(jsonConfig())
    }

    install(CallLogging)

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

private fun Application.configureShutdownHook(httpClient: HttpClient) {
    environment.monitor.subscribe(ApplicationStopping) {
        httpClient.close()
    }
}
