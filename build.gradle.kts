import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    kotlin("jvm") version("1.8.21")
    kotlin("plugin.allopen") version("1.8.21")
    kotlin("plugin.serialization") version("1.8.21")

    id("com.github.johnrengelman.shadow") version("8.1.1")
    id("com.github.ben-manes.versions") version("0.46.0") // ./gradlew dependencyUpdates to check for new versions
    application
}

kotlin {
    jvmToolchain(17)
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    val versions = object {
        val dittNAVCommonUtils = "2022.09.30-12.41-aa46d2d75788"
        val jacksonDataTypeJsr310 = "2.15.2"
        val kotlinxCoroutines = "1.7.1"
        val kotlinxHtmlJvm = "0.8.1"
        val ktor = "2.3.1"
        val logback = "1.4.7"
        val logstash = "7.3"
        val micrometer = "1.11.0"
        val tmsKtorTokenSupport = "2.0.1"
    }

    implementation("com.github.navikt.dittnav-common-lib:dittnav-common-utils:${versions.dittNAVCommonUtils}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${versions.jacksonDataTypeJsr310}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.kotlinxCoroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:${versions.kotlinxHtmlJvm}")
    implementation("io.ktor:ktor-server-metrics-micrometer:${versions.ktor}")
    implementation("io.ktor:ktor-server-auth:${versions.ktor}")
    implementation("io.ktor:ktor-server-auth-jwt:${versions.ktor}")
    implementation("io.ktor:ktor-server-cors:${versions.ktor}")
    implementation("io.ktor:ktor-client-content-negotiation:${versions.ktor}")
    implementation("io.ktor:ktor-server-content-negotiation:${versions.ktor}")
    implementation("io.ktor:ktor-server-default-headers:${versions.ktor}")
    implementation("io.ktor:ktor-server-netty:${versions.ktor}")
    implementation("io.ktor:ktor-server-call-logging:${versions.ktor}")
    implementation("io.ktor:ktor-client-apache:${versions.ktor}")
    implementation("io.ktor:ktor-client-json:${versions.ktor}")
    implementation("io.ktor:ktor-client-serialization:${versions.ktor}")
    implementation("io.ktor:ktor-client-jackson:${versions.ktor}")
    implementation("io.ktor:ktor-client-logging:${versions.ktor}")
    implementation("io.ktor:ktor-server-html-builder:${versions.ktor}")
    implementation("io.ktor:ktor-serialization-jackson:${versions.ktor}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${versions.ktor}")
    implementation("ch.qos.logback:logback-classic:${versions.logback}")
    implementation("net.logstash.logback:logstash-logback-encoder:${versions.logstash}")
    implementation("io.micrometer:micrometer-registry-prometheus:${versions.micrometer}")
    implementation("com.github.navikt.tms-ktor-token-support:token-support-authentication-installer:${versions.tmsKtorTokenSupport}")
    implementation("com.github.navikt.tms-ktor-token-support:token-support-tokendings-exchange:${versions.tmsKtorTokenSupport}")
    implementation("com.github.navikt.tms-ktor-token-support:token-support-tokenx-validation:${versions.tmsKtorTokenSupport}")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

tasks {
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            events("passed", "skipped", "failed")
        }
    }
}
