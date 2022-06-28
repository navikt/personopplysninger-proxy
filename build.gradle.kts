import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    maven("https://jitpack.io")
    mavenCentral()
}

dependencies {
    implementation(DittNAV.Common.logging)
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
    implementation(Ktor.clientLoggingJvm)
    implementation(Ktor.clientSerializationJvm)
    implementation(Ktor.cors)
    implementation(Ktor.clientContentNegotiation)
    implementation(Ktor.defaultHeaders)
    implementation(Ktor.htmlBuilder)
    implementation(Ktor.jackson)
    implementation(Ktor.serverContentNegotiation)
    implementation(Ktor.serverNetty)
    implementation(Ktor.serialization)
    implementation(Logback.classic)
    implementation(Logstash.logbackEncoder)
    implementation(TmsKtorTokenSupport.tokenXValidation)
    implementation(TmsKtorTokenSupport.authenticationInstaller)
    implementation(TmsKtorTokenSupport.tokendingsExchange)
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks {
    register("runServer", JavaExec::class) {

        environment("CORS_ALLOWED_ORIGINS", "localhost:9002")

        environment("NAIS_CLUSTER_NAME", "dev-fss")
        environment("NAIS_NAMESPACE", "personbruker")

        main = application.mainClassName
        classpath = sourceSets["main"].runtimeClasspath
    }
}

apply(plugin = Shadow.pluginId)
