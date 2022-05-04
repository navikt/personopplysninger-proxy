package no.nav.personopplysninger.proxy.config

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.serialization.*
import no.nav.personopplysninger.proxy.health.healthApi
import no.nav.personopplysninger.proxy.routes.*
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

    installAuthenticators {
        installTokenXAuth {
            setAsDefault = true
        }
    }

    routing {
        healthApi(appContext.healthService)
        authenticate {
            eregRouting(appContext.httpClient, environment)
            norg2Routing(appContext.httpClient, environment)
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
