package com.github.plugin.model

data class PluginMetadata(
    val name: String,
    val group: String,
    val version: String,
    val autor: String?,
    val description: String?
)