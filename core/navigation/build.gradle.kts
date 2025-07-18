plugins {
    alias(libs.plugins.aiku.android.library)
    alias(libs.plugins.aiku.android.library.compose)
    alias(libs.plugins.aiku.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.hyunjung.aiku.core.navigation"
}

dependencies{
    implementation(projects.core.model)
    api(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}