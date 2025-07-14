package com.hyunjung.aiku.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuButtonDefaults
import com.hyunjung.aiku.core.designsystem.component.AikuLoadingWheel
import com.hyunjung.aiku.core.designsystem.theme.AikuColors
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography
import com.hyunjung.aiku.core.model.GroupOverview
import com.hyunjung.aiku.core.model.Location
import com.hyunjung.aiku.core.model.Schedule
import com.hyunjung.aiku.core.model.ScheduleStatus
import com.hyunjung.aiku.core.navigation.AikuScreen
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.hyunjung.aiku.core.ui.R as UiRes

private const val Threshold = 5

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val groups by viewModel.groups.collectAsStateWithLifecycle()
    val todaySchedules by viewModel.todaySchedules.collectAsStateWithLifecycle()
    val scheduleUiState by viewModel.scheduleUiState.collectAsStateWithLifecycle()
    val groupUiState by viewModel.groupUiState.collectAsStateWithLifecycle()
    val userNickname by viewModel.userNickName.collectAsStateWithLifecycle()
    val isCreateGroupDialogVisible by viewModel.isCreateGroupDialogVisible.collectAsStateWithLifecycle()

    HomeContent(
        scheduleUiState = scheduleUiState,
        groupUiState = groupUiState,
        userNickname = userNickname,
        groups = groups,
        todaySchedules = todaySchedules,
        loadNextSchedulePage = viewModel::loadNextSchedulePage,
        loadNextGroupPage = viewModel::loadNextGroupPage,
        isCreateGroupDialogVisible = isCreateGroupDialogVisible,
        onOpenCreateGroupDialog = viewModel::openCreateGroupDialog,
        onDismissCreateGroupDialog = viewModel::dismissCreateGroupDialog,
        onCreateGroup = viewModel::createGroup,
        modifier = modifier,
    )
}

@Composable
fun HomeContent(
    scheduleUiState: HomeScheduleUiState,
    groupUiState: HomeGroupUiState,
    userNickname: String,
    groups: List<GroupOverview>,
    todaySchedules: List<Schedule>,
    loadNextSchedulePage: () -> Unit,
    loadNextGroupPage: () -> Unit,
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
            .background(color = AikuColors.Gray01),
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
                todaySchedules = todaySchedules,
                homeScheduleUiState = scheduleUiState,
                onScheduleClick = { groupId, scheduleId ->
                    composeNavigator.navigate(AikuScreen.ScheduleDetail(groupId, scheduleId))
                },
                loadNextSchedulePage = loadNextSchedulePage,
            )
            Spacer(Modifier.height(24.dp))
            GroupOverviewsSection(
                title = stringResource(R.string.feature_home_group_title, userNickname),
                groups = groups,
                groupUiState = groupUiState,
                onGroupClick = {
                    composeNavigator.navigate(AikuScreen.GroupDetail(it))
                },
                onOpenCreateGroupDialog = onOpenCreateGroupDialog,
                loadNextGroupPage = loadNextGroupPage,
            )
        }
        AikuNavigationBar(
            currentScreen = AikuScreen.Home,
            modifier = Modifier.align(Alignment.BottomStart),
        )
        if (groupUiState == HomeGroupUiState.Loading ||
            scheduleUiState == HomeScheduleUiState.Loading
        ) {
            AikuLoadingWheel(
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(80.dp)
            )
        }
        if (groups.isEmpty())
            AikuButton(
                onClick = onOpenCreateGroupDialog,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 88.dp, end = 24.dp),
                shape = CircleShape,
                shadowElevation = 8.dp,
                colors = AikuButtonDefaults.buttonColors(
                    containerColor = AikuColors.CobaltBlue
                ),
                contentPadding = PaddingValues(12.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(R.string.feature_home_fab_add_group),
                    modifier = Modifier
                        .size(36.dp)
                )
            }
    }
}

