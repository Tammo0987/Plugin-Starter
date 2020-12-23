package com.github.plugin.backend

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, module = Application::module).start()
}

fun Application.module() {
    install(Routing) {
        get("/") {
            call.respondText("Hello World", ContentType.Text.Plain)
        }
    }
}