package no.nav.personopplysninger.proxy.routes

import io.ktor.application.call
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.request.header
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import kotlinx.serialization.json.JsonElement
import no.nav.personbruker.dittnav.common.logging.util.logger
import no.nav.personopplysninger.proxy.config.Environment
import no.nav.personopplysninger.proxy.config.NavCallId
import no.nav.personopplysninger.proxy.config.NavConsumerId
import no.nav.personopplysninger.proxy.config.NavConsumerToken
import java.util.*

fun Route.sporingsloggRouting(client: HttpClient, environment: Environment) {
    route("/sporingslogg") {
        get {
            try {
                val callId = call.request.header(HttpHeaders.NavCallId) ?: UUID.randomUUID().toString()

                val response: HttpResponse =
                    client.get(environment.sporingsloggUrl) {
                        header(HttpHeaders.Authorization, "Bearer ".plus(call.request.header(HttpHeaders.NavConsumerToken)))
                        header(HttpHeaders.NavCallId, callId)
                        header(HttpHeaders.NavConsumerId, environment.consumerId)
                    }

                val responseBody: JsonElement = response.receive()

                if (!response.status.isSuccess()) {
                    logger.warn("Kall til sporingslogg feilet med statuskode ${response.status}: $responseBody")
                    call.respond(HttpStatusCode.InternalServerError, "Kall til sporingslogg feilet")
                }
                call.respond(response.status, responseBody)
            } catch (e: Throwable) {
                logger.error("Teknisk feil ved kall til sporingslogg: ${e.message}", e)
                call.respond(HttpStatusCode.InternalServerError, "Teknisk feil ved kall til sporingslogg")
            }
        }
    }
}
