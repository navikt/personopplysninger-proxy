package no.nav.personopplysninger.proxy.config

import no.nav.personbruker.dittnav.common.util.config.StringEnvVar.getEnvVar

data class Environment(
    val corsAllowedOrigins: String = getEnvVar("CORS_ALLOWED_ORIGINS"),
    val kodeverkUrl: String = getEnvVar("KODEVERK_URL"),
    val eregUrl: String = getEnvVar("EREG_URL"),
    val aaregUrl: String = getEnvVar("AAREG_URL"),
    val sporingsloggUrl: String = getEnvVar("SPORINGSLOGG_URL"),
    val stsUrl: String = getEnvVar("STS_URL"),
    val srvUsername: String = getEnvVar("SRVPERSONOPPLYSNINGER_USERNAME"),
    val srvPassword: String = getEnvVar("SRVPERSONOPPLYSNINGER_PASSWORD"),
    val consumerId: String = "personopplysninger-proxy",
)