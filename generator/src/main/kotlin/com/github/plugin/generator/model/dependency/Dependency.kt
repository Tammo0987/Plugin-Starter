package com.github.plugin.generator.model.dependency

data class Dependency(
    val group: String,
    val artifact: String,
    val version: String,
    val scope: DependencyScope
)