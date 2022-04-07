package no.nav.personopplysninger.proxy.config

import no.nav.personbruker.dittnav.common.util.config.StringEnvVar.getEnvVar

data class Environment(
    val corsAllowedOrigins: String = getEnvVar("CORS_ALLOWED_ORIGINS"),
    val kodeverkUrl: String = getEnvVar("KODEVERK_URL"),
    val eregUrl: String = getEnvVar("EREG_URL"),
    val aaregUrl: String = getEnvVar("AAREG_URL"),
    val personMottakUrl: String = getEnvVar("PERSON_MOTTAK_URL"),
    val sporingsloggUrl: String = getEnvVar("SPORINGSLOGG_URL"),
    val tpsProxyUrl: String = getEnvVar("TPS_PROXY_URL"),
    val inst2Url: String = getEnvVar("INST2_URL"),
    val norg2Url: String = getEnvVar("NORG2_URL"),
    val medlUrl: String = getEnvVar("MEDL_URL"),
    val stsUrl: String = getEnvVar("STS_URL"),
    val srvpersonopplysningerUsername: String = getEnvVar("SRVPERSONOPPLYSNINGER_USERNAME"),
    val srvpersonopplysningerPassword: String = getEnvVar("SRVPERSONOPPLYSNINGER_PASSWORD"),
    val srvarbeidsforholdUsername: String = getEnvVar("SRVARBEIDSFORHOLD_USERNAME"),
    val srvarbeidsforholdPassword: String = getEnvVar("SRVARBEIDSFORHOLD_PASSWORD"),
    val consumerId: String = "personopplysninger-proxy",
    val medlTargetApp: String = getEnvVar("MEDL_TARGET_APP"),
    val tpsProxyTargetApp: String = getEnvVar("TPS_PROXY_TARGET_APP"),
)