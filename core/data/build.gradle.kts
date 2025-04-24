plugins {
    alias(libs.plugins.aiku.android.library)
    alias(libs.plugins.aiku.hilt)
}

android {
    namespace = "com.hyunjung.aiku.core.data"
}

dependencies {
    api(projects.core.common)
    implementation(libs.kotlinx.serialization.json)
}