plugins {
    alias(libs.plugins.aiku.android.library)
}

android {
    namespace = "com.hyunjung.aiku.core.auth.token"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}