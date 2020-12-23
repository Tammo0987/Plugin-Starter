package com.github.plugin.generator.model.pipeline

import com.github.plugin.generator.model.Plugin

abstract class Step {

    abstract fun process(plugin: Plugin)

}