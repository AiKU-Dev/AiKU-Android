plugins {
    alias(libs.plugins.aiku.android.library)
}

android {
    namespace = "com.hyunjung.aiku.core.domain"
}

dependencies {
    api(projects.core.model)
    api(libs.kotlinx.coroutines.core)
    api(libs.androidx.paging.compose)
    api(libs.androidx.paging.runtime)
}