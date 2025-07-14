package com.hyunjung.aiku.core.ui.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme

@Composable
fun AikuTabs(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = Color.Transparent,
        contentColor = AiKUTheme.colors.typo,
        indicator = { tabPositions ->
            if (selectedIndex < tabPositions.size) {
                TabRowDefaults.AikuIndicator(
                    height = 4.dp,
                    shape = RoundedCornerShape(2.dp),
                    color = AiKUTheme.colors.green05,
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedIndex])
                )
            }
        },
        modifier = modifier
    ) {
        tabs.forEachIndexed { index, label ->
            val isSelected = index == selectedIndex
            Tab(
                selected = isSelected,
                onClick = { if (!isSelected) onTabSelected(index) },
                unselectedContentColor = AiKUTheme.colors.gray03,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Text(
                    text = label,
                    style = AiKUTheme.typography.subtitle4G
                )
            }
        }
    }
}

@Composable
private fun TabRowDefaults.AikuIndicator(
    modifier: Modifier = Modifier,
    color: Color = AiKUTheme.colors.green05,
    shape: RoundedCornerShape = RoundedCornerShape(2.dp),
    height: Dp = 4.dp
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(height)
            .background(color = color, shape = shape)
    )
}

@Preview(showBackground = true)
@Composable
private fun AikuTabsPreview() {
    AiKUTheme {
        AikuTabs(
            tabs = listOf("멤버", "약속"),
            selectedIndex = 0,
            onTabSelected = {}
        )
    }
}