package no.nav.personopplysninger.proxy.tokenx

import io.ktor.http.*
import io.ktor.request.*
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