package com.github.plugin.backend.service

import com.github.plugin.generator.model.Plugin
import com.github.plugin.generator.service.PluginGenerateService
import net.lingala.zip4j.ZipFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object ZipGenerateService {

    private val directory = Files.createTempDirectory("generate")

    /**
     * @return Zip file path.
     */
    fun generateZip(plugin: Plugin): String {
        val generateDirectory = Paths.get(this.directory.toString(), UUID.randomUUID().toString())
        val runDirectory = Files.createDirectories(generateDirectory)

        PluginGenerateService.generate(plugin, runDirectory)

        val zipFileName = runDirectory.toAbsolutePath().toString() + "/${plugin.metadata.name}.zip"

        ZipFile(zipFileName).addFolder(generateDirectory.resolve(plugin.metadata.name).toFile())

        return zipFileName
    }

}