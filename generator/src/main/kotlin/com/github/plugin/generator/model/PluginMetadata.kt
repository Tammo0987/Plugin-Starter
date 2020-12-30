package com.github.plugin.generator.model

data class PluginMetadata(
    val name: String,
    val group: String,
    val version: String,
    val authors: List<String>,
    val description: String?
)