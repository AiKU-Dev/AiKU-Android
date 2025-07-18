package com.hyunjung.aiku.core.ui.component.common

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuIcon
import com.hyunjung.aiku.core.designsystem.component.AikuSurface
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.designsystem.theme.LocalAikuContentColor
import com.hyunjung.aiku.core.navigation.AikuRoute
import com.hyunjung.aiku.core.navigation.currentComposeNavigator
import com.hyunjung.aiku.core.ui.R
import com.hyunjung.aiku.core.ui.preview.AikuPreviewTheme

@Composable
fun AikuNavigationBar(
    currentScreen: AikuRoute,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = AikuNavigationBarDefaults.windowInsets,
) {
    val composeNavigator = currentComposeNavigator
    val borderColor = AiKUTheme.colors.gray02

    AikuSurface(
        color = AiKUTheme.colors.gray01,
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
                            color = borderColor,
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
            AikuNavigationBarDefaults.items.forEach { (labelResId, icon, screen) ->
                AikuNavigationBarItem(
                    label = stringResource(labelResId),
                    icon = {
                        AikuIcon(painter = icon, contentDescription = null)
                    },
                    selected = screen == currentScreen,
                    onClick = { composeNavigator.navigateToTopLevelDestination(screen) },
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
        targetValue = if (selected) AiKUTheme.colors.green05 else AiKUTheme.colors.gray03,
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
        CompositionLocalProvider(LocalAikuContentColor provides color, content = icon)
        Spacer(Modifier.height(AikuNavigationBarDefaults.NavigationBarItemVerticalPadding))
        AikuText(
            text = label,
            style = AiKUTheme.typography.caption1Medium,
            color = color,
        )
    }
}

@Preview
@Composable
private fun AikuNavigationBarPreview() {
    AikuPreviewTheme {
        AikuNavigationBar(AikuRoute.HomeRoute)
    }
}

object AikuNavigationBarDefaults {
    val NavigationBarHeight = 68.dp
    const val ITEM_ANIMATION_DURATION_MILLIS: Int = 100
    val NavigationBarItemHorizontalPadding: Dp = 8.dp
    val NavigationBarItemVerticalPadding: Dp = 4.dp

    val items: List<Triple<Int, Painter, AikuRoute>>
        @Composable get() = listOf(
            Triple(
                R.string.top_level_destination_my_schedule,
                AikuIcons.Schedule,
                AikuRoute.ScheduleRoute
            ),
            Triple(
                R.string.top_level_destination_home,
                AikuIcons.Home,
                AikuRoute.HomeRoute
            ),
            Triple(
                R.string.top_level_destination_my_page,
                AikuIcons.Account,
                AikuRoute.MyPageRoute
            ),
        )

    val windowInsets: WindowInsets
        @Composable
        get() =
            WindowInsets.systemBars.only(
                WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
            )
}