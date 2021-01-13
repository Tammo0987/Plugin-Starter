package com.github.plugin.backend.route

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.dependencies() = get("/api/dependencies") {
    val mapper = ObjectMapper(YAMLFactory())
    try {
        val dependencies = mapper.readTree(javaClass.classLoader.getResource("dependencies.yaml"))
        call.respond(dependencies)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}