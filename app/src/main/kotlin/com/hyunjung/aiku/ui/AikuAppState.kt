package com.hyunjung.aiku.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAikuAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): AikuAppState =
    remember(
        coroutineScope,
        navController,
    ) {
        AikuAppState(
            navController = navController,
            coroutineScope = coroutineScope,
        )
    }

@Stable
class AikuAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
)