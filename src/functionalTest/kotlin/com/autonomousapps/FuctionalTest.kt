package com.autonomousapps

import org.gradle.testkit.runner.GradleRunner
import kotlin.test.Test
import kotlin.test.assertTrue

@Suppress("FunctionName")
class FunctionalTest {

    @Test fun `can assemble app`() {
        // Setup the test build
        val androidProject = AndroidProject()

        // Run the build
        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("app:assembleDebug")
        runner.withProjectDir(androidProject.projectDir)
        val result = runner.build()

        // Verify the result
        assertTrue {
            result.output.contains("Task :app:assembleDebug")
            result.output.contains("BUILD SUCCESSFUL")
        }
    }
}
