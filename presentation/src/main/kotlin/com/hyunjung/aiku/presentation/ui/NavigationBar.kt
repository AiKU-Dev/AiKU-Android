package com.hyunjung.aiku.presentation.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuSurface
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.designsystem.theme.AikuColors
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography
import com.hyunjung.aiku.core.navigation.AikuComposeNavigator
import com.hyunjung.aiku.core.navigation.AikuScreen
import com.hyunjung.aiku.core.navigation.LocalComposeNavigator
import com.hyunjung.aiku.core.navigation.currentComposeNavigator


@Composable
fun AikuNavigationBar(
    currentScreen: AikuScreen,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = AikuNavigationBarDefaults.windowInsets,
) {
    val composeNavigator = currentComposeNavigator

    val labels = listOf("내 약속", "홈", "마이")
    val screens = listOf(
        AikuScreen.Schedule,
        AikuScreen.Home,
        AikuScreen.MyPage,
    )
    val icons = listOf(
        AikuIcons.Schedule,
        AikuIcons.Home,
        AikuIcons.Account,
    )

    AikuSurface(
        color = AikuColors.Gray01,
        modifier = modifier
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(windowInsets)
                    .defaultMinSize(minHeight = AikuNavigationBarDefaults.NavigationBarHeight)
                    .selectableGroup()
                    .drawBehind {
                        drawLine(
                            color = AikuColors.Gray02,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1.dp.toPx()
                        )
                    },
            horizontalArrangement = Arrangement.spacedBy(
                AikuNavigationBarDefaults.NavigationBarItemHorizontalPadding
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            labels.forEachIndexed { index, label ->
                val screen = screens[index]
                AikuNavigationBarItem(
                    label = label,
                    icon = {
                        Icon(
                            painter = icons[index],
                            contentDescription = null,
                        )
                    },
                    selected = screen == currentScreen,
                    onClick = { composeNavigator.navigateAndClearBackStack(screen) },
                )
            }
        }
    }
}

@Composable
private fun RowScope.AikuNavigationBarItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource? = null
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }

    val color by animateColorAsState(
        targetValue = if (selected) AikuColors.Green05 else AikuColors.Gray03,
        animationSpec = tween(AikuNavigationBarDefaults.ITEM_ANIMATION_DURATION_MILLIS)
    )

    Column(
        modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.Tab,
                interactionSource = interactionSource,
                indication = null,
            )
            .defaultMinSize(minHeight = AikuNavigationBarDefaults.NavigationBarHeight)
            .weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides color, content = icon)
        Spacer(Modifier.height(AikuNavigationBarDefaults.NavigationBarItemVerticalPadding))
        Text(
            text = label,
            style = AikuTypography.Caption1_Medium,
            color = color,
        )
    }
}

@Preview
@Composable
private fun AikuNavigationBarPreview() {
    CompositionLocalProvider(
        LocalComposeNavigator provides AikuComposeNavigator()
    ) {
        AiKUTheme {
            AikuNavigationBar(AikuScreen.Home)
        }
    }
}

object AikuNavigationBarDefaults {
    val NavigationBarHeight = 68.dp
    const val ITEM_ANIMATION_DURATION_MILLIS: Int = 100
    val NavigationBarItemHorizontalPadding: Dp = 8.dp
    val NavigationBarItemVerticalPadding: Dp = 4.dp

    val windowInsets: WindowInsets
        @Composable
        get() =
            WindowInsets.systemBars.only(
                WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
            )
}