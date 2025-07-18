plugins {
    alias(libs.plugins.aiku.android.library)
    alias(libs.plugins.aiku.android.library.compose)
}

android {
    namespace = "com.hyunjung.aiku.core.ui"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.designsystem)
    implementation(projects.core.navigation)
}