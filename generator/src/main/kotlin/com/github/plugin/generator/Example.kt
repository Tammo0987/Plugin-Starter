package com.github.plugin.generator

import com.github.plugin.generator.api.PluginApi
import com.github.plugin.generator.model.Plugin
import com.github.plugin.generator.model.PluginMetadata
import com.github.plugin.generator.model.build.BuildTool
import com.github.plugin.generator.service.PluginGenerateService
import java.nio.file.Paths

fun main() {
    val metadata = PluginMetadata("name", "de.tammo", "1.0", "Tammo", "Example Project")
    val plugin =
        Plugin(
            metadata,
            BuildTool.GRADLE,
            PluginApi.SPIGOT,
            "1.8.8-R0.1-SNAPSHOT",
            mutableListOf(),
            mutableListOf()
        )

    PluginGenerateService.generate(plugin, Paths.get(""))
}