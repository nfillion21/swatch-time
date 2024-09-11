plugins {
    alias(libs.plugins.swatchtime.android.library)
    alias(libs.plugins.swatchtime.android.library.compose)
}

android {
    namespace = "xyz.poolp.feature.time"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.insert.koin)
}