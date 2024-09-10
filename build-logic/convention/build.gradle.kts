plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidLibraryComposeConventionPlugin") {
            id = "swatchtime.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidApplicationComposeConventionPlugin") {
            id = "swatchtime.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplicationConventionPlugin") {
            id = "swatchtime.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryConventionPlugin") {
            id = "swatchtime.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}
