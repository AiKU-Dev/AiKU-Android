plugins {
    alias(libs.plugins.aiku.android.library)
}

android {
    namespace = "com.hyunjung.aiku.core.domain"
}

dependencies {
    api(projects.core.common)
    api(projects.core.model)
    api(libs.kotlinx.coroutines.core)
}