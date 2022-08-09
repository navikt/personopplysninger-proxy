package no.nav.personopplysninger.proxy.tokenx

import io.ktor.http.HttpHeaders
import io.ktor.server.request.ApplicationRequest
import io.ktor.server.request.header
import no.nav.tms.token.support.tokendings.exchange.TokendingsService

class TokenxService(val tokendingsService: TokendingsService) {

    suspend fun exchangeAuthToken(request: ApplicationRequest, targetApp: String): String {
        val authorization = request.header(HttpHeaders.Authorization)!!
        val token =
            if (authorization.startsWith("Bearer ")) authorization.substring(7, authorization.length)
            else authorization
        return tokendingsService.exchangeToken(token, targetApp)
    }
}