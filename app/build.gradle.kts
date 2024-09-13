plugins {
    alias(libs.plugins.swatchtime.android.application)
    alias(libs.plugins.swatchtime.android.application.compose)
}

android {
    namespace = "xyz.poolp.swatchtime"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":feature:time"))
    implementation(libs.insert.koin)
}