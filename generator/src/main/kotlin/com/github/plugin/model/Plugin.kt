package com.github.plugin.model

import com.github.plugin.api.PluginApi
import com.github.plugin.model.build.BuildTool
import com.github.plugin.model.dependency.Dependency
import com.github.plugin.model.repository.Repository

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