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
import kotlinx.serialization.json.JsonElement
import no.nav.personbruker.dittnav.common.logging.util.logger
import no.nav.personopplysninger.proxy.config.Environment
import no.nav.personopplysninger.proxy.config.NavCallId
import no.nav.personopplysninger.proxy.config.NavConsumerId
import no.nav.personopplysninger.proxy.config.NavConsumerToken
import java.util.*

private const val ORGNR = "orgnr"
private const val GYLDIG_DATO = "gyldigDato"

fun Route.eregRouting(client: HttpClient, environment: Environment) {
    route("/v1/organisasjon/{orgnr}/noekkelinfo") {
        get {
            try {
                val callId = call.request.header(HttpHeaders.NavCallId) ?: UUID.randomUUID().toString()
                val gyldigDato = call.request.queryParameters[GYLDIG_DATO]

                val response: HttpResponse =
                    client.get(environment.eregUrl + "/v1/organisasjon/${call.parameters[ORGNR]}/noekkelinfo") {
                        header(HttpHeaders.Authorization, "Bearer ".plus(call.request.header(HttpHeaders.NavConsumerToken)))
                        header(HttpHeaders.NavCallId, callId)
                        header(HttpHeaders.NavConsumerId, environment.consumerId)

                        if (!gyldigDato.isNullOrEmpty()) {
                            parameter(GYLDIG_DATO, gyldigDato)
                        }
                    }


                val responseBody: JsonElement = response.receive()

                if (!response.status.isSuccess()) {
                    logger.warn("Kall til ereg feilet med statuskode ${response.status}: $responseBody")
                    call.respond(HttpStatusCode.InternalServerError, "Kall til ereg feilet")
                }
                call.respond(response.status, responseBody)
            } catch (e: Throwable) {
                logger.error("Teknisk feil ved kall til ereg: ${e.message}", e)
            }
        }
    }
}
