plugins {
    alias(libs.plugins.aiku.android.library)
    alias(libs.plugins.aiku.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.hyunjung.aiku.core.network"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.resources)
    implementation(libs.ktor.client.auth)
    implementation(libs.logback.classic)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
}