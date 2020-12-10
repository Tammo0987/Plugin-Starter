package com.github.plugin.model.dependency

data class Dependency(
    val group: String,
    val artifact: String,
    val version: String,
    val scope: DependencyScope
)