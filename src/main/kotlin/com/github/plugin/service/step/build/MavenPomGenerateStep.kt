package com.github.plugin.service.step.build

import com.github.plugin.model.Plugin
import com.github.plugin.model.pipeline.Step
import com.github.plugin.service.step.io.FileCreationStep
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.XmlVersion
import org.redundent.kotlin.xml.xml
import kotlin.io.path.ExperimentalPathApi

class MavenPomGenerateStep : Step() {

    @ExperimentalPathApi
    override fun process(plugin: Plugin) {
        FileCreationStep(
            "${plugin.metadata.name}/pom.xml",
            this.generatePom(plugin).toByteArray(Charsets.UTF_8)
        ).process(plugin)
    }

    private fun generatePom(plugin: Plugin): String {
        return xml("project") {
            globalProcessingInstruction(
                "xml",
                Pair("version", XmlVersion.V10.value),
                Pair("encoding", Charsets.UTF_8.toString())
            )

            xmlns = "http://maven.apache.org/POM/4.0.0"

            element("modelVersion", "4.0.0")

            emptyLine()

            element("groupId", plugin.metadata.group)
            element("artifactId", plugin.metadata.name)
            element("version", plugin.metadata.version)
        }.toString(PrintOptions(true, singleLineTextElements = true, useSelfClosingTags = false))
    }

    private fun Node.emptyLine() {
        addNode(object : Node("lineBreak") {
            override fun render(builder: Appendable, indent: String, printOptions: PrintOptions) {
                builder.append("\n")
            }
        })
    }
}