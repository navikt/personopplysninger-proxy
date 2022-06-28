object DittNAV {
    object Common {
        private const val version = "2022.04.19-11.11-1043a85c4f6f"
        private const val groupId = "com.github.navikt.dittnav-common-lib"

        const val logging = "$groupId:dittnav-common-logging:$version"
        const val utils = "$groupId:dittnav-common-utils:$version"
    }
}

object Jackson {
    private const val version = "2.13.3"

    const val dataTypeJsr310 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$version"
}

object Kotlin {
    const val version = "1.7.0"
}

object Kotlinx {
    private const val groupId = "org.jetbrains.kotlinx"

    const val coroutines = "$groupId:kotlinx-coroutines-core:1.6.3"
    const val htmlJvm = "$groupId:kotlinx-html-jvm:0.7.5"
}

object Ktor {
    private const val version = "2.0.2"
    private const val groupId = "io.ktor"

    const val auth = "$groupId:ktor-server-auth:$version"
    const val authJwt = "$groupId:ktor-server-auth-jwt:$version"
    const val cors = "$groupId:ktor-server-cors:$version"
    const val clientContentNegotiation = "$groupId:ktor-client-content-negotiation:$version"
    const val serverContentNegotiation = "$groupId:ktor-server-content-negotiation:$version"
    const val defaultHeaders = "$groupId:ktor-server-default-headers:$version"
    const val serverNetty = "$groupId:ktor-server-netty:$version"
    const val clientApache = "$groupId:ktor-client-apache:$version"
    const val clientJson = "$groupId:ktor-client-json:$version"
    const val clientSerializationJvm = "$groupId:ktor-client-serialization-jvm:$version"
    const val clientJackson = "$groupId:ktor-client-jackson:$version"
    const val clientLogging = "$groupId:ktor-client-logging:$version"
    const val clientLoggingJvm = "$groupId:ktor-client-logging-jvm:$version"
    const val htmlBuilder = "$groupId:ktor-server-html-builder:$version"
    const val jackson = "$groupId:ktor-serialization-jackson:$version"
    const val serialization = "$groupId:ktor-serialization-kotlinx-json:$version"
}

object Logback {
    private const val version = "1.2.11"
    const val classic = "ch.qos.logback:logback-classic:$version"
}

object Logstash {
    private const val version = "7.2"
    const val logbackEncoder = "net.logstash.logback:logstash-logback-encoder:$version"
}

object Shadow {
    const val version = "7.1.2"
    const val pluginId = "com.github.johnrengelman.shadow"
}

object TmsKtorTokenSupport {
    private const val version = "2022.05.19-09.32-5076b2435b0a"
    private const val groupId = "com.github.navikt.tms-ktor-token-support"

    const val authenticationInstaller = "$groupId:token-support-authentication-installer:$version"
    const val tokendingsExchange = "$groupId:token-support-tokendings-exchange:$version"
    const val tokenXValidation = "$groupId:token-support-tokenx-validation:$version"
}

object Versions {
    const val version = "0.42.0"
    const val pluginId = "com.github.ben-manes.versions"
}