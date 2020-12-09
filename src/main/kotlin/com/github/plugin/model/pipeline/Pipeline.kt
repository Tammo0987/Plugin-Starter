package com.github.plugin.model.pipeline

import com.github.plugin.model.Plugin

class Pipeline {

    private val steps: MutableList<Step> = mutableListOf()

    fun execute(plugin: Plugin) = this.steps.forEach { it.process(plugin) }

    fun appendStep(step: Step) = this.steps.add(step)

}