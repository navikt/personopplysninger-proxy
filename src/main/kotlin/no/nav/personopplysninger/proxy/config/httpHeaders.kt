package no.nav.personopplysninger.proxy.config

import io.ktor.http.*

val HttpHeaders.NavCallId: String
    get() = "Nav-Call-Id"
val HttpHeaders.NavConsumerId: String
    get() = "Nav-Consumer-Id"
val HttpHeaders.NavConsumerToken: String
    get() = "Nav-Consumer-Token"
val HttpHeaders.NavPersonident: String
    get() = "Nav-Personident"
val HttpHeaders.Enhetsnr: String
    get() = "enhetsnr"
