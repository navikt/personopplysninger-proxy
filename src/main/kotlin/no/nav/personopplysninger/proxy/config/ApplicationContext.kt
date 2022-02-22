package no.nav.personopplysninger.proxy.config

import no.nav.personopplysninger.proxy.health.HealthService
import no.nav.personopplysninger.proxy.sts.STSConsumer

class ApplicationContext {

    val environment = Environment()

    val httpClient = HttpClientBuilder.build()
    val healthService = HealthService(this)

    val stsConsumer = STSConsumer(httpClient, environment)

}
