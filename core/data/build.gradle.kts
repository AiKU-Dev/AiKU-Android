plugins {
    alias(libs.plugins.aiku.android.library)
    alias(libs.plugins.aiku.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.hyunjung.aiku.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    api(projects.core.common)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.kotlinx.coroutines.test)
}