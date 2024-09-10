import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import xyz.poolp.swatchtime.configureAndroidCompose

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            project.pluginManager.apply("com.android.library")

            extensions.configure<LibraryExtension> {
                configureAndroidCompose(this)
            }
        }
    }
}