@Composable
private fun TodaySchedulesSection(
    todaySchedules: List<Schedule>,
    homeScheduleUiState: HomeScheduleUiState,
    onScheduleClick: (groupId: Long, scheduleId: Long) -> Unit,
    loadNextSchedulePage: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
) {
    val formatter = remember { DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.getDefault()) }
    val today = remember { LocalDate.now().format(formatter) }

    val listState = rememberLazyListState()
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .map { layoutInfo ->
                val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                val totalCount = layoutInfo.totalItemsCount
                lastVisibleIndex >= totalCount - Threshold
            }
            .distinctUntilChanged()
            .collect { shouldLoad ->
                if (shouldLoad && homeScheduleUiState == HomeScheduleUiState.Idle) {
                    loadNextSchedulePage()
                }
            }
    }

    Column(modifier = modifier) {
        Text(
            text = today,
            style = AikuTypography.Subtitle2_G,
            color = AikuColors.Typo
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = title,
            style = AikuTypography.Body2,
            color = AikuColors.Typo
        )
        Spacer(Modifier.height(12.dp))
        if (todaySchedules.isEmpty()) {
            UpcomingSchedulePlaceholder()
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    items = todaySchedules,
                    key = { it.scheduleId }
                ) { schedule ->
                    UpcomingScheduleCard(
                        groupName = schedule.groupName,
                        location = schedule.location.locationName,
                        isRunning = schedule.scheduleStatus == ScheduleStatus.RUNNING,
                        time = schedule.scheduleTime,
                        onClick = { onScheduleClick(schedule.groupId, schedule.scheduleId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun GroupOverviewsSection(
    title: String,
    groups: List<GroupOverview>,
    groupUiState: HomeGroupUiState,
    onGroupClick: (Long) -> Unit,
    onOpenCreateGroupDialog: () -> Unit,
    loadNextGroupPage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .map { layoutInfo ->
                val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                val totalCount = layoutInfo.totalItemsCount
                lastVisibleIndex >= totalCount - Threshold
            }
            .distinctUntilChanged()
            .collect { shouldLoad ->
                if (shouldLoad && groupUiState == HomeGroupUiState.Idle) {
                    loadNextGroupPage()
                }
            }
    }

    Column(modifier = modifier) {
        Text(
            text = title,
            style = AikuTypography.Subtitle4_G,
            color = AikuColors.Typo
        )
        Spacer(Modifier.height(12.dp))
        if (groups.isEmpty()) {
            EmptyPlaceholder(
                title = stringResource(UiRes.string.group_empty_message),
                buttonText = stringResource(UiRes.string.group_empty_button),
                onClickButton = onOpenCreateGroupDialog,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = groups,
                    key = { it.groupId }
                ) { group ->
                    GroupOverviewCard(
                        groupName = group.groupName,
                        time = group.lastScheduleTime,
                        onClick = { onGroupClick(group.groupId) },
                        memberSize = group.memberSize
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenEmptyPreview() {
    AikuPreviewTheme {
        HomeContent(
            userNickname = "닉네임",
            groups = emptyList(),
            todaySchedules = emptyList(),
            scheduleUiState = HomeScheduleUiState.Idle,
            groupUiState = HomeGroupUiState.Idle,
            loadNextGroupPage = {},
            loadNextSchedulePage = {},
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
        HomeContent(
            userNickname = "닉네임",
            groups = mockGroups,
            todaySchedules = mockSchedules,
            scheduleUiState = HomeScheduleUiState.Idle,
            groupUiState = HomeGroupUiState.Idle,
            loadNextGroupPage = {},
            loadNextSchedulePage = {},
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
        HomeContent(
            userNickname = "닉네임",
            groups = mockGroups,
            todaySchedules = mockSchedules,
            scheduleUiState = HomeScheduleUiState.Idle,
            groupUiState = HomeGroupUiState.Idle,
            loadNextGroupPage = {},
            loadNextSchedulePage = {},
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
    GroupOverview(
        groupId = 1,
        groupName = "그룹 1",
        memberSize = 138,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupOverview(
        groupId = 2,
        groupName = "그룹 2",
        memberSize = 6,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupOverview(
        groupId = 3,
        groupName = "그룹 3",
        memberSize = 48,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupOverview(
        groupId = 4,
        groupName = "그룹 4",
        memberSize = 8,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupOverview(
        groupId = 5,
        groupName = "그룹 5",
        memberSize = 33,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupOverview(
        groupId = 6,
        groupName = "그룹 6",
        memberSize = 120,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupOverview(
        groupId = 7,
        groupName = "그룹 7",
        memberSize = 20,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupOverview(
        groupId = 8,
        groupName = "그룹 8",
        memberSize = 37,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupOverview(
        groupId = 9,
        groupName = "그룹 9",
        memberSize = 41,
        lastScheduleTime = previewLocalDateTime
    ),
    GroupOverview(
        groupId = 10,
        groupName = "그룹 10",
        memberSize = 44,
        lastScheduleTime = previewLocalDateTime
    )
)
