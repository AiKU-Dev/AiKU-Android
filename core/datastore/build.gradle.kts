plugins {
    alias(libs.plugins.aiku.android.library)
    alias(libs.plugins.aiku.android.hilt)
}

android {
    namespace = "com.hyunjung.aiku.core.datastore"
}

dependencies {
    implementation(projects.core.datastoreProto)
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(libs.androidx.dataStore)
}