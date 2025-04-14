package com.hyunjung.aiku.core.designsystem.component

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.designsystem.theme.AikuColors
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import kotlin.math.abs


@Composable
fun <T> Picker(
    items: List<T>,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    visibleItemsCount: Int = 3,
    textStyle: TextStyle = AikuTypography.Subtitle3,
    selectedTextStyle: TextStyle = AikuTypography.Subtitle2,
    itemPadding: PaddingValues = PaddingValues(8.dp),
    isInfinity: Boolean = false
) {
    val density = LocalDensity.current
    val visibleItemsMiddle = remember { visibleItemsCount / 2 }

    val adjustedItems = if (!isInfinity) {
        listOf(null) + items + listOf(null)
    } else {
        items
    }

    val listScrollCount = if (isInfinity) {
        Int.MAX_VALUE
    } else {
        adjustedItems.size
    }

    val listScrollMiddle = remember { listScrollCount / 2 }
    val listStartIndex = remember {
        if (isInfinity) {
            listScrollMiddle - listScrollMiddle % adjustedItems.size - visibleItemsMiddle + startIndex
        } else {
            startIndex
        }
    }

    fun getItem(index: Int) = adjustedItems[index % adjustedItems.size]

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val itemHeight = with(density) {
        selectedTextStyle.lineHeight.toDp() + itemPadding.calculateTopPadding() + itemPadding.calculateBottomPadding()
    }

    LaunchedEffect(adjustedItems, listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .mapNotNull { index ->
                getItem(index + visibleItemsMiddle)
            }
            .distinctUntilChanged()
            .collect { item -> onItemSelected(item) }
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            modifier = modifier
                .align(Alignment.Center)
                .wrapContentSize()
                .height(itemHeight * visibleItemsCount)
        ) {
            items(
                items = adjustedItems,
                key = { it.hashCode() },
            ) { item ->
                val fraction by remember {
                    derivedStateOf {
                        val currentItem =
                            listState.layoutInfo.visibleItemsInfo.firstOrNull { it.key == item.hashCode() }
                        currentItem?.offset?.let { offset ->
                            val itemHeightPx = with(density) { itemHeight.toPx() }
                            val fraction =
                                (offset - itemHeightPx * visibleItemsMiddle) / itemHeightPx
                            abs(fraction)
                        } ?: 0f
                    }
                }

                Text(
                    text = item?.toString() ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = textStyle.copy(
                        fontSize = lerp(
                            selectedTextStyle.fontSize,
                            textStyle.fontSize,
                            fraction
                        ),
                        fontWeight = if (fraction < 0.1f) {
                            selectedTextStyle.fontWeight
                        } else {
                            textStyle.fontWeight
                        },
                        color = lerp(
                            AikuColors.Typo,
                            AikuColors.Gray03,
                            fraction
                        )
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .height(itemHeight)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                        .fillParentMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PickerPreview() {
    AiKUTheme {
        Picker(
            items = (2025..2035).toList(),
            onItemSelected = {},
        )
    }
}