package no.nav.personopplysninger.proxy.config

import io.ktor.http.HttpHeaders

val HttpHeaders.NavCallId: String
    get() = "Nav-Call-Id"
val HttpHeaders.NavConsumerId: String
    get() = "Nav-Consumer-Id"
val HttpHeaders.NavPersonident: String
    get() = "Nav-Personident"