import java.util.Properties

plugins {
    alias(libs.plugins.aiku.android.application)
    alias(libs.plugins.aiku.android.application.compose)
    alias(libs.plugins.aiku.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.mapsplatform.secrets.plugin)
}

android {
    namespace = "com.hyunjung.aiku"

    defaultConfig {
        applicationId = "com.hyunjung.aiku"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val secretsFile = rootProject.file("secrets.properties")
        val kakaoKey = Properties().run {
            if (secretsFile.exists()) secretsFile.inputStream().use { load(it) }
            getProperty("KAKAO_NATIVE_APP_KEY", "")
        }

        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"$kakaoKey\"")

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
    implementation(projects.feature.auth)
    implementation(projects.feature.splash)
    implementation(projects.feature.profile)

    implementation(projects.core.designsystem)
    implementation(projects.core.data)
    implementation(projects.core.network)
    implementation(projects.core.navigation)
    implementation(projects.core.ui)
    implementation(projects.core.model)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.kakao.common)
    implementation(libs.kakao.auth)

    ksp(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)

    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.kotlin.test)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.kotlin.test)
}