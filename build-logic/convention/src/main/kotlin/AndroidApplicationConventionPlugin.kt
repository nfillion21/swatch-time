import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import xyz.poolp.swatchtime.configureKotlinAndroid

class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(project.pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureDefaultConfig()
            }
        }
    }
}

private fun ApplicationExtension.configureDefaultConfig() {
    defaultConfig {
        applicationId = "xyz.poolp.swatchtime"
        targetSdk = 34
        versionCode = 7
        versionName = "1.4.2"

        vectorDrawables {
            useSupportLibrary = true
        }
    }
}