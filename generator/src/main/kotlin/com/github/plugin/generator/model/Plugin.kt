package com.github.plugin.generator.model

import com.github.plugin.generator.api.PluginApi
import com.github.plugin.generator.model.build.BuildTool
import com.github.plugin.generator.model.dependency.Dependency
import com.github.plugin.generator.model.repository.Repository

data class Plugin(
    val metadata: PluginMetadata,
    val buildTool: BuildTool,
    val api: PluginApi,
    val version: String,
    val repositories: MutableList<Repository>,
    val dependencies: MutableList<Dependency>
) {

    init {
        this.api.repositories.forEach { this.repositories.add(it) }
        this.dependencies.add(this.api.dependency(this.version))
    }

}