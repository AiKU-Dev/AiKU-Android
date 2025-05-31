package com.hyunjung.aiku.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuIconButton
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AikuColors
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography
import com.hyunjung.aiku.core.navigation.AikuScreen
import com.hyunjung.aiku.core.navigation.currentComposeNavigator

private val TopAppBarPadding = 20.dp
private val NavigationIconSize = 24.dp
private val ActionsSpacing = 8.dp

@Composable
fun AikuLogoAppBar(
    modifier: Modifier = Modifier,
    title: String = "AiKU",
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = AikuColors.Gray01,
        titleContentColor = AikuColors.CobaltBlue,
    ),
    paddingValues: PaddingValues = PaddingValues(TopAppBarPadding),
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
        paddingValues = paddingValues
    )
}

@Composable
fun AikuBackAppBar(
    title: String,
    modifier: Modifier = Modifier,
    actions: @Composable (RowScope.() -> Unit) = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        titleContentColor = AikuColors.Typo,
        containerColor = AikuColors.Gray01,
        actionIconContentColor = AikuColors.Gray04,
        navigationIconContentColor = AikuColors.Gray04,
    ),
    paddingValues: PaddingValues = PaddingValues(TopAppBarPadding)
) {
    val composeNavigator = currentComposeNavigator

    TopAppBarBase(
        title = {
            Text(
                text = title,
                style = AikuTypography.Subtitle2,
            )
        },
        navigationIcon = {
            AikuIconButton(
                onClick = { composeNavigator.navigateUp() },
                imageVector = AikuIcons.ArrowBackIosNew,
                contentDescription = "navigateUp",
                size = NavigationIconSize
            )
        },
        actions = actions,
        colors = colors,
        centeredTitle = true,
        modifier = modifier,
        paddingValues = paddingValues
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
    paddingValues: PaddingValues,
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
            .background(colors.containerColor)
            .padding(paddingValues),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
        ) {
            CompositionLocalProvider(
                LocalContentColor provides colors.navigationIconContentColor,
                content = navigationIcon
            )
        }

        Box(
            modifier = Modifier
                .align(if (centeredTitle) Alignment.Center else Alignment.CenterStart)
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
    AikuPreviewTheme {
        AikuLogoAppBar()
    }
}

@Preview(showBackground = true)
@Composable
private fun NavigationAppBarPreview() {
    AikuPreviewTheme {
        AikuBackAppBar(
            title = stringResource(android.R.string.untitled),
            actions = {
                AikuIconButton(
                    onClick = {},
                    imageVector = AikuIcons.MoreHoriz,
                    contentDescription = null,
                    size = 24.dp
                )
            }
        )
    }
}