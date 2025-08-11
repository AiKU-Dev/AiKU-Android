package com.hyunjung.aiku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val appEntryState by viewModel.appEntryState.collectAsStateWithLifecycle()
            val appState = rememberAikuAppState()

            CompositionLocalProvider(
                LocalComposeNavigator provides composeNavigator,
            ) {
                AiKUTheme {
                    AikuApp(
                        appState = appState,
                        appEntryState = appEntryState
                    )
                }
            }
        }
    }
}