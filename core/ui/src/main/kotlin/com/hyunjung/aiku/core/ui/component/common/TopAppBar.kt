package com.hyunjung.aiku.core.ui.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuIconButton
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AikuColors
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography
import com.hyunjung.aiku.core.navigation.AikuScreen
import com.hyunjung.aiku.core.navigation.currentComposeNavigator
import com.hyunjung.aiku.core.ui.preview.AikuPreviewTheme

@Composable
fun AikuLogoTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "AiKU",
    colors: AikuTopAppBarColors = AikuTopAppBarDefaults.topAppBarColors(
        titleContentColor = AikuColors.CobaltBlue,
    ),
) {
    val composeNavigator = currentComposeNavigator

    BaseTopAppBarLayout(
        title = {
            Text(
                text = title,
                style = AikuTypography.Headline3_G,
            )
        },
        actionsContent = {
            CompositionLocalProvider(
                LocalContentColor provides Color.Unspecified
            ) {
                AikuIconButton(
                    onClick = { composeNavigator.navigate(route = AikuScreen.AkuChargingStation) },
                    painter = AikuIcons.Aku,
                    contentDescription = "AkuChargingStation",
                    size = AikuTopAppBarDefaults.ActionIconSize,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = colors.actionIconColor
                    )
                )
            }
        },
        colors = colors,
        centeredTitle = false,
        modifier = modifier,
    )
}

@Composable
fun AikuTopAppBarWithNavigation(
    title: String,
    modifier: Modifier = Modifier,
    actions: @Composable (RowScope.() -> Unit) = {},
    colors: AikuTopAppBarColors = AikuTopAppBarDefaults.topAppBarColors(),
    paddingValues: PaddingValues = PaddingValues(
        vertical = AikuTopAppBarDefaults.VerticalPadding,
        horizontal = AikuTopAppBarDefaults.HorizontalPadding
    )
) {
    val composeNavigator = currentComposeNavigator

    BaseTopAppBarLayout(
        title = {
            Text(
                text = title,
                style = AikuTypography.Subtitle2,
                color = LocalContentColor.current
            )
        },
        navigationIcon = {
            AikuIconButton(
                onClick = { composeNavigator.navigateUp() },
                imageVector = AikuIcons.ArrowBackIosNew,
                contentDescription = "navigateUp",
                size = AikuTopAppBarDefaults.NavigationIconSize
            )
        },
        actionsContent = actions,
        colors = colors,
        centeredTitle = true,
        modifier = modifier,
        paddingValues = paddingValues
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BaseTopAppBarLayout(
    title: @Composable (() -> Unit),
    actionsContent: @Composable RowScope.() -> Unit,
    colors: AikuTopAppBarColors,
    centeredTitle: Boolean,
    modifier: Modifier,
    navigationIcon: @Composable (() -> Unit) = {},
    paddingValues: PaddingValues = PaddingValues(
        vertical = AikuTopAppBarDefaults.VerticalPadding,
        horizontal = AikuTopAppBarDefaults.HorizontalPadding
    )
) {
    val actionsRow = @Composable {
        Row(
            horizontalArrangement = Arrangement.spacedBy(AikuTopAppBarDefaults.ActionsSpacing),
            verticalAlignment = Alignment.CenterVertically,
            content = actionsContent
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(AikuTopAppBarDefaults.TopAppBarHeight)
            .background(colors.containerColor)
            .padding(paddingValues)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
        ) {
            CompositionLocalProvider(
                LocalContentColor provides colors.navigationIconColor,
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
                LocalContentColor provides colors.titleColor,
                content = title
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            CompositionLocalProvider(
                LocalContentColor provides colors.actionIconColor,
                content = actionsRow
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AikuAppBarPreview() {
    AikuPreviewTheme {
        AikuLogoTopAppBar()
    }
}

@Preview(showBackground = true)
@Composable
private fun NavigationAppBarPreview() {
    AikuPreviewTheme {
        AikuTopAppBarWithNavigation(
            title = "<Untitled>",
            actions = {
                AikuIconButton(
                    onClick = {},
                    imageVector = AikuIcons.MoreHoriz,
                    contentDescription = null,
                    size = AikuTopAppBarDefaults.ActionIconSize
                )
            }
        )
    }
}

object AikuTopAppBarDefaults {

    val VerticalPadding: Dp = 8.dp
    val HorizontalPadding: Dp = 20.dp
    val ActionIconSize: Dp = 24.dp
    val NavigationIconSize: Dp = 24.dp
    val ActionsSpacing: Dp = 4.dp
    val TopAppBarHeight: Dp = 52.dp

    val TitleContentColor: Color = AikuColors.Typo
    val ContainerColor: Color = AikuColors.Gray01
    val ActionIconContentColor: Color = Color.Unspecified
    val NavigationIconContentColor: Color = AikuColors.Gray04

    @Composable
    fun topAppBarColors(
        containerColor: Color = ContainerColor,
        titleContentColor: Color = TitleContentColor,
        actionIconContentColor: Color = ActionIconContentColor,
        navigationIconContentColor: Color = NavigationIconContentColor
    ): AikuTopAppBarColors = AikuTopAppBarColors(
        containerColor = containerColor,
        titleColor = titleContentColor,
        actionIconColor = actionIconContentColor,
        navigationIconColor = navigationIconContentColor,
    )
}

data class AikuTopAppBarColors(
    val containerColor: Color,
    val titleColor: Color,
    val navigationIconColor: Color,
    val actionIconColor: Color
)
