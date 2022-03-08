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

private const val HISTORIKK = "historikk"
private const val SPORINGSINFORASJON = "sporingsinformasjon"
private const val REGELVERK = "regelverk"
private const val ARBEIDSFORHOLDTYPE = "arbeidsforholdtype"
private const val ID = "id"

fun Route.aaregRouting(client: HttpClient, environment: Environment, stsConsumer: STSConsumer) {
    route("/v1/arbeidsforhold/{id}") {
        get {
            try {
                val callId = call.request.header(HttpHeaders.NavCallId) ?: UUID.randomUUID().toString()
                val id = call.parameters[ID]
                val historikk = call.request.queryParameters[HISTORIKK]
                val sporingsinformasjon = call.request.queryParameters[SPORINGSINFORASJON]
                val stsToken = stsConsumer.getToken(environment.srvarbeidsforholdUsername, environment.srvarbeidsforholdPassword)

                val response: HttpResponse =
                    client.get(environment.aaregUrl + "/v1/arbeidsforhold/${id}") {
                        header(HttpHeaders.Authorization, "Bearer ".plus(call.request.header(HttpHeaders.NavConsumerToken)))
                        header(HttpHeaders.NavConsumerToken, "Bearer ".plus(stsToken))
                        header(HttpHeaders.NavCallId, callId)
                        header(HttpHeaders.NavConsumerId, environment.consumerId)
                        header(HttpHeaders.NavPersonident, call.request.header(HttpHeaders.NavPersonident))

                        parameter(HISTORIKK, historikk)
                        parameter(SPORINGSINFORASJON, sporingsinformasjon)
                    }

                val responseBody: JsonElement = response.receive()

                if (!response.status.isSuccess()) {
                    logger.warn("Kall til aareg feilet med statuskode ${response.status}: $responseBody")
                    call.respond(HttpStatusCode.InternalServerError, "Kall til aareg feilet")
                }
                call.respond(response.status, responseBody)
            } catch (e: Throwable) {
                logger.error("Teknisk feil ved kall til aareg: ${e.message}", e)
            }
        }
    }

    route("/v1/arbeidstaker/arbeidsforhold") {
        get {
            try {
                val callId = call.request.header(HttpHeaders.NavCallId) ?: UUID.randomUUID().toString()
                val regelverk = call.request.queryParameters[REGELVERK]
                val sporingsinformasjon = call.request.queryParameters[SPORINGSINFORASJON]
                val arbeidsforholdtype = call.request.queryParameters[ARBEIDSFORHOLDTYPE]
                val stsToken = stsConsumer.getToken(environment.srvarbeidsforholdUsername, environment.srvarbeidsforholdPassword)

                val response: HttpResponse =
                    client.get(environment.aaregUrl + "/v1/arbeidstaker/arbeidsforhold") {
                        header(HttpHeaders.Authorization, "Bearer ".plus(call.request.header(HttpHeaders.NavConsumerToken)))
                        header(HttpHeaders.NavConsumerToken, "Bearer ".plus(stsToken))
                        header(HttpHeaders.NavCallId, callId)
                        header(HttpHeaders.NavConsumerId, environment.consumerId)
                        header(HttpHeaders.NavPersonident, call.request.header(HttpHeaders.NavPersonident))

                        parameter(REGELVERK, regelverk)
                        parameter(SPORINGSINFORASJON, sporingsinformasjon)
                        parameter(ARBEIDSFORHOLDTYPE, arbeidsforholdtype)
                    }


                val responseBody: JsonElement = response.receive()

                if (!response.status.isSuccess()) {
                    logger.warn("Kall til aareg feilet med statuskode ${response.status}: $responseBody")
                    call.respond(HttpStatusCode.InternalServerError, "Kall til aareg feilet")
                }
                call.respond(response.status, responseBody)
            } catch (e: Throwable) {
                logger.error("Teknisk feil ved kall til aareg: ${e.message}", e)
            }
        }
    }
}
