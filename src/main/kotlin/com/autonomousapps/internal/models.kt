@file:Suppress("UnstableApiUsage")

package com.autonomousapps.internal

import org.gradle.api.GradleException
import org.gradle.api.artifacts.component.ComponentIdentifier
import org.gradle.api.artifacts.component.ModuleComponentIdentifier
import org.gradle.api.artifacts.component.ProjectComponentIdentifier
import java.io.File

internal data class Artifact(
    /**
     * In group:artifact form. E.g.,
     * 1. "javax.inject:javax.inject"
     * 2. ":my-project"
     */
    val identifier: String,
    /**
     * Library (e.g., downloaded from jcenter) or a project ("module" in a multi-module project).
     */
    val componentType: ComponentType,
    /**
     * If false, a direct dependency (declared in the `dependencies {}` block). If true, a transitive dependency.
     */
    var isTransitive: Boolean? = null,
    /**
     *
     */
    var children: Set<String>? = null,
    /**
     * Physical artifact on disk; a jar file.
     */
    var file: File? = null
) {

    constructor(componentIdentifier: ComponentIdentifier, file: File? = null) : this(
        identifier = componentIdentifier.asString(),
        componentType = ComponentType.of(componentIdentifier),
        file = file
    )
}

internal enum class ComponentType {
    /**
     * A 3rd-party dependency.
     */
    LIBRARY,
    /**
     * A project dependency, aka a "module" in a multi-module or multi-project build.
     */
    PROJECT;

    companion object {
        fun of(componentIdentifier: ComponentIdentifier) = when (componentIdentifier) {
            is ModuleComponentIdentifier -> LIBRARY
            is ProjectComponentIdentifier -> PROJECT
            else -> throw GradleException("'This shouldn't happen'")
        }
    }
}

/**
 * A library or project.
 */
internal data class Component(
    /**
     * In group:artifact form. E.g.,
     * 1. "javax.inject:javax.inject"
     * 2. ":my-project"
     */
    val identifier: String,
    /**
     * If false, a direct dependency (declared in the `dependencies {}` block). If true, a transitive dependency.
     */
    val isTransitive: Boolean,
    /**
     * The classes declared by this library.
     */
    val classes: Set<String>
) : Comparable<Component> {

    override fun compareTo(other: Component): Int {
        return identifier.compareTo(other.identifier)
    }
}

/**
 * Represents a "mis-used" transitive dependency. The [identifier] is the unique name, and the [usedTransitiveClasses]
 * are the class members of the dependency that are used directly (which "shouldn't" be).
 */
internal data class TransitiveDependency(
    /**
     * In group:artifact form. E.g.,
     * 1. "javax.inject:javax.inject"
     * 2. ":my-project"
     */
    val identifier: String,
    /**
     * These are class members of this dependency that are used directly by the project in question. They have leaked
     * onto the classpath.
     */
    val usedTransitiveClasses: Set<String>
)

/**
 * Represents a dependency ([identifier]) that is declared in the `dependencies {}` block of a build script. This
 * dependency is unused and has zero or more transitive dependencies that _are_ used ([usedTransitiveDependencies]).
 */
internal data class UnusedDirectDependency(
    /**
     * In group:artifact form. E.g.,
     * 1. "javax.inject:javax.inject"
     * 2. ":my-project"
     */
    val identifier: String,
    /**
     * If this direct dependency has any transitive dependencies that are used, they will be in this set.
     *
     * In group:artifact form. E.g.,
     * 1. "javax.inject:javax.inject"
     * 2. ":my-project"
     */
    val usedTransitiveDependencies: MutableSet<String>
)
