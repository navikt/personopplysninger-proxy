package no.nav.personopplysninger.proxy.health

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.micrometer.prometheus.PrometheusMeterRegistry


fun Routing.health(
    collectorRegistry: PrometheusMeterRegistry,
    ready: () -> Boolean = { true },
    alive: () -> Boolean = { true },
) {

    fun statusFor(b: () -> Boolean) = b().let { if (it) HttpStatusCode.OK else HttpStatusCode.InternalServerError }

    route("/internal") {

        get("/isReady") {
            statusFor(ready).let { call.respondText("Ready: $it", status = it) }
        }

        get("/isAlive") {
            statusFor(alive).let { call.respondText("Alive: $it", status = it) }
        }

        get("/metrics") {
            call.respond(collectorRegistry.scrape())
        }
    }

}

