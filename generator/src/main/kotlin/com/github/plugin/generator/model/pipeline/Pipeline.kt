package com.github.plugin.generator.model.pipeline

import com.github.plugin.generator.model.Plugin
import java.nio.file.Path

class Pipeline {

    private val steps: MutableList<Step> = mutableListOf()

    fun execute(plugin: Plugin, workingDirectory: Path) = this.steps.forEach { it.process(plugin, workingDirectory) }

    fun appendSteps(steps: List<Step>) = steps.forEach { this.steps.add(it) }

}