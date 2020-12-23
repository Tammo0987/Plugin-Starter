package com.github.plugin.model.pipeline

import com.github.plugin.model.Plugin

abstract class Step {

    abstract fun process(plugin: Plugin)

}