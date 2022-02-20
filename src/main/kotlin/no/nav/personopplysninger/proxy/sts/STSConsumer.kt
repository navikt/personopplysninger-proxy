package no.nav.personopplysninger.proxy.sts

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import no.nav.personbruker.dittnav.common.logging.util.logger
import no.nav.personopplysninger.proxy.config.Environment
import no.nav.personopplysninger.proxy.config.NavConsumerId
import java.io.UnsupportedEncodingException
import java.util.*

class STSConsumer(val client: HttpClient, val environment: Environment) {

    suspend fun getToken(): String {
        try {
            val response: HttpResponse =
                client.get(environment.stsUrl + "/rest/v1/sts/token") {
                    header(HttpHeaders.NavConsumerId, environment.consumerId)
                    header(HttpHeaders.Authorization, createBasicAuthHeaderValue(environment))

                    parameter("grant_type", "client_credentials")
                    parameter("scope", "openid")
                    expectSuccess = true
                }

            logger.info("Kall til url: ${response.call.request.url}") // TODO: Fjern denne

            val stsToken: TokenDto = response.receive()
            return stsToken.access_token
        } catch (e: Throwable) {
            logger.error("Teknisk feil ved kall til sts: ${e.message}", e)
            throw e
        }
    }

    private fun createBasicAuthHeaderValue(env: Environment): String {
        val token = "${env.srvUsername}:${env.srvPassword}"
        try {
            return "BASIC " + Base64.getEncoder().encodeToString(token.toByteArray(charset("UTF-8")))
        } catch (ex: UnsupportedEncodingException) {
            throw IllegalStateException("Cannot encode with UTF-8", ex)
        }
    }
}
