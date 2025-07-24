package com.hyunjung.aiku.core.ui.paging

import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState

@Composable
fun LazyPagingRow(
    refreshLoadState: LoadState,
    isEmpty: Boolean,
    loading: @Composable () -> Unit,
    error: @Composable (Throwable) -> Unit,
    empty: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal =
        if (!reverseLayout) Arrangement.Start else Arrangement.End,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    overscrollEffect: OverscrollEffect? = rememberOverscrollEffect(),
    content: LazyListScope.() -> Unit
) {
    RefreshStateLayout(
        refreshLoadState = refreshLoadState,
        isEmpty = isEmpty,
        empty = empty,
        modifier = modifier,
        error = error,
        loading = loading,
        content = {
            LazyRow(
                state = state,
                contentPadding = contentPadding,
                reverseLayout = reverseLayout,
                horizontalArrangement = horizontalArrangement,
                verticalAlignment = verticalAlignment,
                flingBehavior = flingBehavior,
                userScrollEnabled = userScrollEnabled,
                overscrollEffect = overscrollEffect,
                content = content
            )
        }
    )
}

@Composable
private fun RefreshStateLayout(
    refreshLoadState: LoadState,
    isEmpty: Boolean,
    empty: @Composable () -> Unit,
    error: @Composable (Throwable) -> Unit,
    loading: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (refreshLoadState) {
            is LoadState.Loading -> loading()
            is LoadState.Error -> error(refreshLoadState.error)
            is LoadState.NotLoading -> {
                if (isEmpty) {
                    empty()
                } else {
                    content()
                }
            }
        }
    }
}
