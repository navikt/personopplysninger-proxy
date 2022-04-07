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
import no.nav.personopplysninger.proxy.config.NavPersonident
import no.nav.personopplysninger.proxy.tokenx.TokenxService
import java.util.*

fun Route.tpsProxyRouting(client: HttpClient, environment: Environment, tokenxService: TokenxService) {
    route("/person") {
        get {
            try {
                val callId = call.request.header(HttpHeaders.NavCallId) ?: UUID.randomUUID().toString()
                val tokenxToken = tokenxService.exchangeAuthToken(call.request, environment.medlTargetApp)

                val response: HttpResponse =
                    client.get(environment.tpsProxyUrl) {
                        header(HttpHeaders.Authorization, "Bearer ".plus(tokenxToken))
                        header(HttpHeaders.NavCallId, callId)
                        header(HttpHeaders.NavConsumerId, environment.consumerId)
                        header(HttpHeaders.NavPersonident, call.request.header(HttpHeaders.NavPersonident))
                    }

                val responseBody: JsonElement = response.receive()

                if (!response.status.isSuccess()) {
                    logger.warn("Kall til tps-proxy feilet med statuskode ${response.status}: $responseBody")
                    call.respond(HttpStatusCode.InternalServerError, "Kall til tps-proxy feilet")
                }
                call.respond(response.status, responseBody)
            } catch (e: Throwable) {
                logger.error("Teknisk feil ved kall til tps-proxy: ${e.message}", e)
            }
        }
    }
}
