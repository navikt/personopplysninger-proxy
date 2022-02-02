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
import no.nav.personopplysninger.proxy.config.Environment
import no.nav.personopplysninger.proxy.config.NavCallId
import no.nav.personopplysninger.proxy.config.NavConsumerId
import java.util.*

const val ORGNR = "orgnr"

fun Route.eregRouting(client: HttpClient, environment: Environment) {
    route("/v1/organisasjon/{orgnr}/noekkelinfo") {
        get {
            try {
                val callId = call.request.header(HttpHeaders.NavCallId) ?: UUID.randomUUID().toString()

                val response: HttpResponse =
                    client.get(environment.eregUrl + "v1/organisasjon/${call.parameters[ORGNR]}/noekkelinfo") {
                        header(HttpHeaders.Authorization, call.request.header(HttpHeaders.Authorization))
                        header(HttpHeaders.NavCallId, callId)
                        header(HttpHeaders.NavConsumerId, environment.consumerId)
                    }

                call.respond(response.status, response.receive() as String)
            } catch (e: Throwable) {
                call.application.environment.log.error(e.message, e)
                throw e
            }



        }
    }
}
