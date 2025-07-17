plugins {
    alias(libs.plugins.aiku.android.library)
    alias(libs.plugins.aiku.android.library.compose)
}

android {
    namespace = "com.hyunjung.aiku.core.terms"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
}