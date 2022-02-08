package no.nav.personopplysninger.proxy.config

import io.ktor.http.*

val HttpHeaders.NavCallId: String
    get() = "Nav-Call-Id"
val HttpHeaders.NavConsumerId: String
    get() = "Nav-Consumer-Id"
val HttpHeaders.NavSelvbetjeningstoken: String
    get() = "Nav-Selvbetjeningstoken"
