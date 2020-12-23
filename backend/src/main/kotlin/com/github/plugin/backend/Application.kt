package com.github.plugin.backend

import com.github.plugin.backend.route.generate
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, module = Application::module).start()
}

fun Application.module() {
    install(DefaultHeaders)
    install(Compression)

    install(ContentNegotiation) {
        jackson()
    }

    install(Routing) {
        generate()
    }
}