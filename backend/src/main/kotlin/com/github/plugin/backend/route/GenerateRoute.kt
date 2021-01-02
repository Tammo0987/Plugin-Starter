package com.github.plugin.backend.route

import com.github.plugin.backend.service.ZipGenerateService
import com.github.plugin.generator.model.Plugin
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File

fun Route.generate() = post("/api/generate") {
    val plugin = call.receive<Plugin>()

    val zipFile = ZipGenerateService.generateZip(plugin)

    val file = File(zipFile)

    if (file.exists()) {
        call.response.header(
            HttpHeaders.ContentDisposition,
            ContentDisposition.Attachment.withParameter(
                ContentDisposition.Parameters.FileName,
                "${plugin.metadata.name}.zip"
            ).toString()
        )
        call.respondFile(file)
    } else {
        call.respond(HttpStatusCode.InternalServerError)
    }
}