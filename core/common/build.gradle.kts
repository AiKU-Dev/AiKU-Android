plugins {
    alias(libs.plugins.aiku.android.library)
    alias(libs.plugins.aiku.android.hilt)
}

android {
    namespace = "com.hyunjung.aiku.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}