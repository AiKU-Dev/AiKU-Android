package com.hyunjung.aiku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.navigation.AikuRoute
import com.hyunjung.aiku.core.navigation.AppComposeNavigator
import com.hyunjung.aiku.core.navigation.LocalComposeNavigator
import com.hyunjung.aiku.ui.AikuApp
import com.hyunjung.aiku.ui.rememberAikuAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var composeNavigator: AppComposeNavigator<AikuRoute>

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val appState = rememberAikuAppState()
            CompositionLocalProvider(
                LocalComposeNavigator provides composeNavigator,
            ) {
                AiKUTheme { AikuApp(appState) }
            }
        }
    }
}