plugins {
    alias(libs.plugins.aiku.android.library)
    alias(libs.plugins.aiku.android.library.compose)
}

android {
    namespace = "com.hyunjung.aiku.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.util)

    implementation(libs.androidx.compose.material.iconsExtended)
}