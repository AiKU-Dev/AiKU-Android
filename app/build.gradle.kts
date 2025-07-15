import java.util.Properties

plugins {
    alias(libs.plugins.aiku.android.application)
    alias(libs.plugins.aiku.android.application.compose)
    alias(libs.plugins.aiku.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.mapsplatform.secrets.plugin)
}

val secrets = Properties().apply {
    rootProject
        .file("secrets.properties")
        .takeIf { it.exists() }
        ?.inputStream()
        ?.use(::load)
}

android {
    namespace = "com.hyunjung.aiku"

    defaultConfig {
        applicationId = "com.hyunjung.aiku"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        secrets.forEach { key, value ->
            buildConfigField(
                "String",
                key as String,
                "\"${value as String}\""
            )
        }

        val kakaoKey = secrets.getProperty("KAKAO_NATIVE_APP_KEY") ?: ""
        manifestPlaceholders["kakaoScheme"] = "kakao$kakaoKey"
        manifestPlaceholders["kakaoKey"] = kakaoKey
    }

    buildFeatures {
        buildConfig = true
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
    implementation(libs.kakao.auth)
    implementation(libs.kakao.common)

    ksp(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)

    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.kotlin.test)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.kotlin.test)
}