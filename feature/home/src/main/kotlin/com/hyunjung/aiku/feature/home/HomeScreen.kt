package com.hyunjung.aiku.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuButtonDefaults
import com.hyunjung.aiku.core.designsystem.component.AikuIcon
import com.hyunjung.aiku.core.designsystem.component.AikuLoadingWheel
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.group.GroupSummary
import com.hyunjung.aiku.core.model.schedule.Location
import com.hyunjung.aiku.core.model.schedule.Schedule
import com.hyunjung.aiku.core.model.schedule.ScheduleStatus
import com.hyunjung.aiku.core.navigation.AikuRoute
import com.hyunjung.aiku.core.navigation.currentComposeNavigator
import com.hyunjung.aiku.core.ui.component.common.AikuLogoTopAppBar
import com.hyunjung.aiku.core.ui.component.common.AikuNavigationBar
import com.hyunjung.aiku.core.ui.component.common.AikuNavigationBarDefaults
import com.hyunjung.aiku.core.ui.component.common.AikuTopAppBarDefaults
import com.hyunjung.aiku.core.ui.component.common.EmptyPlaceholder
import com.hyunjung.aiku.core.ui.component.dialog.CreateGroupDialog
import com.hyunjung.aiku.core.ui.component.group.GroupOverviewCard
import com.hyunjung.aiku.core.ui.component.schedule.UpcomingScheduleCard
import com.hyunjung.aiku.core.ui.component.schedule.UpcomingSchedulePlaceholder
import com.hyunjung.aiku.core.ui.preview.AikuPreviewTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.hyunjung.aiku.core.ui.R as UiRes

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val userNickname by viewModel.userNickName.collectAsStateWithLifecycle()
    val isCreateGroupDialogVisible by viewModel.isCreateGroupDialogVisible.collectAsStateWithLifecycle()

    HomeScreen(
        userNickname = userNickname,
        schedulePagingData = viewModel.schedulePagingData,
        groupSummaryPagingData = viewModel.groupSummaryPagingData,
        isCreateGroupDialogVisible = isCreateGroupDialogVisible,
        onOpenCreateGroupDialog = viewModel::openCreateGroupDialog,
        onDismissCreateGroupDialog = viewModel::dismissCreateGroupDialog,
        onCreateGroup = viewModel::createGroup,
        modifier = modifier,
    )
}

@Composable
private fun HomeScreen(
    userNickname: String,
    schedulePagingData: Flow<PagingData<Schedule>>,
    groupSummaryPagingData: Flow<PagingData<GroupSummary>>,
    isCreateGroupDialogVisible: Boolean,
    onOpenCreateGroupDialog: () -> Unit,
    onDismissCreateGroupDialog: () -> Unit,
    onCreateGroup: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val composeNavigator = currentComposeNavigator

    if (isCreateGroupDialogVisible) {
        CreateGroupDialog(
            onDismiss = onDismissCreateGroupDialog,
            onCreateGroup = { onCreateGroup(it) }
        )
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = AiKUTheme.colors.gray01),
    ) {
        AikuLogoTopAppBar(
            modifier = Modifier.align(Alignment.TopStart),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = AikuTopAppBarDefaults.TopAppBarHeight,
                    bottom = AikuNavigationBarDefaults.NavigationBarHeight
                ),
        ) {
            TodaySchedulesSection(
                title = stringResource(R.string.feature_home_schedule_title),
                schedulePagingData = schedulePagingData,
                onScheduleClick = { groupId, scheduleId ->
                    composeNavigator.navigate(AikuRoute.ScheduleDetailRoute(groupId, scheduleId))
                },
            )
            Spacer(Modifier.height(12.dp))
            GroupOverviewsSection(
                title = stringResource(R.string.feature_home_group_title, userNickname),
                groupSummaryPagingData = groupSummaryPagingData,
                onGroupClick = {
                    composeNavigator.navigate(AikuRoute.GroupDetailRoute(it))
                },
                onOpenCreateGroupDialog = onOpenCreateGroupDialog,
                modifier = Modifier.weight(1f)
            )
        }
        AikuNavigationBar(
            currentScreen = AikuRoute.HomeRoute,
            modifier = Modifier.align(Alignment.BottomStart),
        )
    }
}

