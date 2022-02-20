package no.nav.personopplysninger.proxy.sts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TokenDto (
    val access_token: String = ""
)
