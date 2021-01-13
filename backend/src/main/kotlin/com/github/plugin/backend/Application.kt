package com.github.plugin.backend

import com.github.plugin.backend.route.dependencies
import com.github.plugin.backend.route.generate
import com.github.plugin.backend.route.versions
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, module = Application::module).start()
}

fun Application.module() {
    install(DefaultHeaders)
    install(Compression)

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)

        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.AccessControlAllowOrigin)

        allowCredentials = true

        anyHost()
    }

    install(ContentNegotiation) {
        jackson()
    }

    install(Routing) {
        generate()
        versions()
        dependencies()
    }

    install(StatusPages) {
        exception<Throwable> {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}