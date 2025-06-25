package com.hyunjung.aiku

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = 35

        defaultConfig {
            minSdk = 21
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }
    }

    dependencies {
        "coreLibraryDesugaring"(libs.findLibrary("android.desugarJdkLibs").get())
    }
}

internal fun Project.configureKotlinAndroid(
    extension: KotlinAndroidProjectExtension,
) {
    val warningsAsErrors: String? by project

    extension.apply {
        compilerOptions {
            allWarningsAsErrors = warningsAsErrors.toBoolean()
            freeCompilerArgs.set(
                freeCompilerArgs.getOrElse(emptyList()) + listOf(
                    "-Xcontext-receivers",
                    "-Xopt-in=kotlin.RequiresOptIn",
                    // Enable experimental coroutines APIs, including Flow
                    "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    // Enable experimental compose APIs
                    "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                    "-Xopt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi",
                    "-Xopt-in=androidx.compose.animation.ExperimentalSharedTransitionApi",
                )
            )

            // Set JVM target to 17
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}