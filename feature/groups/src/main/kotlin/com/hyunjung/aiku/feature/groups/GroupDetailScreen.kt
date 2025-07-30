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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuSurface
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.group.GroupMember
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.MemberProfileImage
import com.hyunjung.aiku.core.model.profile.ProfileBackgroundColor
import com.hyunjung.aiku.core.model.schedule.ScheduleStatus
import com.hyunjung.aiku.core.ui.component.common.AikuTabs
import com.hyunjung.aiku.core.ui.component.common.EmptyPlaceholder
import com.hyunjung.aiku.core.ui.component.schedule.ScheduleCard
import com.hyunjung.aiku.core.ui.extension.backgroundColor
import com.hyunjung.aiku.core.ui.extension.painter

enum class GroupDetailTab(val label: String) {
    MEMBER("멤버"),
    SCHEDULE("약속"),
}

data class GroupSchedule(
    val id: Long,
    val hostName: String,
    val scheduleName: String,
    val location: String,
    val participantCount: Int,
    val scheduleStatus: ScheduleStatus,
    val time: Long,
)

sealed interface MemberUiState {
    object Loading : MemberUiState
    data class Success(val members: List<GroupMember>) : MemberUiState {
        fun isEmpty(): Boolean = members.isEmpty()
    }
}

sealed interface GroupScheduleUiState {
    object Loading : GroupScheduleUiState
    data class Success(
        val schedules: List<GroupSchedule>
    ) : GroupScheduleUiState {
        fun isEmpty(): Boolean = schedules.isEmpty()
    }
}

@Composable
fun GroupDetailScreen(
    selectedTab: GroupDetailTab,
    onTabSelected: (Int) -> Unit,
    memberUiState: MemberUiState,
    groupScheduleUiState: GroupScheduleUiState,
    modifier: Modifier = Modifier,
) {
    val tabs = GroupDetailTab.entries
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
                        memberUiState = memberUiState,
                        onMemberClick = {},
                        onInviteClick = {},
                    )
                }

                GroupDetailTab.SCHEDULE -> {
                    ScheduleTabContent(
                        groupScheduleUiState = groupScheduleUiState,
                        onScheduleClick = {},
                        onCreateSchedule = {},
                    )
                }
            }
        }
    }
}

@Composable
private fun MemberTabContent(
    memberUiState: MemberUiState,
    onMemberClick: (Long) -> Unit,
    onInviteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        when (memberUiState) {
            is MemberUiState.Loading -> {
                // todo: loading state
            }

            is MemberUiState.Success -> {
                if (memberUiState.isEmpty()) {
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
                        items(items = memberUiState.members, key = { it.memberId }) { member ->
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
    }
}

@Composable
private fun ScheduleTabContent(
    groupScheduleUiState: GroupScheduleUiState,
    onScheduleClick: (Long) -> Unit,
    onCreateSchedule: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        when (groupScheduleUiState) {
            is GroupScheduleUiState.Loading -> {
                // todo : Loading State
            }

            is GroupScheduleUiState.Success -> {
                if (groupScheduleUiState.isEmpty()) {
                    EmptyPlaceholder(
                        title = stringResource(R.string.group_detail_schedule_section_empty_title),
                        buttonText = stringResource(R.string.group_detail_schedule_section_empty_button),
                        onClickButton = onCreateSchedule,
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(vertical = 20.dp)
                    ) {
                        item {
                            AikuText(
                                text = stringResource(
                                    R.string.group_detail_schedule_section_schedule_status_summary,
                                    groupScheduleUiState.schedules.filter { it.scheduleStatus == ScheduleStatus.RUNNING }.size,
                                    groupScheduleUiState.schedules.filter { it.scheduleStatus == ScheduleStatus.WAITING }.size
                                ),
                                style = AiKUTheme.typography.caption1SemiBold,
                            )
                        }
                        items(
                            items = groupScheduleUiState.schedules, key = { it.id }) {
                            ScheduleCard(
                                onClick = { onScheduleClick(it.id) },
                                scheduleName = it.scheduleName,
                                location = it.location,
                                time = it.time,
                                scheduleStatus = it.scheduleStatus
                            )
                        }
                    }
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
            memberUiState = MemberUiState.Success(emptyList()),
            groupScheduleUiState = GroupScheduleUiState.Success(emptyList())
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
            memberUiState = MemberUiState.Success(groupMembers),
            groupScheduleUiState = GroupScheduleUiState.Success(emptyList())
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
            memberUiState = MemberUiState.Success(emptyList()),
            groupScheduleUiState = GroupScheduleUiState.Success(emptyList())
        )
    }
}

@Preview(showBackground = true, name = "Schedule Tab")
@Composable
private fun GroupDetailScreenScheduleTabPreview() {
    AiKUTheme {
        val groupSchedules = listOf(
            GroupSchedule(
                id = 0,
                hostName = "홍길동",
                participantCount = 4,
                scheduleName = "약속 이름",
                location = "홍대 입구역 1번 출구",
                scheduleStatus = ScheduleStatus.WAITING,
                time = 1734048000000L,
            ),
            GroupSchedule(
                id = 1,
                hostName = "홍길동",
                participantCount = 4,
                scheduleName = "약속 이름",
                location = "홍대 입구역 1번 출구",
                scheduleStatus = ScheduleStatus.RUNNING,
                time = 1734048000000L,
            ),
            GroupSchedule(
                id = 2,
                hostName = "홍길동",
                participantCount = 4,
                scheduleName = "약속 이름",
                location = "홍대 입구역 1번 출구",
                scheduleStatus = ScheduleStatus.BEFORE_JOIN,
                time = 1734048000000L,
            ),
            GroupSchedule(
                id = 3,
                hostName = "홍길동",
                participantCount = 4,
                scheduleName = "약속 이름",
                location = "홍대 입구역 1번 출구",
                scheduleStatus = ScheduleStatus.TERMINATED,
                time = 1734048000000L,
            ),
        )
        GroupDetailScreen(
            selectedTab = GroupDetailTab.SCHEDULE,
            onTabSelected = {},
            memberUiState = MemberUiState.Success(emptyList()),
            groupScheduleUiState = GroupScheduleUiState.Success(groupSchedules)
        )
    }
}