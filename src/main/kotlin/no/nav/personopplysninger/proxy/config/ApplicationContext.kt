package no.nav.personopplysninger.proxy.config

import no.nav.personopplysninger.proxy.health.HealthService
import no.nav.personopplysninger.proxy.sts.STSConsumer
import no.nav.personopplysninger.proxy.tokenx.TokenxService
import no.nav.tms.token.support.tokendings.exchange.TokendingsServiceBuilder

class ApplicationContext {

    val environment = Environment()
    val tokendingsService = TokendingsServiceBuilder.buildTokendingsService()

    val httpClient = HttpClientBuilder.build()
    val healthService = HealthService(this)

    val stsConsumer = STSConsumer(httpClient, environment)
    val tokenxService = TokenxService(tokendingsService)

}
