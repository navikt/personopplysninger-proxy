plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
    kotlin("jvm").version(Kotlin.version)
    kotlin("plugin.allopen").version(Kotlin.version)
    kotlin("plugin.serialization").version(Kotlin.version)

    id(Shadow.pluginId) version (Shadow.version)
    id(Versions.pluginId) version Versions.version // ./gradlew dependencyUpdates to check for new versions
    application
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(DittNAV.Common.utils)
    implementation(Jackson.dataTypeJsr310)
    implementation(Kotlinx.coroutines)
    implementation(Kotlinx.htmlJvm)
    implementation(Ktor.auth)
    implementation(Ktor.authJwt)
    implementation(Ktor.clientApache)
    implementation(Ktor.clientJackson)
    implementation(Ktor.clientJson)
    implementation(Ktor.clientLogging)
    implementation(Ktor.clientSerialization)
    implementation(Ktor.cors)
    implementation(Ktor.clientContentNegotiation)
    implementation(Ktor.defaultHeaders)
    implementation(Ktor.htmlBuilder)
    implementation(Ktor.jackson)
    implementation(Ktor.serverContentNegotiation)
    implementation(Ktor.serverNetty)
    implementation(Ktor.serverCallLogging)
    implementation(Ktor.serialization)
    implementation(Ktor.metricsMicrometer)
    implementation(Micrometer.registryPrometheus)
    implementation(Logback.classic)
    implementation(Logstash.logbackEncoder)
    implementation(TmsKtorTokenSupport.tokenXValidation)
    implementation(TmsKtorTokenSupport.authenticationInstaller)
    implementation(TmsKtorTokenSupport.tokendingsExchange)
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

kotlin {
    jvmToolchain(17)
}

apply(plugin = Shadow.pluginId)
