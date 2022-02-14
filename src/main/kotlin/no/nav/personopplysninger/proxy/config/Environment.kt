package no.nav.personopplysninger.proxy.config

import no.nav.personbruker.dittnav.common.util.config.StringEnvVar.getEnvVar

data class Environment(
    val corsAllowedOrigins: String = getEnvVar("CORS_ALLOWED_ORIGINS"),
    val kodeverkUrl: String = getEnvVar("KODEVERK_URL"),
    val eregUrl: String = getEnvVar("EREG_URL"),
    val aaregUrl: String = getEnvVar("AAREG_URL"),
    val sporingsloggUrl: String = getEnvVar("SPORINGSLOGG_URL"),
    val consumerId: String = "personopplysninger-proxy",
)
