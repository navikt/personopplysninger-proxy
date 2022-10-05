object DittNAV {
    object Common {
        private const val version = "2022.09.30-12.41-aa46d2d75788"
        private const val groupId = "com.github.navikt.dittnav-common-lib"

        const val utils = "$groupId:dittnav-common-utils:$version"
    }
}

object Jackson {
    private const val version = "2.13.3"

    const val dataTypeJsr310 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$version"
}

object Kotlin {
    const val version = "1.7.20"
}

object Kotlinx {
    private const val groupId = "org.jetbrains.kotlinx"

    const val coroutines = "$groupId:kotlinx-coroutines-core:1.6.4"
    const val htmlJvm = "$groupId:kotlinx-html-jvm:0.8.0"
}

object Ktor {
    private const val version = "2.1.2"
    private const val groupId = "io.ktor"

    const val metricsMicrometer = "$groupId:ktor-server-metrics-micrometer:$version"
    const val auth = "$groupId:ktor-server-auth:$version"
    const val authJwt = "$groupId:ktor-server-auth-jwt:$version"
    const val cors = "$groupId:ktor-server-cors:$version"
    const val clientContentNegotiation = "$groupId:ktor-client-content-negotiation:$version"
    const val serverContentNegotiation = "$groupId:ktor-server-content-negotiation:$version"
    const val defaultHeaders = "$groupId:ktor-server-default-headers:$version"
    const val serverNetty = "$groupId:ktor-server-netty:$version"
    const val serverCallLogging = "$groupId:ktor-server-call-logging:$version"
    const val clientApache = "$groupId:ktor-client-apache:$version"
    const val clientJson = "$groupId:ktor-client-json:$version"
    const val clientSerialization = "$groupId:ktor-client-serialization:$version"
    const val clientJackson = "$groupId:ktor-client-jackson:$version"
    const val clientLogging = "$groupId:ktor-client-logging:$version"
    const val htmlBuilder = "$groupId:ktor-server-html-builder:$version"
    const val jackson = "$groupId:ktor-serialization-jackson:$version"
    const val serialization = "$groupId:ktor-serialization-kotlinx-json:$version"
}

object Logback {
    private const val version = "1.4.3"
    const val classic = "ch.qos.logback:logback-classic:$version"
}

object Logstash {
    private const val version = "7.2"
    const val logbackEncoder = "net.logstash.logback:logstash-logback-encoder:$version"
}

object Micrometer {
    private const val version = "1.9.4"
    const val registryPrometheus = "io.micrometer:micrometer-registry-prometheus:$version"
}

object Shadow {
    const val version = "7.1.2"
    const val pluginId = "com.github.johnrengelman.shadow"
}

object TmsKtorTokenSupport {
    private const val version = "2.0.0"
    private const val groupId = "com.github.navikt.tms-ktor-token-support"

    const val authenticationInstaller = "$groupId:token-support-authentication-installer:$version"
    const val tokendingsExchange = "$groupId:token-support-tokendings-exchange:$version"
    const val tokenXValidation = "$groupId:token-support-tokenx-validation:$version"
}

object Versions {
    const val version = "0.42.0"
    const val pluginId = "com.github.ben-manes.versions"
}