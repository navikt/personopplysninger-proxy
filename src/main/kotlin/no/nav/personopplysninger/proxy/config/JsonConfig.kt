package no.nav.personopplysninger.proxy.config

import kotlinx.serialization.json.Json

fun jsonConfig(): Json {
    return Json {
        this.ignoreUnknownKeys = true
        this.encodeDefaults = true
        this.prettyPrint = true
    }
}
