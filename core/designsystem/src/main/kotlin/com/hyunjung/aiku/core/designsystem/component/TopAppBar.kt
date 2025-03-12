package com.hyunjung.aiku.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.R
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.designsystem.theme.AikuColors
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AikuTopAppBar(
    title: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector? = null,
    actions: @Composable (RowScope.() -> Unit) = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent
    ),
    onNavigationClick: () -> Unit = {},
) {
    AikuTopAppBarBase(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        colors = colors,
        onNavigationClick = onNavigationClick,
        titleAlignment = Alignment.CenterStart,
        centeredTitle = false
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AikuCenterAlignedTopAppBar(
    title: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector? = null,
    actions: @Composable (RowScope.() -> Unit) = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent
    ),
    onNavigationClick: () -> Unit = {},
) {
    AikuTopAppBarBase(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        colors = colors,
        onNavigationClick = onNavigationClick,
        titleAlignment = Alignment.Center,
        centeredTitle = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AikuTopAppBarBase(
    title: @Composable (() -> Unit),
    modifier: Modifier,
    navigationIcon: ImageVector?,
    actions: @Composable (RowScope.() -> Unit),
    colors: TopAppBarColors,
    onNavigationClick: () -> Unit,
    titleAlignment: Alignment,
    centeredTitle: Boolean
) {
    val titleOffset = if (navigationIcon != null && !centeredTitle) {
        NavigationIconSize + TitleStartPadding
    } else if (!centeredTitle) {
        TitleStartPadding
    } else {
        0.dp
    }

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
        if (navigationIcon != null) {
            Box(
                modifier = Modifier
                    .padding(start = NavigationIconStartPadding)
                    .align(Alignment.CenterStart)
            ) {
                AikuIconButton(
                    onClick = onNavigationClick,
                    imageVector = navigationIcon,
                    contentDescription = stringResource(R.string.core_designsystem_navigation_icon_description),
                    iconSize = NavigationIconSize
                )
            }
        }

        Box(
            modifier = Modifier
                .align(titleAlignment)
                .offset(x = titleOffset)
        ) {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = LocalDensity.current.density,
                    fontScale = 1f,
                ),
                LocalContentColor provides colors.titleContentColor,
                LocalTextStyle provides AikuTypography.Subtitle3_Bold,
                content = title
            )
        }

        Box(
            modifier = Modifier
                .padding(end = ActionsEndPadding)
                .align(Alignment.CenterEnd),
        ) {
            CompositionLocalProvider(
                LocalContentColor provides colors.actionIconContentColor,
                content = actionsRow
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun AikuTopAppBarPreview() {
    AiKUTheme {
        AikuTopAppBar(
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
                    iconSize = 22.dp
                )
                AikuIconButton(
                    onClick = {},
                    imageVector = Icons.Default.DateRange,
                    iconSize = 22.dp
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
                    iconSize = 22.dp
                )
                AikuIconButton(
                    onClick = {},
                    imageVector = Icons.Default.DateRange,
                    iconSize = 22.dp
                )
            }
        )
    }
}

private val TopAppBarHeight = 48.dp
private val NavigationIconSize = 24.dp
private val NavigationIconStartPadding = 4.dp
private val TitleStartPadding = 20.dp
private val ActionsEndPadding = 22.dp
private val ActionsSpacing = 8.dp