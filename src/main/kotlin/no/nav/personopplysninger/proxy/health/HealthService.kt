package no.nav.personopplysninger.proxy.health

import no.nav.personopplysninger.proxy.config.ApplicationContext

class HealthService(private val applicationContext: ApplicationContext) {

    suspend fun getHealthChecks(): List<HealthStatus> {
        return emptyList()
    }
}
