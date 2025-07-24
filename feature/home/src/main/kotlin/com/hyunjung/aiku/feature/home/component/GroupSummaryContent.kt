package com.hyunjung.aiku.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuButtonDefaults
import com.hyunjung.aiku.core.designsystem.component.AikuIcon
import com.hyunjung.aiku.core.designsystem.component.AikuLoadingWheel
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.group.GroupSummary
import com.hyunjung.aiku.core.ui.component.common.EmptyPlaceholder
import com.hyunjung.aiku.core.ui.paging.LazyPagingColumn
import com.hyunjung.aiku.core.ui.preview.GroupSummaryPreviewParameterProvider
import com.hyunjung.aiku.feature.home.R
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun GroupSummaryContent(
    userNickname: String,
    lazyPagingGroupSummaries: LazyPagingItems<GroupSummary>,
    onGroupClick: (Long) -> Unit,
    onShowCreateGroupDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val isGroupListEmpty = lazyPagingGroupSummaries.itemCount == 0

    Column(modifier = modifier) {
        AikuText(
            text = stringResource(R.string.feature_home_group_title, userNickname),
            style = AiKUTheme.typography.subtitle4G,
        )
        Spacer(Modifier.height(12.dp))
        Box {
            GroupSummaryList(
                lazyPagingGroupSummaries = lazyPagingGroupSummaries,
                isEmpty = isGroupListEmpty,
                onGroupSummaryClick = onGroupClick,
                onShowCreateGroupDialog = onShowCreateGroupDialog,
            )

            if (!isGroupListEmpty) {
                CreateGroupButton(onClick = onShowCreateGroupDialog)
            }
        }
    }
}

@Composable
private fun GroupSummaryList(
    lazyPagingGroupSummaries: LazyPagingItems<GroupSummary>,
    isEmpty: Boolean,
    onGroupSummaryClick: (Long) -> Unit,
    onShowCreateGroupDialog: () -> Unit,
) {
    LazyPagingColumn(
        refreshLoadState = lazyPagingGroupSummaries.loadState.refresh,
        isEmpty = isEmpty,
        loading = {
            AikuLoadingWheel(modifier = Modifier.size(80.dp))
        },
        empty = {
            EmptyPlaceholder(
                title = stringResource(R.string.feature_home_group_empty_message),
                buttonText = stringResource(R.string.feature_home_group_empty_button),
                onClickButton = onShowCreateGroupDialog,
                modifier = Modifier.fillMaxSize()
            )
        },
        error = { throwable ->
            // TODO: Error UI 교체
            AikuText(
                text = "${throwable.message}",
                style = AiKUTheme.typography.body1SemiBold
            )
        },
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 40.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = lazyPagingGroupSummaries.itemCount,
            key = { lazyPagingGroupSummaries[it]?.groupId ?: "group-$it" }
        ) { index ->
            lazyPagingGroupSummaries[index]?.let { group ->
                GroupSummaryCard(
                    groupName = group.groupName,
                    time = group.lastScheduleTime,
                    onClick = { onGroupSummaryClick(group.groupId) },
                    memberSize = group.memberSize
                )
            }
        }
    }
}

@Composable
private fun BoxScope.CreateGroupButton(onClick: () -> Unit) {
    AikuButton(
        onClick = onClick,
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
            modifier = Modifier.size(36.dp)
        )
    }
}

@Preview
@Composable
private fun GroupSummaryContentPreview(
    @PreviewParameter(GroupSummaryPreviewParameterProvider::class)
    groupSummaries: List<GroupSummary>
) {
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

    AiKUTheme {
        GroupSummaryContent(
            userNickname = "아이쿠",
            lazyPagingGroupSummaries = lazyPagingGroupSummaries,
            onGroupClick = {},
            onShowCreateGroupDialog = {},
            modifier = Modifier.padding(20.dp)
        )
    }
}