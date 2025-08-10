package com.hyunjung.aiku.feature.group.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hyunjung.aiku.core.designsystem.component.AikuLoadingWheel
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.group.GroupSchedule
import com.hyunjung.aiku.core.ui.component.common.EmptyPlaceholder
import com.hyunjung.aiku.core.ui.paging.LazyPagingColumn
import com.hyunjung.aiku.core.ui.preview.GroupScheduleListPreviewParameterProvider
import com.hyunjung.aiku.feature.group.detail.R
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun GroupScheduleTab(
    lazyPagingGroupSchedules: LazyPagingItems<GroupSchedule>,
    onScheduleClick: (Long) -> Unit,
    onCreateSchedule: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        LazyPagingColumn(
            refreshLoadState = lazyPagingGroupSchedules.loadState.refresh,
            isEmpty = lazyPagingGroupSchedules.itemCount == 0,
            loading = { AikuLoadingWheel(modifier = Modifier.align(Alignment.Center)) },
            empty = {
                EmptyPlaceholder(
                    title = stringResource(R.string.group_detail_schedule_tab_empty_title),
                    buttonText = stringResource(R.string.group_detail_schedule_tab_empty_button),
                    onClickButton = onCreateSchedule,
                    modifier = Modifier.align(Alignment.Center)
                )
            },
            error = { throwable ->
                // todo : Error UI 교체
                AikuText(
                    text = "${throwable.message}",
                    style = AiKUTheme.typography.body1SemiBold
                )
            },
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // todo : stick header 수정
            item {
                AikuText(
                    text = stringResource(
                        R.string.group_detail_schedule_tab_schedule_status_summary,
                        0,
                        4,
                    ),
                    style = AiKUTheme.typography.caption1SemiBold,
                )
            }

            items(
                count = lazyPagingGroupSchedules.itemCount,
                key = { lazyPagingGroupSchedules[it]?.id ?: "group-schedule-$it" }
            ) { index ->
                lazyPagingGroupSchedules[index]?.let { groupSchedule ->
                    GroupScheduleCard(
                        onClick = { onScheduleClick(groupSchedule.id) },
                        groupSchedule = groupSchedule,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun GroupScheduleTabPreview(
    @PreviewParameter(GroupScheduleListPreviewParameterProvider::class)
    groupSchedules: List<GroupSchedule>
) {
    AiKUTheme {
        GroupScheduleTab(
            lazyPagingGroupSchedules = flowOf(
                PagingData.from(
                    data = groupSchedules,
                    sourceLoadStates =
                        LoadStates(
                            refresh = LoadState.NotLoading(false),
                            append = LoadState.NotLoading(false),
                            prepend = LoadState.NotLoading(false),
                        ),
                ),
            ).collectAsLazyPagingItems(),
            onScheduleClick = {},
            onCreateSchedule = {},
        )
    }
}