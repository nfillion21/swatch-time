package xyz.poolp.swatchtime

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose (
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {

        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.12"
        }
    }

    dependencies {
        add("implementation", libs.findLibrary("androidx.core.ktx").get())
        add("implementation", libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
        add("implementation", libs.findLibrary("androidx.activity.compose").get())

        val composeBom = libs.findLibrary("androidx.compose.bom").get()
        add("implementation", platform(composeBom))
        add("implementation", libs.findLibrary("androidx.ui").get())
        add("implementation", libs.findLibrary("androidx.ui.graphics").get())
        add("implementation", libs.findLibrary("androidx.ui.tooling.preview").get())
        add("implementation", libs.findLibrary("androidx.material3").get())
        add("debugImplementation", libs.findLibrary("androidx.ui.tooling").get())
    }
}