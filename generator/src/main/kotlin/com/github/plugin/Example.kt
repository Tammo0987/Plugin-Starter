package com.github.plugin

import com.github.plugin.api.PluginApi
import com.github.plugin.model.Plugin
import com.github.plugin.model.PluginMetadata
import com.github.plugin.model.build.BuildTool
import com.github.plugin.service.PluginGenerateService

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

    PluginGenerateService().generate(plugin)
}