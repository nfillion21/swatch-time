plugins {
    alias(libs.plugins.swatchtime.android.library)
}

android {
    namespace = "xyz.poolp.core.common"
}

dependencies {
    implementation(libs.kotlinx.dateTime)
}