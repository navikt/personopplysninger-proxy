package no.nav.personopplysninger.proxy.routes

import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.json.Json
import no.nav.personbruker.dittnav.common.logging.util.logger
import no.nav.personopplysninger.proxy.config.Environment
import no.nav.personopplysninger.proxy.config.NavCallId
import no.nav.personopplysninger.proxy.config.NavConsumerId
import java.util.*

const val SPRAAK = "spraak"
const val EKSKLUDER_UGYLDIGE = "ekskluderUgyldige"
const val NAVN = "navn"

fun Route.kodeverkRouting(client: HttpClient, environment: Environment) {
    route("/api/v1/kodeverk/{navn}/koder/betydninger") {
        get {
            try {
                val callId = call.request.header(HttpHeaders.NavCallId) ?: UUID.randomUUID().toString()

                val response: HttpResponse =
                    client.get(environment.kodeverkUrl + "/api/v1/kodeverk/${call.parameters[NAVN]}/koder/betydninger") {
                        parameter(SPRAAK, call.request.queryParameters[SPRAAK])
                        parameter(EKSKLUDER_UGYLDIGE, call.request.queryParameters[EKSKLUDER_UGYLDIGE])

                        header(HttpHeaders.NavCallId, callId)
                        header(HttpHeaders.NavConsumerId, environment.consumerId)
                    }

                val responseBody: Json = response.receive()

                if (!response.status.isSuccess()) {
                    logger.warn("Kall til kodeverk feilet med statuskode ${response.status}: $responseBody")
                    call.respond(HttpStatusCode.InternalServerError, "Kall til kodeverk feilet")
                }
                call.respond(response.status, responseBody)
            } catch (e: Throwable) {
                logger.error("Teknisk feil ved kall til kodeverk: ${e.message}", e)
            }
        }
    }
}
