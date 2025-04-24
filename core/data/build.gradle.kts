plugins {
    alias(libs.plugins.aiku.android.library)
    alias(libs.plugins.aiku.hilt)
}

android {
    namespace = "com.hyunjung.aiku.core.data"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}