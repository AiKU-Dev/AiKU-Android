package com.hyunjung.aiku.feature.group.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.hyunjung.aiku.core.ui.component.common.AikuTabs
import com.hyunjung.aiku.core.ui.preview.GroupMemberListPreviewParameterProvider
import com.hyunjung.aiku.feature.group.detail.component.GroupMemberTab
import com.hyunjung.aiku.feature.group.detail.component.GroupScheduleTab
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun GroupDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: GroupDetailViewModel = hiltViewModel()
) {
    val groupDetailUiState by viewModel.groupDetailUiState.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val lazyPagingGroupSchedules = viewModel.groupSchedulePagingData.collectAsLazyPagingItems()

    GroupDetailScreen(
        selectedTab = selectedTab,
        onTabSelected = viewModel::updateSelectedTab,
        groupDetailUiState = groupDetailUiState,
        lazyPagingGroupSchedules = lazyPagingGroupSchedules,
        modifier = modifier
    )
}

@Composable
private fun GroupDetailScreen(
    selectedTab: GroupDetailTab,
    onTabSelected: (GroupDetailTab) -> Unit,
    groupDetailUiState: GroupDetailUiState,
    lazyPagingGroupSchedules: LazyPagingItems<GroupSchedule>,
    modifier: Modifier = Modifier,
) {
    when (groupDetailUiState) {
        is GroupDetailUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AikuText(groupDetailUiState.message)
            }
        }

        is GroupDetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AikuLoadingWheel(Modifier.size(80.dp))
            }
        }

        is GroupDetailUiState.Success -> {
            val tabs = GroupDetailTab.entries
            Column(modifier = modifier.fillMaxSize()) {
                AdContainer()
                AikuTabs(
                    tabs = tabs.map { it.label },
                    selectedIndex = tabs.indexOf(selectedTab),
                    onTabSelected = { index -> onTabSelected(tabs[index]) },
                    modifier = Modifier.padding(16.dp)
                )
                when (selectedTab) {
                    GroupDetailTab.MEMBER -> {
                        GroupMemberTab(
                            groupMembers = groupDetailUiState.groupDetail.members,
                            onMemberClick = {},
                            onInviteClick = {},
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp)
                        )
                    }

                    GroupDetailTab.SCHEDULE -> {
                        GroupScheduleTab(
                            lazyPagingGroupSchedules = lazyPagingGroupSchedules,
                            onScheduleClick = {},
                            onCreateSchedule = {},
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

// todo : 임시 광고 Area
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

@Preview
@Composable
private fun GroupDetailScreenMemberTabPreview(
    @PreviewParameter(GroupMemberListPreviewParameterProvider::class)
    groupMembers: List<GroupMember>
) {
    AiKUTheme {
        GroupDetailScreen(
            selectedTab = GroupDetailTab.MEMBER,
            onTabSelected = {},
            groupDetailUiState = GroupDetailUiState.Success(
                GroupDetail(
                    id = 0,
                    name = "그룹 이름",
                    members = groupMembers
                )
            ),
            lazyPagingGroupSchedules = flowOf(PagingData.empty<GroupSchedule>())
                .collectAsLazyPagingItems(),
        )
    }
}