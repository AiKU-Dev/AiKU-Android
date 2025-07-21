plugins {
    alias(libs.plugins.aiku.android.library)
    alias(libs.plugins.aiku.android.hilt)
}

android {
    namespace = "com.hyunjung.aiku.core.auth.social"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.kakao.auth)
    implementation(libs.kakao.common)
    implementation(libs.kakao.user)
}