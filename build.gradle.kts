import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    val kotlinVersion = "1.9.23"
    val shadowVersion = "8.1.1"
    val versionsVersion = "0.51.0"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("com.github.johnrengelman.shadow") version shadowVersion
    id("com.github.ben-manes.versions") version versionsVersion // ./gradlew dependencyUpdates to check for new versions
    application
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    val dittNAVCommonUtilsVersion = "2022.09.30-12.41-aa46d2d75788"
    val jacksonDataTypeJsr310Version = "2.17.0"
    val kotlinxCoroutinesVersion = "1.8.0"
    val kotlinxHtmlJvmVersion = "0.11.0"
    val ktorVersion = "2.3.10"
    val logbackVersion = "1.5.5"
    val logstashVersion = "7.4"
    val micrometerVersion = "1.12.5"
    val tmsKtorTokenSupportVersion = "4.0.0"

    implementation("com.github.navikt.dittnav-common-lib:dittnav-common-utils:$dittNAVCommonUtilsVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonDataTypeJsr310Version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinxHtmlJvmVersion")
    implementation("io.ktor:ktor-server-metrics-micrometer:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-default-headers:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-jackson:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("io.ktor:ktor-server-html-builder:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashVersion")
    implementation("io.micrometer:micrometer-registry-prometheus:$micrometerVersion")
    implementation("com.github.navikt.tms-ktor-token-support:tokendings-exchange:$tmsKtorTokenSupportVersion")
    implementation("com.github.navikt.tms-ktor-token-support:tokenx-validation:$tmsKtorTokenSupportVersion")
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
