package com.hyunjung.aiku.feature.groups

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hyunjung.aiku.core.designsystem.component.AikuLoadingWheel
import com.hyunjung.aiku.core.designsystem.component.AikuSurface
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.group.GroupDetail
import com.hyunjung.aiku.core.model.group.GroupMember
import com.hyunjung.aiku.core.model.group.GroupSchedule
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.MemberProfileImage
import com.hyunjung.aiku.core.model.profile.ProfileBackgroundColor
import com.hyunjung.aiku.core.model.schedule.Location
import com.hyunjung.aiku.core.model.schedule.ScheduleStatus
import com.hyunjung.aiku.core.ui.component.common.AikuTabs
import com.hyunjung.aiku.core.ui.component.common.EmptyPlaceholder
import com.hyunjung.aiku.core.ui.component.schedule.ScheduleCard
import com.hyunjung.aiku.core.ui.extension.backgroundColor
import com.hyunjung.aiku.core.ui.extension.painter
import com.hyunjung.aiku.core.ui.paging.LazyPagingRow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime

@Composable
fun GroupDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: GroupDetailViewModel = hiltViewModel()
) {
    val groupDetailUiState by viewModel.groupDetailUiState.collectAsStateWithLifecycle()
    val lazyPagingGroupSchedules = viewModel.groupSchedulePagingData.collectAsLazyPagingItems()

    var selectedTab by remember { mutableStateOf(GroupDetailTab.MEMBER) }

    GroupDetailScreen(
        selectedTab = selectedTab,
        onTabSelected = { selectedTab = GroupDetailTab.entries[it] },
        groupDetailUiState = groupDetailUiState,
        lazyPagingGroupSchedules = lazyPagingGroupSchedules,
        modifier = modifier
    )
}

