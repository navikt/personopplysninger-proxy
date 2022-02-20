package no.nav.personopplysninger.proxy.sts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.serialization.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@Serializable
data class TokenDto (
    val access_token: String = ""
)
