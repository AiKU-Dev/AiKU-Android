plugins {
    alias(libs.plugins.aiku.android.application)
    alias(libs.plugins.aiku.android.application.compose)
    alias(libs.plugins.aiku.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.hyunjung.aiku"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hyunjung.aiku"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.login)
    implementation(projects.feature.groups)

    implementation(projects.core.designsystem)
    implementation(projects.core.data)
    implementation(projects.core.network)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.material3)

    ksp(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)

    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.kotlin.test)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.kotlin.test)
}