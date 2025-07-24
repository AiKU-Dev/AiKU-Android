package com.hyunjung.aiku.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hyunjung.aiku.core.designsystem.component.AikuScaffold
import com.hyunjung.aiku.core.model.group.GroupSummary
import com.hyunjung.aiku.core.model.schedule.Schedule
import com.hyunjung.aiku.core.navigation.AikuRoute
import com.hyunjung.aiku.core.ui.component.common.AikuLogoTopAppBar
import com.hyunjung.aiku.core.ui.component.common.AikuNavigationBar
import com.hyunjung.aiku.core.ui.preview.AikuPreviewTheme
import com.hyunjung.aiku.core.ui.preview.PreviewParameterData.groupSummaries
import com.hyunjung.aiku.core.ui.preview.PreviewParameterData.schedules
import com.hyunjung.aiku.feature.home.component.CreateGroupDialog
import com.hyunjung.aiku.feature.home.component.GroupSummaryContent
import com.hyunjung.aiku.feature.home.component.UpcomingScheduleContent
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    onScheduleClick: (groupId: Long, scheduleId: Long) -> Unit,
    onGroupSummaryClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val lazyPagingSchedules = viewModel.schedulePagingData.collectAsLazyPagingItems()
    val lazyPagingGroupSummaries = viewModel.groupSummaryPagingData.collectAsLazyPagingItems()

    val userNickname by viewModel.userNickName.collectAsStateWithLifecycle()
    var showCreateGroupDialog by remember { mutableStateOf(false) }

    if (showCreateGroupDialog) {
        CreateGroupDialog(
            onDismiss = { showCreateGroupDialog = false },
            onCreateGroup = {
                viewModel.createGroup(it)
                lazyPagingGroupSummaries.refresh()
            }
        )
    }

    HomeScreen(
        userNickname = userNickname,
        lazyPagingSchedules = lazyPagingSchedules,
        lazyPagingGroupSummaries = lazyPagingGroupSummaries,
        onScheduleClick = onScheduleClick,
        onGroupSummaryClick = onGroupSummaryClick,
        onShowCreateGroupDialog = { showCreateGroupDialog = true },
        modifier = modifier,
    )
}

@Composable
private fun HomeScreen(
    userNickname: String,
    lazyPagingSchedules: LazyPagingItems<Schedule>,
    lazyPagingGroupSummaries: LazyPagingItems<GroupSummary>,
    onScheduleClick: (groupId: Long, scheduleId: Long) -> Unit,
    onGroupSummaryClick: (Long) -> Unit,
    onShowCreateGroupDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {

    AikuScaffold(
        modifier = modifier,
        topBar = { AikuLogoTopAppBar() },
        bottomBar = { AikuNavigationBar(AikuRoute.HomeRoute) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
        ) {
            UpcomingScheduleContent(
                lazyPagingSchedules = lazyPagingSchedules,
                onScheduleClick = onScheduleClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )
            GroupSummaryContent(
                userNickname = userNickname,
                lazyPagingGroupSummaries = lazyPagingGroupSummaries,
                onGroupSummaryClick = onGroupSummaryClick,
                onShowCreateGroupDialog = onShowCreateGroupDialog,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {

    val lazyPagingSchedules = flowOf(
        PagingData.from(
            data = schedules,
            sourceLoadStates =
                LoadStates(
                    refresh = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                ),
        ),
    ).collectAsLazyPagingItems()

    val lazyPagingGroupSummaries = flowOf(
        PagingData.from(
            data = groupSummaries,
            sourceLoadStates =
                LoadStates(
                    refresh = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                ),
        ),
    ).collectAsLazyPagingItems()


    AikuPreviewTheme {
        HomeScreen(
            userNickname = "아이쿠",
            lazyPagingSchedules = lazyPagingSchedules,
            lazyPagingGroupSummaries = lazyPagingGroupSummaries,
            onScheduleClick = { _, _ -> },
            onGroupSummaryClick = {},
            onShowCreateGroupDialog = {},
        )
    }
}