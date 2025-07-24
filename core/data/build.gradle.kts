plugins {
    alias(libs.plugins.aiku.android.library)
    alias(libs.plugins.aiku.android.hilt)
}

android {
    namespace = "com.hyunjung.aiku.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation(projects.core.authSocial)
    implementation(projects.core.authToken)
    implementation(projects.core.datastore)
    implementation(projects.core.domain)
    implementation(projects.core.network)
    implementation(projects.core.common)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.androidx.paging.testing)
}