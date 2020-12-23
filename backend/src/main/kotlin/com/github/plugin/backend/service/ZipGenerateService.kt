package com.github.plugin.backend.service

import com.github.plugin.generator.model.Plugin
import com.github.plugin.generator.service.PluginGenerateService
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object ZipGenerateService {

    private val directory = Files.createTempDirectory("generate")

    /**
     * @return Zip file path.
     */
    fun generateZip(plugin: Plugin): String {
        val runDirectory = Files.createDirectories(Paths.get(this.directory.toString(), UUID.randomUUID().toString()))

        PluginGenerateService.generate(plugin, runDirectory)

        return runDirectory.toAbsolutePath().toString() + "/${plugin.metadata.name}.zip"
    }

}