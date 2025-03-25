package com.hyunjung.aiku.presentation.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuButtonDefaults
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.designsystem.theme.AikuColors
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography
import com.hyunjung.aiku.presentation.R
import com.hyunjung.aiku.presentation.home.component.CreateGroupDialog
import com.hyunjung.aiku.presentation.home.component.EmptyScheduleCard
import com.hyunjung.aiku.presentation.home.component.EmptyStateCard
import com.hyunjung.aiku.presentation.home.component.GroupCard
import com.hyunjung.aiku.presentation.home.component.ScheduleCard
import java.text.SimpleDateFormat
import java.util.Locale

sealed interface ScheduleUiState {
    object Loading : ScheduleUiState
    data class Success(
        val schedules: List<Schedule>
    ) : ScheduleUiState {
        fun isEmpty(): Boolean = schedules.isEmpty()
    }

    data class Error(val message: String) : ScheduleUiState
}

sealed interface GroupUiState {
    object Loading : GroupUiState
    data class Success(
        val groups: List<Group>
    ) : GroupUiState {
        fun isEmpty(): Boolean = groups.isEmpty()
    }

    data class Error(val message: String) : GroupUiState
}

data class Group(
    val id: Long,
    val name: String,
    val latestScheduleTime: Long,
    val memberSize: Int,
)

data class Schedule(
    val id: Long,
    val groupName: String,
    val location: String,
    val isRunning: Boolean,
    val time: Long,
)

@Composable
fun HomeScreen(
    userNickname: String,
    scheduleUiState: ScheduleUiState,
    groupUiState: GroupUiState,
    modifier: Modifier = Modifier,
    showCreateGroupDialog: Boolean = false,
    onCreateGroupDismissed: () -> Unit = {}
) {
    val dateFormatter = remember { SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()) }
    if (showCreateGroupDialog) {
        CreateGroupDialog(
            onDismiss = onCreateGroupDismissed,
            onCreateGroup = {}
        )
    }
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .padding(20.dp),
        ) {
            Text(
                text = dateFormatter.format(System.currentTimeMillis()),
                style = AikuTypography.Subtitle2_G,
                color = AikuColors.Typo
            )
            UpcomingSchedule(
                title = stringResource(R.string.presentation_home_schedule_title),
                scheduleUiState = scheduleUiState,
                onScheduleClick = {}
            )
            Spacer(Modifier.height(24.dp))
            GroupList(
                title = stringResource(R.string.presentation_home_group_title, userNickname),
                groupUiState = groupUiState,
                onGroupClick = {},
                onCreateGroup = {}
            )
        }
        if (groupUiState is GroupUiState.Success && !groupUiState.isEmpty())
            AikuButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 12.dp, end = 24.dp),
                shape = CircleShape,
                shadowElevation = 8.dp,
                colors = AikuButtonDefaults.buttonColors(
                    containerColor = AikuColors.CobaltBlue
                ),
                contentPadding = PaddingValues(12.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(R.string.presentation_home_fab_add_group),
                    modifier = Modifier
                        .size(36.dp)
                )
            }
    }
}

@Composable
private fun ColumnScope.UpcomingSchedule(
    scheduleUiState: ScheduleUiState,
    onScheduleClick: (Long) -> Unit,
    title: String,
) {
    Text(
        text = title,
        style = AikuTypography.Body2,
        color = AikuColors.Typo
    )
    Spacer(Modifier.height(12.dp))
    when (scheduleUiState) {
        is ScheduleUiState.Error -> TODO()
        ScheduleUiState.Loading -> TODO()
        is ScheduleUiState.Success -> {
            if (scheduleUiState.isEmpty()) {
                EmptyScheduleCard()
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = scheduleUiState.schedules,
                        key = { it.id }
                    ) {
                        ScheduleCard(
                            groupName = it.groupName,
                            location = it.location,
                            isRunning = it.isRunning,
                            time = it.time,
                            onClick = { onScheduleClick(it.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.GroupList(
    title: String,
    groupUiState: GroupUiState,
    onGroupClick: (Long) -> Unit,
    onCreateGroup: () -> Unit,
) {
    Text(
        text = title,
        style = AikuTypography.Subtitle4_G,
        color = AikuColors.Typo
    )
    Spacer(Modifier.height(12.dp))
    when (groupUiState) {
        is GroupUiState.Error -> TODO()
        GroupUiState.Loading -> TODO()
        is GroupUiState.Success -> {
            if (groupUiState.isEmpty()) {
                EmptyStateCard(
                    title = stringResource(R.string.presentation_group_empty_message),
                    buttonText = stringResource(R.string.presentation_home_no_group_button),
                    onClickButton = onCreateGroup,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = groupUiState.groups,
                        key = { it.id }
                    ) {
                        GroupCard(
                            groupName = it.name,
                            time = it.latestScheduleTime,
                            onClick = { onGroupClick(it.id) },
                            memberSize = it.memberSize
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenEmptyPreview() {
    AiKUTheme {
        HomeScreen(
            userNickname = "닉네임",
            scheduleUiState = ScheduleUiState.Success(emptyList()),
            groupUiState = GroupUiState.Success(emptyList()),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    val mockSchedules = listOf(
        Schedule(
            id = 6L,
            groupName = "건국대학교",
            location = "공학관",
            isRunning = true,
            time = 1724152332000L // "2024-08-20T12:12:12"
        ),
        Schedule(
            id = 8L,
            groupName = "건국대학교",
            location = "공학관",
            isRunning = false,
            time = 1724152332000L
        ),
        Schedule(
            id = 9L,
            groupName = "건국대학교",
            location = "공학관",
            isRunning = false,
            time = 1724152332000L
        )
    )
    val mockGroups = listOf(
        Group(id = 1, name = "그룹 1", latestScheduleTime = 1722859932000L, memberSize = 138),
        Group(id = 2, name = "그룹 2", latestScheduleTime = 1723032732000L, memberSize = 6),
        Group(id = 3, name = "그룹 3", latestScheduleTime = 1723723932000L, memberSize = 48),
        Group(id = 4, name = "그룹 4", latestScheduleTime = 1722687132000L, memberSize = 8),
        Group(id = 5, name = "그룹 5", latestScheduleTime = 1722946332000L, memberSize = 33),
        Group(id = 6, name = "그룹 6", latestScheduleTime = 1721823132000L, memberSize = 120),
        Group(id = 7, name = "그룹 7", latestScheduleTime = 1721909532000L, memberSize = 20),
        Group(id = 8, name = "그룹 8", latestScheduleTime = 1723205532000L, memberSize = 37),
        Group(id = 9, name = "그룹 9", latestScheduleTime = 1723378332000L, memberSize = 41),
        Group(id = 10, name = "그룹 10", latestScheduleTime = 1722600732000L, memberSize = 44)
    )

    AiKUTheme {
        HomeScreen(
            userNickname = "닉네임",
            scheduleUiState = ScheduleUiState.Success(mockSchedules),
            groupUiState = GroupUiState.Success(mockGroups),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreviewWithDialog() {
    AiKUTheme {
        HomeScreen(
            showCreateGroupDialog = true,
            userNickname = "닉네임",
            scheduleUiState = ScheduleUiState.Success(emptyList()),
            groupUiState = GroupUiState.Success(emptyList()),
        )
    }
}