package com.hyunjung.aiku.presentation.home.screen

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuSurface
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.designsystem.theme.AikuColors
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography
import com.hyunjung.aiku.presentation.R
import com.hyunjung.aiku.presentation.home.component.AikuTabs
import com.hyunjung.aiku.presentation.home.component.EmptyStateCard
import com.hyunjung.aiku.presentation.home.component.GroupScheduleCard
import com.hyunjung.aiku.presentation.home.component.ScheduleStatus

enum class GroupDetailTab(val label: String) {
    MEMBER("멤버"),
    SCHEDULE("약속"),
}

data class Member(
    val id: Long,
    val avatar: Painter,
    val backgroundColor: Color,
    val name: String,
)

data class GroupSchedule(
    val id: Long,
    val scheduleName: String,
    val location: String,
    val scheduleStatus: ScheduleStatus,
    val time: Long,
)

sealed interface MemberUiState {
    object Loading : MemberUiState
    data class Success(val members: List<Member>) : MemberUiState {
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
    memberUiState: MemberUiState,
    groupScheduleUiState: GroupScheduleUiState,
    modifier: Modifier = Modifier,
) {
    var selectedTab by remember { mutableStateOf(GroupDetailTab.MEMBER) }
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
                onTabSelected = { newIndex ->
                    selectedTab = tabs[newIndex]
                }
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
                    EmptyStateCard(
                        title = stringResource(R.string.presentation_member_detail_member_section_empty_title),
                        buttonText = stringResource(R.string.presentation_member_detail_member_section_empty_button),
                        onClickButton = onInviteClick,
                    )
                } else {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        contentPadding = PaddingValues(vertical = 20.dp)
                    ) {
                        item {
                            MemberMiniProfile(
                                member = Member(
                                    id = 0,
                                    avatar = painterResource(R.drawable.presentation_char_head_unknown),
                                    name = stringResource(R.string.presentation_member_detail_member_section_invite),
                                    backgroundColor = AikuColors.Gray02,
                                ),
                                contentDescription = stringResource(R.string.presentation_member_detail_member_section_invite),
                                onClick = onInviteClick,
                            )
                        }
                        items(items = memberUiState.members, key = { it.id }) { member ->
                            MemberMiniProfile(
                                member = member,
                                contentDescription = stringResource(
                                    R.string.presentation_member_detail_member_section_avatar_description,
                                    member.avatar
                                ),
                                onClick = {
                                    onMemberClick(member.id)
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
    onCreateSchedule:() -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        when (groupScheduleUiState) {
            is GroupScheduleUiState.Loading -> {
                // todo : Loading State
            }
            is GroupScheduleUiState.Success -> {
                if (groupScheduleUiState.isEmpty()) {
                    EmptyStateCard(
                        title = stringResource(R.string.presentation_member_detail_schedule_section_empty_title),
                        buttonText = stringResource(R.string.presentation_member_detail_schedule_section_empty_button),
                        onClickButton = onCreateSchedule,
                    )
                } else {
                    Text(
                        text = stringResource(
                            R.string.presentation_member_detail_schedule_section_schedule_status_summary,
                            groupScheduleUiState.schedules.filter { it.scheduleStatus == ScheduleStatus.RUNNING }.size,
                            groupScheduleUiState.schedules.filter { it.scheduleStatus == ScheduleStatus.WAITING }.size
                        ),
                        style = AikuTypography.Caption1_SemiBold,
                        color = AikuColors.Typo,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(
                            items = groupScheduleUiState.schedules, key = { it.id }) {
                            GroupScheduleCard(
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
private fun MemberMiniProfile(
    member: Member,
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
                painter = member.avatar,
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = member.backgroundColor, shape = CircleShape
                    )
                    .padding(8.dp)
            )
            Text(
                text = member.name,
                style = AikuTypography.Body2_SemiBold,
                color = AikuColors.Typo,
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
            Text(
                text = "광고", style = AikuTypography.Subtitle3_G
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GroupDetailScreenPreview() {
    AiKUTheme {
        val members = listOf(
            Member(
                id = 1,
                avatar = painterResource(R.drawable.presentation_char_head_boy),
                name = "사용자1",
                backgroundColor = AikuColors.Green05,
            ),
            Member(
                id = 2,
                avatar = painterResource(R.drawable.presentation_char_head_baby),
                name = "사용자2",
                backgroundColor = AikuColors.Yellow05,
            ),
            Member(
                id = 3,
                avatar = painterResource(R.drawable.presentation_char_head_scratch),
                name = "abcdef",
                backgroundColor = AikuColors.Purple05,
            ),
            Member(
                id = 4,
                avatar = painterResource(R.drawable.presentation_char_head_girl),
                name = "ABCDEF",
                backgroundColor = AikuColors.Yellow05,
            ),
            Member(
                id = 5,
                avatar = painterResource(R.drawable.presentation_char_head_baby),
                name = "사용자5",
                backgroundColor = AikuColors.Green05,
            ),

            )
        val groupSchedules = listOf(
            GroupSchedule(
                id = 0,
                scheduleName = "약속 이름",
                location = "홍대 입구역 1번 출구",
                scheduleStatus = ScheduleStatus.WAITING,
                time = 1734048000000L,
            ),
            GroupSchedule(
                id = 1,
                scheduleName = "약속 이름",
                location = "홍대 입구역 1번 출구",
                scheduleStatus = ScheduleStatus.RUNNING,
                time = 1734048000000L,
            ),
            GroupSchedule(
                id = 2,
                scheduleName = "약속 이름",
                location = "홍대 입구역 1번 출구",
                scheduleStatus = ScheduleStatus.BEFORE_JOIN,
                time = 1734048000000L,
            ),
            GroupSchedule(
                id = 3,
                scheduleName = "약속 이름",
                location = "홍대 입구역 1번 출구",
                scheduleStatus = ScheduleStatus.ENDED,
                time = 1734048000000L,
            ),
        )
        GroupDetailScreen(
            memberUiState = MemberUiState.Success(emptyList()),
            groupScheduleUiState = GroupScheduleUiState.Success(groupSchedules)
        )
    }
}