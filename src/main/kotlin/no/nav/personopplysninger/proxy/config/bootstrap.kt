package no.nav.personopplysninger.proxy.config

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.util.*
import no.nav.personopplysninger.proxy.health.healthApi
import no.nav.personopplysninger.proxy.routes.aaregRouting
import no.nav.personopplysninger.proxy.routes.eregRouting
import no.nav.personopplysninger.proxy.routes.kodeverkRouting
import no.nav.personopplysninger.proxy.routes.sporingsloggRouting
import no.nav.tms.token.support.authentication.installer.installAuthenticators

@KtorExperimentalAPI
fun Application.mainModule(appContext: ApplicationContext = ApplicationContext()) {
    val environment = Environment()

    install(DefaultHeaders)

    install(CORS) {
        host(environment.corsAllowedOrigins)
        allowCredentials = true
        header(HttpHeaders.ContentType)
    }

    install(ContentNegotiation) {
        json(jsonConfig(true))
    }

    installAuthenticators {
        installTokenXAuth {
            setAsDefault = true
        }
    }

    routing {
        healthApi(appContext.healthService)
        authenticate {
            kodeverkRouting(appContext.httpClient, environment)
            eregRouting(appContext.httpClient, environment)
            aaregRouting(appContext.httpClient, environment, appContext.stsConsumer)
            sporingsloggRouting(appContext.httpClient, environment)
        }
    }

    configureShutdownHook(appContext.httpClient)
}

private fun Application.configureShutdownHook(httpClient: HttpClient) {
    environment.monitor.subscribe(ApplicationStopping) {
        httpClient.close()
    }
}
