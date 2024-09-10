package xyz.poolp.relaxing

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose (
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {

        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("kotlinCompilerExtension").get().toString()
        }
    }

    dependencies {
        val composeBom = libs.findLibrary("compose.bom").get()
        add("implementation", platform(composeBom))
        add("androidTestImplementation", platform(composeBom))
        add("implementation", libs.findLibrary("compose.ui").get())
        add("implementation", libs.findLibrary("compose.ui.tooling.preview").get())
        add("implementation", libs.findLibrary("compose.material3").get())
        add("debugImplementation", libs.findLibrary("compose.ui.tooling").get())

        add("debugImplementation", libs.findLibrary("compose.ui.test.manifest").get())
        //add("androidTestImplementation", libs.findLibrary("compose.ui.test.junit4").get())

        add("implementation", libs.findLibrary("activity.compose").get())
        add("implementation", libs.findLibrary("navigation.compose").get())

        add("implementation", libs.findLibrary("androidx.core.ktx").get())
        add("implementation", libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
        add("implementation", libs.findLibrary("androidx.lifecycle.runtime.compose").get())
        add("androidTestImplementation", libs.findLibrary("androidx.junit").get())
        add("androidTestImplementation", libs.findLibrary("androidx.espresso.core").get())

        add("testImplementation", libs.findLibrary("junit").get())

        add("implementation", libs.findLibrary("navigation.compose").get())
    }
}