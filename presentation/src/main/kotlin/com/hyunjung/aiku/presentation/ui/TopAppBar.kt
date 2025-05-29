package com.hyunjung.aiku.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuIconButton
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.designsystem.theme.AikuColors
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography
import com.hyunjung.aiku.core.navigation.AikuComposeNavigator
import com.hyunjung.aiku.core.navigation.AikuScreen
import com.hyunjung.aiku.core.navigation.LocalComposeNavigator
import com.hyunjung.aiku.core.navigation.currentComposeNavigator

@Composable
fun AikuAppBar(
    modifier: Modifier = Modifier,
    title: String = "AiKU",
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = AikuColors.White,
        titleContentColor = AikuColors.CobaltBlue,
    ),
) {
    val composeNavigator = currentComposeNavigator

    TopAppBarBase(
        title = {
            Text(
                text = title,
                style = AikuTypography.Headline3_G,
            )
        },
        actions = {
            CompositionLocalProvider(
                LocalContentColor provides Color.Unspecified
            ) {
                AikuIconButton(
                    onClick = { composeNavigator.navigate(route = AikuScreen.AkuChargingStation) },
                    painter = AikuIcons.Aku,
                    contentDescription = "AkuChargingStation",
                    size = 24.dp
                )
            }
        },
        colors = colors,
        centeredTitle = false,
        modifier = modifier,
    )
}

@Composable
fun AikuCenterAlignedTopAppBar(
    title: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    actions: @Composable (RowScope.() -> Unit) = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {
    TopAppBarBase(
        title = title,
        navigationIcon = {},
        actions = actions,
        colors = colors,
        centeredTitle = true,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarBase(
    title: @Composable (() -> Unit),
    actions: @Composable (RowScope.() -> Unit),
    colors: TopAppBarColors,
    centeredTitle: Boolean,
    modifier: Modifier,
    navigationIcon: @Composable (() -> Unit) = {},
) {
    val actionsRow = @Composable {
        Row(
            horizontalArrangement = Arrangement.spacedBy(ActionsSpacing),
            verticalAlignment = Alignment.CenterVertically,
            content = actions
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(TopAppBarHeight)
            .background(colors.containerColor),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(end = NavigationIconStartPadding)
        ) {
            CompositionLocalProvider(
                LocalContentColor provides colors.navigationIconContentColor,
                content = navigationIcon
            )
        }

        Box(
            modifier = Modifier
                .align(if (centeredTitle) Alignment.Center else Alignment.CenterStart)
                .padding(horizontal = TitlePadding)
        ) {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = LocalDensity.current.density,
                    fontScale = 1f,
                ),
                LocalContentColor provides colors.titleContentColor,
                content = title
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = ActionsEndPadding)
        ) {
            CompositionLocalProvider(
                LocalContentColor provides colors.actionIconContentColor,
                content = actionsRow
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AikuAppBarPreview() {
    CompositionLocalProvider(
        LocalComposeNavigator provides AikuComposeNavigator()
    ) {
        AiKUTheme {
            AikuAppBar()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AikuCenterAlignedTopAppBarPreview() {
    AiKUTheme {
        AikuCenterAlignedTopAppBar(
            title = {
                Text(
                    text = "AiKU",
                    style = AikuTypography.Headline3_G,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                titleContentColor = AikuColors.CobaltBlue,
                containerColor = AikuColors.White
            ),
            actions = {
                AikuIconButton(
                    onClick = {},
                    imageVector = Icons.Default.Add,
                    size = 22.dp
                )
                AikuIconButton(
                    onClick = {},
                    imageVector = Icons.Default.DateRange,
                    size = 22.dp
                )
            }
        )
    }
}

private val TopAppBarHeight = 48.dp
private val NavigationIconSize = 24.dp
private val NavigationIconStartPadding = 4.dp
private val TitlePadding = 20.dp
private val ActionsEndPadding = 22.dp
private val ActionsSpacing = 8.dp