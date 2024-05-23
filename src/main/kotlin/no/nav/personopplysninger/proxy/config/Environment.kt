package no.nav.personopplysninger.proxy.config

data class Environment(
    val corsAllowedOrigins: String = System.getenv("CORS_ALLOWED_ORIGINS"),
    val inst2Url: String = System.getenv("INST2_URL"),
    val medlUrl: String = System.getenv("MEDL_URL"),
    val consumerId: String = "personopplysninger-proxy",
    val medlTargetApp: String = System.getenv("MEDL_TARGET_APP"),
    val inst2TargetApp: String = System.getenv("INST2_TARGET_APP"),
)