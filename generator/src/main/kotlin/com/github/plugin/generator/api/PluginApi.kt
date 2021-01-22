package com.github.plugin.generator.api

import com.github.plugin.generator.api.step.SpigotStep
import com.github.plugin.generator.api.step.SpongeStep
import com.github.plugin.generator.model.dependency.Dependency
import com.github.plugin.generator.model.dependency.DependencyScope
import com.github.plugin.generator.model.pipeline.Step
import com.github.plugin.generator.model.repository.Repository

enum class PluginApi(
    val repositories: List<Repository>,
    val versions: List<String>,
    val step: Step,
    private val group: String,
    private val artifact: String,
    private val dependencyScope: DependencyScope
) {
    SPIGOT(
        listOf(
            Repository("spigot-repo", "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"),
            Repository("sonatype", "https://oss.sonatype.org/content/repositories/snapshots")
        ),
        listOf(
            "1.8",
            "1.8.3",
            "1.8.4",
            "1.8.5",
            "1.8.6",
            "1.8.7",
            "1.8.8",
            "1.9",
            "1.9.2",
            "1.9.4",
            "1.10",
            "1.10.2",
            "1.11",
            "1.11.1",
            "1.11.2",
            "1.12",
            "1.12.1",
            "1.12.2",
            "1.13",
            "1.13.1",
            "1.13.2",
            "1.14",
            "1.14.1",
            "1.14.2",
            "1.14.3",
            "1.14.4",
            "1.15",
            "1.15.1",
            "1.15.2",
            "1.16.1",
            "1.16.2",
            "1.16.3",
            "1.16.4"
        ),
        SpigotStep(),
        "org.spigotmc",
        "spigot-api",
        DependencyScope.PROVIDED
    ),
    PAPER(
        listOf(Repository("papermc", "https://papermc.io/repo/repository/maven-public/")),
        listOf(
            "1.16.5-R0.1-SNAPSHOT",
            "1.16.4-R0.1-SNAPSHOT",
            "1.16.3-R0.1-SNAPSHOT",
            "1.16.2-R0.1-SNAPSHOT",
            "1.16.1-R0.1-SNAPSHOT",
            "1.15.2-R0.1-SNAPSHOT",
            "1.15.1-R0.1-SNAPSHOT",
            "1.15-R0.1-SNAPSHOT",
            "1.14.4-R0.1-SNAPSHOT",
            "1.14.3-R0.1-SNAPSHOT",
            "1.14.2-R0.1-SNAPSHOT",
            "1.14.1-R0.1-SNAPSHOT",
            "1.14-R0.1-SNAPSHOT",
            "1.13.2-R0.1-SNAPSHOT",
            "1.13.1-R0.1-SNAPSHOT",
            "1.13-R0.1-SNAPSHOT",
            "1.12.2-R0.1-SNAPSHOT",
            "1.12.1-R0.1-SNAPSHOT",
            "1.12-R0.1-SNAPSHOT",
            "1.11.2-R0.1-SNAPSHOT",
            "1.11.1-R0.1-SNAPSHOT",
            "1.11-R0.1-SNAPSHOT",
            "1.10.2-R0.1-SNAPSHOT",
            "1.9.4-R0.1-SNAPSHOT",
        ),
        SpigotStep(),
        "com.destroystokyo.paper",
        "paper-api",
        DependencyScope.PROVIDED
    ),
    SPONGE(
        emptyList(),
        emptyList(),
        SpongeStep(),
        "",
        "",
        DependencyScope.PROVIDED
    );

    fun dependency(version: String): Dependency =
        Dependency(this.group, this.artifact, version, this.dependencyScope)
}