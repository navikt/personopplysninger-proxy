package no.nav.personopplysninger.proxy.health

interface HealthCheck {

    suspend fun status(): HealthStatus

}
