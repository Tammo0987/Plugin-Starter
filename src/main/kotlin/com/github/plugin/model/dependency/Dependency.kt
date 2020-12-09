package com.github.plugin.model.dependency

data class Dependency(
    private val group: String,
    private val artifact: String,
    private val version: String,
    private val scope: DependencyScope
)