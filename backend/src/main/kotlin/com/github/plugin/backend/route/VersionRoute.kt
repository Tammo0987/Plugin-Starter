package com.github.plugin.backend.route

import com.github.plugin.generator.api.PluginApi
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.versions() = get("/api/versions") {
    val versionMap = PluginApi.values().associate { Pair(it, it.versions) }
    call.respond(versionMap)
}