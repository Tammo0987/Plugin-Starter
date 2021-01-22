package com.github.plugin.generator

import com.github.plugin.generator.api.PluginApi
import com.github.plugin.generator.model.Plugin
import com.github.plugin.generator.model.PluginMetadata
import com.github.plugin.generator.model.build.BuildTool
import com.github.plugin.generator.model.language.Language
import com.github.plugin.generator.service.PluginGenerateService
import java.nio.file.Paths

fun main() {
    val metadata = PluginMetadata("test", "de.tammo", "1.0", listOf("Tammo", "Paul"), "Example Project")
    val plugin =
        Plugin(
            metadata,
            BuildTool.GRADLE,
            PluginApi.PAPER,
            "1.16.5-R0.1-SNAPSHOT",
            Language.KOTLIN,
            mutableListOf(),
            mutableListOf()
        )

    PluginGenerateService.generate(plugin, Paths.get(""))
}