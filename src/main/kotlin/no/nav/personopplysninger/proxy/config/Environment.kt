package no.nav.personopplysninger.proxy.config

import no.nav.personbruker.dittnav.common.util.config.StringEnvVar.getEnvVar

data class Environment(
    val corsAllowedOrigins: String = getEnvVar("CORS_ALLOWED_ORIGINS"),
    val inst2Url: String = getEnvVar("INST2_URL"),
    val medlUrl: String = getEnvVar("MEDL_URL"),
    val consumerId: String = "personopplysninger-proxy",
    val medlTargetApp: String = getEnvVar("MEDL_TARGET_APP"),
    val inst2TargetApp: String = getEnvVar("INST2_TARGET_APP"),
)