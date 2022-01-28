package no.nav.personopplysninger.proxy.config

import no.nav.personopplysninger.proxy.health.HealthService

class ApplicationContext {

    val httpClient = HttpClientBuilder.build()
    val healthService = HealthService(this)

}
