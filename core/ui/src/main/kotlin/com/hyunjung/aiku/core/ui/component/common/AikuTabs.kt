package com.hyunjung.aiku.core.ui.component.common

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuHorizontalDivider
import com.hyunjung.aiku.core.designsystem.component.AikuTab
import com.hyunjung.aiku.core.designsystem.component.AikuTabRow
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme

@Composable
fun AikuTabs(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    AikuTabRow(
        selectedTabIndex = selectedIndex,
        divider = {
            AikuHorizontalDivider(
                color = AiKUTheme.colors.gray02
            )
        },
        modifier = modifier
    ) {
        tabs.forEachIndexed { index, label ->
            val isSelected = index == selectedIndex
            AikuTab(
                selected = isSelected,
                onClick = { if (!isSelected) onTabSelected(index) },
                unselectedContentColor = AiKUTheme.colors.gray03,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                AikuText(
                    text = label,
                    style = AiKUTheme.typography.subtitle4G
                )
            }
        }
    }
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