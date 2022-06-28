package no.nav.personopplysninger.proxy.routes

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.server.application.call
import io.ktor.server.request.header
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import kotlinx.serialization.json.JsonElement
import no.nav.personbruker.dittnav.common.logging.util.logger
import no.nav.personopplysninger.proxy.config.Environment
import no.nav.personopplysninger.proxy.config.NavCallId
import no.nav.personopplysninger.proxy.config.NavConsumerId
import no.nav.personopplysninger.proxy.config.NavPersonident
import no.nav.personopplysninger.proxy.tokenx.TokenxService
import java.util.*

fun Route.medlRouting(client: HttpClient, environment: Environment, tokenxService: TokenxService) {
    route("/api/v1/innsyn/person") {
        get {
            try {
                val callId = call.request.header(HttpHeaders.NavCallId) ?: UUID.randomUUID().toString()
                val tokenxToken = tokenxService.exchangeAuthToken(call.request, environment.medlTargetApp)

                val response: HttpResponse =
                    client.get(environment.medlUrl + "/api/v1/innsyn/person") {
                        header(
                            HttpHeaders.Authorization,
                            "Bearer ".plus(tokenxToken)
                        )
                        header(HttpHeaders.NavCallId, callId)
                        header(HttpHeaders.NavConsumerId, environment.consumerId)
                        header(HttpHeaders.NavPersonident, call.request.header(HttpHeaders.NavPersonident))
                    }

                val responseBody: JsonElement = response.body()

                if (!response.status.isSuccess()) {
                    logger.warn("Kall til medl feilet med statuskode ${response.status}: $responseBody")
                    call.respond(HttpStatusCode.InternalServerError, "Kall til medl feilet")
                }
                call.respond(response.status, responseBody)
            } catch (e: Throwable) {
                logger.error("Teknisk feil ved kall til medl: ${e.message}", e)
                call.respond(HttpStatusCode.InternalServerError, "Teknisk feil ved kall til medl")
            }
        }
    }
}
