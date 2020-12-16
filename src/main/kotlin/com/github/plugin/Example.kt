package com.github.plugin

import com.github.plugin.model.Plugin
import com.github.plugin.model.PluginMetadata
import com.github.plugin.model.build.BuildTool
import com.github.plugin.model.dependency.Dependency
import com.github.plugin.model.dependency.DependencyScope
import com.github.plugin.model.repository.Repository
import com.github.plugin.service.PluginGenerateService

fun main() {
    val metadata = PluginMetadata("name", "de.tammo", "1.0", "Tammo", "Example Project")
    val plugin =
        Plugin(
            metadata,
            BuildTool.GRADLE,
            mutableListOf(Repository("jcenter", "https://jcenter.bintray.com")),
            mutableListOf(Dependency("de.tammo", "test", "1.0", DependencyScope.COMPILE))
        )

    PluginGenerateService().generate(plugin)
}