package com.hyunjung.aiku.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.hyunjung.aiku.core.model.schedule.ScheduleStatus
import com.hyunjung.aiku.core.model.schedule.UpcomingSchedule
import com.hyunjung.aiku.core.ui.paging.LazyPagingRow
import com.hyunjung.aiku.core.ui.preview.UpcomingSchedulePreviewParameterProvider
import com.hyunjung.aiku.feature.home.R
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
internal fun UpcomingScheduleContent(
    lazyPagingUpcomingSchedules: LazyPagingItems<UpcomingSchedule>,
    onScheduleClick: (groupId: Long, scheduleId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val formatter = remember { DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.getDefault()) }
    val today = remember { LocalDate.now().format(formatter) }

    Column(modifier = modifier) {
        AikuText(
            text = today,
            style = AiKUTheme.typography.subtitle2G,
        )
        Spacer(Modifier.height(8.dp))
        AikuText(
            text = stringResource(R.string.feature_home_schedule_title),
            style = AiKUTheme.typography.body2,
        )
        Spacer(Modifier.height(12.dp))
        LazyPagingRow(
            refreshLoadState = lazyPagingUpcomingSchedules.loadState.refresh,
            isEmpty = lazyPagingUpcomingSchedules.itemCount == 0,
            loading = { AikuLoadingWheel(Modifier.align(Alignment.Center)) },
            empty = { UpcomingSchedulePlaceholder(Modifier.fillMaxHeight()) },
            error = { throwable ->
                // todo : Error UI 교체
                AikuText(
                    text = "${throwable.message}",
                    style = AiKUTheme.typography.body1SemiBold
                )
            },
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(132.dp)
        ) {
            items(
                count = lazyPagingUpcomingSchedules.itemCount,
                key = { lazyPagingUpcomingSchedules[it]?.id ?: "schedule-$it" }
            ) { index ->
                lazyPagingUpcomingSchedules[index]?.let { schedule ->
                    UpcomingScheduleCard(
                        groupName = schedule.groupName,
                        location = schedule.location.locationName,
                        isRunning = schedule.scheduleStatus == ScheduleStatus.RUNNING,
                        time = schedule.scheduleTime,
                        onClick = {
                            onScheduleClick(
                                schedule.groupId,
                                schedule.id
                            )
                        },
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun UpcomingSchedulePreview(
    @PreviewParameter(UpcomingSchedulePreviewParameterProvider::class)
    upcomingUpcomingSchedules: List<UpcomingSchedule>,
) {

    val lazyPagingUpcomingSchedules = flowOf(
        PagingData.from(
            data = upcomingUpcomingSchedules,
            sourceLoadStates =
                LoadStates(
                    refresh = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                ),
        ),
    ).collectAsLazyPagingItems()

    AiKUTheme {
        UpcomingScheduleContent(
            lazyPagingUpcomingSchedules = lazyPagingUpcomingSchedules,
            onScheduleClick = { _, _ -> },
        )
    }
}