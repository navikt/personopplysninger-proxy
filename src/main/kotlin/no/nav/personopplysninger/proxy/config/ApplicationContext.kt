package no.nav.personopplysninger.proxy.config

import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import no.nav.personopplysninger.proxy.tokenx.TokenxService
import no.nav.tms.token.support.tokendings.exchange.TokendingsServiceBuilder

class ApplicationContext {

    val tokendingsService = TokendingsServiceBuilder.buildTokendingsService()

    val httpClient = HttpClientBuilder.build()

    val appMicrometerRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)

    val tokenxService = TokenxService(tokendingsService)

}
