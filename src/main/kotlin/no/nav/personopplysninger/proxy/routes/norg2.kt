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
import no.nav.personopplysninger.proxy.config.Enhetsnr
import no.nav.personopplysninger.proxy.config.Environment
import no.nav.personopplysninger.proxy.config.NavCallId
import no.nav.personopplysninger.proxy.config.NavConsumerId
import no.nav.personopplysninger.proxy.config.NavConsumerToken
import java.util.*

private const val GEOGRAFISK = "geografisk"
private const val ENHETSNR = "enhetsnr"

fun Route.norg2Routing(client: HttpClient, environment: Environment) {
    route("/enhet/navkontor/{geografisk}") {
        get {
            try {
                val callId = call.request.header(HttpHeaders.NavCallId) ?: UUID.randomUUID().toString()

                val response: HttpResponse =
                    client.get(environment.norg2Url + "/enhet/navkontor/${call.parameters[GEOGRAFISK]}") {
                        header(HttpHeaders.Authorization, "Bearer ".plus(call.request.header(HttpHeaders.NavConsumerToken)))
                        header(HttpHeaders.NavCallId, callId)
                        header(HttpHeaders.NavConsumerId, environment.consumerId)
                    }

                val responseBody: JsonElement = response.receive()

                if (!response.status.isSuccess()) {
                    logger.warn("Kall til norg2 feilet med statuskode ${response.status}: $responseBody")
                    call.respond(HttpStatusCode.InternalServerError, "Kall til norg2 feilet")
                }
                call.respond(response.status, responseBody)
            } catch (e: Throwable) {
                logger.error("Teknisk feil ved kall til norg2: ${e.message}", e)
                call.respond(HttpStatusCode.InternalServerError, "Teknisk feil ved kall til norg2")
            }
        }
    }

    route("/enhet/{enhetsnr}/kontaktinformasjon") {
        get {
            try {
                val callId = call.request.header(HttpHeaders.NavCallId) ?: UUID.randomUUID().toString()

                val response: HttpResponse =
                    client.get(environment.norg2Url + "/enhet/${call.parameters[ENHETSNR]}/kontaktinformasjon") {
                        header(HttpHeaders.Authorization, "Bearer ".plus(call.request.header(HttpHeaders.NavConsumerToken)))
                        header(HttpHeaders.NavCallId, callId)
                        header(HttpHeaders.NavConsumerId, environment.consumerId)
                        header(HttpHeaders.Enhetsnr, call.request.header(HttpHeaders.Enhetsnr))
                    }

                val responseBody: JsonElement = response.receive()

                if (!response.status.isSuccess()) {
                    logger.warn("Kall til norg2 feilet med statuskode ${response.status}: $responseBody")
                    call.respond(HttpStatusCode.InternalServerError, "Kall til norg2 feilet")
                }
                call.respond(response.status, responseBody)
            } catch (e: Throwable) {
                logger.error("Teknisk feil ved kall til norg2: ${e.message}", e)
                call.respond(HttpStatusCode.InternalServerError, "Teknisk feil ved kall til norg2")
            }
        }
    }
}