@Composable
private fun TodaySchedulesSection(
    schedulePagingData: Flow<PagingData<Schedule>>,
    onScheduleClick: (groupId: Long, scheduleId: Long) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
) {
    val formatter = remember { DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.getDefault()) }
    val today = remember { LocalDate.now().format(formatter) }

    val lazyPagingSchedules = schedulePagingData.collectAsLazyPagingItems()
    Column(modifier = modifier) {
        AikuText(
            text = today,
            style = AiKUTheme.typography.subtitle2G,
        )
        Spacer(Modifier.height(8.dp))
        AikuText(
            text = title,
            style = AiKUTheme.typography.body2,
        )
        Box(
            modifier = Modifier
                .heightIn(min = 132.dp)
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            when (val loadState = lazyPagingSchedules.loadState.refresh) {
                is LoadState.Error -> {
                    AikuText("Error: ${loadState.error}")
                }

                is LoadState.Loading -> {
                    AikuLoadingWheel(
                        modifier = Modifier.size(80.dp),
                        contentDescription = "",
                    )
                }

                is LoadState.NotLoading -> {
                    if (lazyPagingSchedules.itemCount == 0) {
                        UpcomingSchedulePlaceholder()
                    } else {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(
                                count = lazyPagingSchedules.itemCount,
                                key = { lazyPagingSchedules[it]?.scheduleId ?: "schedule-$it" }
                            ) { index ->
                                lazyPagingSchedules[index]?.let { schedule ->
                                    UpcomingScheduleCard(
                                        groupName = schedule.groupName,
                                        location = schedule.location.locationName,
                                        isRunning = schedule.scheduleStatus == ScheduleStatus.RUNNING,
                                        time = schedule.scheduleTime,
                                        onClick = {
                                            onScheduleClick(
                                                schedule.groupId,
                                                schedule.scheduleId
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GroupOverviewsSection(
    title: String,
    groupSummaryPagingData: Flow<PagingData<GroupSummary>>,
    onGroupClick: (Long) -> Unit,
    onOpenCreateGroupDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyPagingGroupSummaries = groupSummaryPagingData.collectAsLazyPagingItems()

    Column(modifier = modifier) {
        AikuText(
            text = title,
            style = AiKUTheme.typography.subtitle4G,
        )
        Spacer(Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            when (val loadState = lazyPagingGroupSummaries.loadState.refresh) {
                is LoadState.Error -> {
                    AikuText("Error: ${loadState.error}")
                }

                is LoadState.Loading -> {
                    AikuLoadingWheel(
                        modifier = Modifier.size(80.dp),
                        contentDescription = "",
                    )
                }

                is LoadState.NotLoading -> {
                    if (lazyPagingGroupSummaries.itemCount == 0) {
                        EmptyPlaceholder(
                            title = stringResource(UiRes.string.group_empty_message),
                            buttonText = stringResource(UiRes.string.group_empty_button),
                            onClickButton = onOpenCreateGroupDialog,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(
                                    count = lazyPagingGroupSummaries.itemCount,
                                    key = { lazyPagingGroupSummaries[it]?.groupId ?: "group-$it" }
                                ) { index ->
                                    lazyPagingGroupSummaries[index]?.let { group ->
                                        GroupOverviewCard(
                                            groupName = group.groupName,
                                            time = group.lastScheduleTime,
                                            onClick = { onGroupClick(group.groupId) },
                                            memberSize = group.memberSize
                                        )
                                    }
                                }
                            }
                            AikuButton(
                                onClick = onOpenCreateGroupDialog,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(bottom = 12.dp),
                                shape = CircleShape,
                                shadowElevation = 8.dp,
                                colors = AikuButtonDefaults.buttonColors(
                                    containerColor = AiKUTheme.colors.cobaltBlue
                                ),
                                contentPadding = PaddingValues(12.dp),
                            ) {
                                AikuIcon(
                                    imageVector = AikuIcons.Add,
                                    contentDescription = stringResource(R.string.feature_home_fab_add_group),
                                    modifier = Modifier
                                        .size(36.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenEmptyPreview() {
    AikuPreviewTheme {
        HomeScreen(
            userNickname = "닉네임",
            schedulePagingData = flowOf(PagingData.from(emptyList())),
            groupSummaryPagingData = flowOf(PagingData.from(emptyList())),
            onOpenCreateGroupDialog = {},
            onDismissCreateGroupDialog = {},
            onCreateGroup = {},
            isCreateGroupDialogVisible = false,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    AikuPreviewTheme {
        HomeScreen(
            userNickname = "닉네임",
            schedulePagingData = flowOf(PagingData.from(mockSchedules)),
            groupSummaryPagingData = flowOf(PagingData.from(mockGroups)),
            onOpenCreateGroupDialog = {},
            onDismissCreateGroupDialog = {},
            onCreateGroup = {},
            isCreateGroupDialogVisible = false,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreviewWithDialog() {
    AikuPreviewTheme {
        HomeScreen(
            userNickname = "닉네임",
            schedulePagingData = flowOf(PagingData.from(mockSchedules)),
            groupSummaryPagingData = flowOf(PagingData.from(mockGroups)),
            onOpenCreateGroupDialog = {},
            onDismissCreateGroupDialog = {},
            onCreateGroup = {},
            isCreateGroupDialogVisible = true,
        )
    }
}

private val previewLocalDateTime = LocalDateTime.parse("2025-06-30T12:12:12")
private val mockSchedules = listOf(
    Schedule(
        groupId = 1L,
        groupName = "건국대학교",
        scheduleId = 6L,
        scheduleName = "산협프 회의",
        location = Location(
            latitude = 37.5407,
            longitude = 127.0795,
            locationName = "공학관"
        ),
        scheduleTime = previewLocalDateTime,
        memberSize = 5,
        scheduleStatus = ScheduleStatus.RUNNING
    ),
    Schedule(
        groupId = 1L,
        groupName = "건국대학교",
        scheduleId = 8L,
        scheduleName = "팀 정기모임",
        location = Location(
            latitude = 37.5407,
            longitude = 127.0795,
            locationName = "공학관"
        ),
        scheduleTime = previewLocalDateTime,
        memberSize = 5,
        scheduleStatus = ScheduleStatus.TERMINATED
    ),
    Schedule(
        groupId = 1L,
        groupName = "건국대학교",
        scheduleId = 9L,
        scheduleName = "스터디 회의",
        location = Location(
            latitude = 37.5407,
            longitude = 127.0795,
            locationName = "공학관"
        ),
        scheduleTime = previewLocalDateTime,
        memberSize = 5,
        scheduleStatus = ScheduleStatus.TERMINATED
    )
)
private val mockGroups = listOf(
    GroupSummary(
        groupId = 1,
        groupName = "그룹 1",
        memberSize = 138,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupSummary(
        groupId = 2,
        groupName = "그룹 2",
        memberSize = 6,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupSummary(
        groupId = 3,
        groupName = "그룹 3",
        memberSize = 48,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupSummary(
        groupId = 4,
        groupName = "그룹 4",
        memberSize = 8,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupSummary(
        groupId = 5,
        groupName = "그룹 5",
        memberSize = 33,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupSummary(
        groupId = 6,
        groupName = "그룹 6",
        memberSize = 120,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupSummary(
        groupId = 7,
        groupName = "그룹 7",
        memberSize = 20,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupSummary(
        groupId = 8,
        groupName = "그룹 8",
        memberSize = 37,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupSummary(
        groupId = 9,
        groupName = "그룹 9",
        memberSize = 41,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupSummary(
        groupId = 10,
        groupName = "그룹 10",
        memberSize = 44,
        lastScheduleTime = previewLocalDateTime
    )
)