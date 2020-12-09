package com.github.plugin.model

import com.github.plugin.model.build.BuildTool
import com.github.plugin.model.dependency.Dependency
import com.github.plugin.model.repository.Repository

data class Plugin(
    val metadata: PluginMetadata,
    val buildTool: BuildTool,
    val repositories: List<Repository>,
    val dependencies: List<Dependency>
)