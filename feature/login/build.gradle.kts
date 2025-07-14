plugins {
    alias(libs.plugins.aiku.android.feature)
    alias(libs.plugins.aiku.android.library.compose)
    alias(libs.plugins.aiku.android.hilt)
}

android {
    namespace = "com.hyunjung.aiku.feature.login"
}