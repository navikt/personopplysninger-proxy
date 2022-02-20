package no.nav.personopplysninger.proxy.sts

import kotlinx.serialization.Serializable

@Serializable
data class TokenDto (
    val access_token: String = ""
)
