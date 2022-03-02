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
import no.nav.personopplysninger.proxy.sts.STSConsumer
import java.util.*

private const val ENDRING_ID = "endringId"

fun Route.personmottak(client: HttpClient, environment: Environment, stsConsumer: STSConsumer) {
    route("/api/v1") {
        post("/endringer") {
            try {
                val callId = call.request.header(HttpHeaders.NavCallId) ?: UUID.randomUUID().toString()
                val requestBody = call.receive<JsonElement>()
                val stsToken = stsConsumer.getToken(environment.srvpersonopplysningerUsername, environment.srvpersonopplysningerPassword)

                val response: HttpResponse =
                    client.post(path = environment.personMottakUrl + "/api/v1/endringer", body = requestBody) {
                        contentType(ContentType.Application.Json)

                        header(HttpHeaders.Authorization, "Bearer ".plus(call.request.header(HttpHeaders.NavConsumerToken)))
                        header(HttpHeaders.NavConsumerToken, "Bearer ".plus(stsToken))
                        header(HttpHeaders.NavCallId, callId)
                        header(HttpHeaders.NavConsumerId, environment.consumerId)
                        header(HttpHeaders.NavPersonident, call.request.header(HttpHeaders.NavPersonident))
                    }

                val responseBody: JsonElement = response.receive()

                if (!response.status.isSuccess()) {
                    logger.warn("Kall til person-mottak feilet med statuskode ${response.status}: $responseBody")
                }

                val location = call.request.header(HttpHeaders.Location)
                if (!location.isNullOrEmpty()) {
                    call.response.header(HttpHeaders.Location, location)
                }

                call.respond(response.status, responseBody)
            } catch (e: Throwable) {
                logger.error("Teknisk feil ved kall til person-mottak: ${e.message}", e)
            }
        }

        post("/endring/bankkonto") {
            try {
                val callId = call.request.header(HttpHeaders.NavCallId) ?: UUID.randomUUID().toString()
                val requestBody = call.receive<JsonElement>()
                val stsToken = stsConsumer.getToken(environment.srvpersonopplysningerUsername, environment.srvpersonopplysningerPassword)

                logger.info("Url: " + environment.personMottakUrl + "/api/v1/endring/bankkonto")

                val response: HttpResponse =
                    client.post(path = environment.personMottakUrl + "/api/v1/endring/bankkonto", body = requestBody) {
                        contentType(ContentType.Application.Json)

                        header(HttpHeaders.Authorization, "Bearer ".plus(call.request.header(HttpHeaders.NavConsumerToken)))
                        header(HttpHeaders.NavConsumerToken, "Bearer ".plus(stsToken))
                        header(HttpHeaders.NavCallId, callId)
                        header(HttpHeaders.NavConsumerId, environment.consumerId)
                        header(HttpHeaders.NavPersonident, call.request.header(HttpHeaders.NavPersonident))
                    }

                val responseBody: JsonElement = response.receive()

                if (!response.status.isSuccess()) {
                    logger.warn("Kall til person-mottak feilet med statuskode ${response.status}: $responseBody")
                }

                val location = call.request.header(HttpHeaders.Location)
                if (!location.isNullOrEmpty()) {
                    call.response.header(HttpHeaders.Location, location)
                }

                call.respond(response.status, responseBody)
            } catch (e: Throwable) {
                logger.error("Teknisk feil ved kall til person-mottak: ${e.message}", e)
            }
        }

        get("/endringer/{$ENDRING_ID}") {
            try {
                val callId = call.request.header(HttpHeaders.NavCallId) ?: UUID.randomUUID().toString()
                val stsToken = stsConsumer.getToken(environment.srvpersonopplysningerUsername, environment.srvpersonopplysningerPassword)

                val response: HttpResponse =
                    client.get(environment.personMottakUrl + "/api/v1/endringer/${call.parameters[ENDRING_ID]}") {
                        header(HttpHeaders.Authorization, "Bearer ".plus(call.request.header(HttpHeaders.NavConsumerToken)))
                        header(HttpHeaders.NavConsumerToken, "Bearer ".plus(stsToken))
                        header(HttpHeaders.NavCallId, callId)
                        header(HttpHeaders.NavConsumerId, environment.consumerId)
                    }

                val responseBody: JsonElement = response.receive()

                if (!response.status.isSuccess()) {
                    logger.warn("Kall til person-mottak feilet med statuskode ${response.status}: $responseBody")
                }

                call.respond(response.status, responseBody)
            } catch (e: Throwable) {
                logger.error("Teknisk feil ved kall til person-mottak: ${e.message}", e)
            }
        }
    }
}
