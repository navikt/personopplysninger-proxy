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
import no.nav.personopplysninger.proxy.config.*
import java.util.*

fun Route.inst2Routing(client: HttpClient, environment: Environment) {
    route("/v1/person/innsyn") {
        get {
            try {
                val callId = call.request.header(HttpHeaders.NavCallId) ?: UUID.randomUUID().toString()

                val response: HttpResponse =
                    client.get(environment.inst2Url + "/v1/person/innsyn") {
                        header(HttpHeaders.Authorization, "Bearer ".plus(call.request.header(HttpHeaders.NavConsumerToken)))
                        header(HttpHeaders.NavCallId, callId)
                        header(HttpHeaders.NavConsumerId, environment.consumerId)
                        header(HttpHeaders.NavPersonident, call.request.header(HttpHeaders.NavPersonident))
                    }

                val responseBody: JsonElement = response.receive()

                if (!response.status.isSuccess()) {
                    logger.warn("Kall til inst2 feilet med statuskode ${response.status}: $responseBody")
                    call.respond(HttpStatusCode.InternalServerError, "Kall til inst2 feilet")
                }
                call.respond(response.status, responseBody)
            } catch (e: Throwable) {
                logger.error("Teknisk feil ved kall til inst2: ${e.message}", e)
            }
        }
    }
}