@Composable
private fun GroupDetailScreen(
    selectedTab: GroupDetailTab,
    onTabSelected: (Int) -> Unit,
    groupDetailUiState: GroupDetailUiState,
    lazyPagingGroupSchedules: LazyPagingItems<GroupSchedule>,
    modifier: Modifier = Modifier,
) {
    val tabs = GroupDetailTab.entries

    when (groupDetailUiState) {
        is GroupDetailUiState.Error -> {
            // todo : error ui
        }

        is GroupDetailUiState.Loading -> {
            // todo: loading ui
        }

        is GroupDetailUiState.Success -> {
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                AdContainer()
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    AikuTabs(
                        tabs = tabs.map { it.label },
                        selectedIndex = tabs.indexOf(selectedTab),
                        onTabSelected = onTabSelected
                    )
                    when (selectedTab) {
                        GroupDetailTab.MEMBER -> {
                            MemberTabContent(
                                groupMembers = groupDetailUiState.groupDetail.members,
                                onMemberClick = {},
                                onInviteClick = {},
                            )
                        }

                        GroupDetailTab.SCHEDULE -> {
                            ScheduleTabContent(
                                lazyPagingGroupSchedules = lazyPagingGroupSchedules,
                                onScheduleClick = {},
                                onCreateSchedule = {},
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MemberTabContent(
    groupMembers: List<GroupMember>,
    onMemberClick: (Long) -> Unit,
    onInviteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        if (groupMembers.isEmpty()) {
            EmptyPlaceholder(
                title = stringResource(R.string.group_detail_member_section_empty_title),
                buttonText = stringResource(R.string.group_detail_member_section_empty_button),
                onClickButton = onInviteClick,
            )
        } else {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(vertical = 20.dp)
            ) {
                // todo : 초대하기 UI
                items(items = groupMembers, key = { it.memberId }) { member ->
                    MemberAvatarCard(
                        member = member,
                        contentDescription = null,
                        onClick = {
                            onMemberClick(member.memberId)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ScheduleTabContent(
    lazyPagingGroupSchedules: LazyPagingItems<GroupSchedule>,
    onScheduleClick: (Long) -> Unit,
    onCreateSchedule: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        LazyPagingRow(
            refreshLoadState = lazyPagingGroupSchedules.loadState.refresh,
            isEmpty = lazyPagingGroupSchedules.itemCount == 0,
            loading = { AikuLoadingWheel() },
            empty = {
                EmptyPlaceholder(
                    title = stringResource(R.string.group_detail_schedule_section_empty_title),
                    buttonText = stringResource(R.string.group_detail_schedule_section_empty_button),
                    onClickButton = onCreateSchedule,
                )
            },
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
                .heightIn(min = 132.dp)
                .padding(vertical = 12.dp),
        ) {
            // todo : stick header 수정
//            item {
//                AikuText(
//                    text = stringResource(
//                        R.string.group_detail_schedule_section_schedule_status_summary,
//                        groupScheduleUiState.schedules.filter { it.scheduleStatus == ScheduleStatus.RUNNING }.size,
//                        groupScheduleUiState.schedules.filter { it.scheduleStatus == ScheduleStatus.WAITING }.size
//                    ),
//                    style = AiKUTheme.typography.caption1SemiBold,
//                )
//            }

            items(
                count = lazyPagingGroupSchedules.itemCount,
                key = { lazyPagingGroupSchedules[it]?.scheduleId ?: "group-schedule-$it" }
            ) { index ->
                lazyPagingGroupSchedules[index]?.let { groupSchedule ->
                    ScheduleCard(
                        onClick = { onScheduleClick(groupSchedule.scheduleId) },
                        scheduleName = groupSchedule.scheduleName,
                        location = groupSchedule.location,
                        time = groupSchedule.scheduleTime,
                        scheduleStatus = groupSchedule.scheduleStatus
                    )
                }
            }
        }
    }
}

@Composable
private fun MemberAvatarCard(
    member: GroupMember,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable(onClick = onClick)
        ) {
            Image(
                painter = member.memberProfileImage.painter(),
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = member.memberProfileImage.backgroundColor(), shape = CircleShape
                    )
                    .padding(8.dp)
            )
            AikuText(
                text = member.nickname,
                style = AiKUTheme.typography.body2SemiBold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun AdContainer(
    modifier: Modifier = Modifier,
) {
    AikuSurface(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        color = Color(0xFFECECEC),
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            AikuText(
                text = "광고", style = AiKUTheme.typography.subtitle3G
            )
        }
    }
}

@Preview(showBackground = true, name = "Member Tab - Empty")
@Composable
private fun GroupDetailScreenMemberTabEmptyPreview() {
    AiKUTheme {
        GroupDetailScreen(
            selectedTab = GroupDetailTab.MEMBER,
            onTabSelected = {},
            groupDetailUiState = GroupDetailUiState.Success(
                GroupDetail(
                    groupId = 0,
                    groupName = "그룹 이름",
                    members = emptyList()
                )
            ),
            lazyPagingGroupSchedules = flowOf(PagingData.empty<GroupSchedule>())
                .collectAsLazyPagingItems(),
        )
    }
}

@Preview(showBackground = true, name = "Member Tab")
@Composable
private fun GroupDetailScreenMemberTabPreview() {
    AiKUTheme {
        val groupMembers = listOf(
            GroupMember(
                memberId = 1,
                nickname = "사용자1",
                memberProfileImage = MemberProfileImage.Avatar(
                    type = AvatarType.BOY,
                    backgroundColor = ProfileBackgroundColor.GREEN
                )
            ),
            GroupMember(
                memberId = 2,
                nickname = "사용자2",
                memberProfileImage = MemberProfileImage.Avatar(
                    type = AvatarType.BABY,
                    backgroundColor = ProfileBackgroundColor.YELLOW
                )
            ),
            GroupMember(
                memberId = 3,
                nickname = "abcdef",
                memberProfileImage = MemberProfileImage.Avatar(
                    type = AvatarType.SCRATCH,
                    backgroundColor = ProfileBackgroundColor.PURPLE
                )
            ),
            GroupMember(
                memberId = 4,
                nickname = "ABCDEF",
                memberProfileImage = MemberProfileImage.Avatar(
                    type = AvatarType.GIRL,
                    backgroundColor = ProfileBackgroundColor.YELLOW
                )
            ),
            GroupMember(
                memberId = 5,
                nickname = "사용자5",
                memberProfileImage = MemberProfileImage.Avatar(
                    type = AvatarType.BABY,
                    backgroundColor = ProfileBackgroundColor.GREEN
                )
            )
        )

        GroupDetailScreen(
            selectedTab = GroupDetailTab.MEMBER,
            onTabSelected = {},
            groupDetailUiState = GroupDetailUiState.Success(
                GroupDetail(
                    groupId = 0,
                    groupName = "그룹 이름",
                    members = groupMembers
                )
            ),
            lazyPagingGroupSchedules = flowOf(PagingData.empty<GroupSchedule>())
                .collectAsLazyPagingItems(),
        )
    }
}

@Preview(showBackground = true, name = "Schedule Tab - Empty")
@Composable
private fun GroupDetailScreenScheduleTabEmptyPreview() {
    AiKUTheme {
        GroupDetailScreen(
            selectedTab = GroupDetailTab.SCHEDULE,
            onTabSelected = {},
            groupDetailUiState = GroupDetailUiState.Success(
                GroupDetail(
                    groupId = 0,
                    groupName = "그룹 이름",
                    members = emptyList()
                )
            ),
            lazyPagingGroupSchedules = flowOf(PagingData.empty<GroupSchedule>())
                .collectAsLazyPagingItems(),
        )
    }
}

@Preview(showBackground = true, name = "Schedule Tab")
@Composable
private fun GroupDetailScreenScheduleTabPreview() {
    AiKUTheme {
        val groupSchedules = listOf(
            GroupSchedule(
                scheduleId = 0,
                scheduleName = "약속 이름",
                memberSize = 4,
                location = Location(
                    locationName = "홍대 입구역 1번 출구",
                    latitude = 37.566535,
                    longitude = 126.977969
                ),
                scheduleStatus = ScheduleStatus.WAITING,
                scheduleTime = LocalDateTime.of(2024, 12, 13, 18, 0),
                accept = true
            ),
            GroupSchedule(
                scheduleId = 1,
                scheduleName = "약속 이름",
                memberSize = 4,
                location = Location(
                    locationName = "홍대 입구역 1번 출구",
                    latitude = 37.566535,
                    longitude = 126.977969
                ),
                scheduleStatus = ScheduleStatus.RUNNING,
                scheduleTime = LocalDateTime.of(2024, 12, 13, 18, 0),
                accept = true
            ),
            GroupSchedule(
                scheduleId = 2,
                scheduleName = "약속 이름",
                memberSize = 4,
                location = Location(
                    locationName = "홍대 입구역 1번 출구",
                    latitude = 37.566535,
                    longitude = 126.977969
                ),
                scheduleStatus = ScheduleStatus.BEFORE_JOIN,
                scheduleTime = LocalDateTime.of(2024, 12, 13, 18, 0),
                accept = false
            ),
            GroupSchedule(
                scheduleId = 3,
                scheduleName = "약속 이름",
                memberSize = 4,
                location = Location(
                    locationName = "홍대 입구역 1번 출구",
                    latitude = 37.566535,
                    longitude = 126.977969
                ),
                scheduleStatus = ScheduleStatus.TERMINATED,
                scheduleTime = LocalDateTime.of(2024, 12, 13, 18, 0),
                accept = false
            )
        )

        val lazyPagingGroupSchedules = flowOf(
            PagingData.from(
                data = groupSchedules,
                sourceLoadStates =
                    LoadStates(
                        refresh = LoadState.NotLoading(false),
                        append = LoadState.NotLoading(false),
                        prepend = LoadState.NotLoading(false),
                    ),
            ),
        ).collectAsLazyPagingItems()


        GroupDetailScreen(
            selectedTab = GroupDetailTab.SCHEDULE,
            onTabSelected = {},
            groupDetailUiState = GroupDetailUiState.Success(
                GroupDetail(
                    groupId = 0,
                    groupName = "그룹 이름",
                    members = emptyList()
                )
            ),
            lazyPagingGroupSchedules = lazyPagingGroupSchedules,
        )
    }
}

private enum class GroupDetailTab(val label: String) {
    MEMBER("멤버"),
    SCHEDULE("약속"),
}