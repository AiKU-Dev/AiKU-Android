package com.hyunjung.aiku.feature.group.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.LocalAikuTextStyle
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.group.GroupMember
import com.hyunjung.aiku.core.model.profile.MemberProfileImage
import com.hyunjung.aiku.core.ui.component.common.EmptyPlaceholder
import com.hyunjung.aiku.core.ui.extension.backgroundColor
import com.hyunjung.aiku.core.ui.extension.painter
import com.hyunjung.aiku.core.ui.preview.GroupMemberListPreviewParameterProvider
import com.hyunjung.aiku.feature.group.detail.R
import com.hyunjung.aiku.core.ui.R as UiR

@Composable
internal fun GroupMemberTab(
    groupMembers: List<GroupMember>,
    onMemberClick: (Long) -> Unit,
    onInviteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (groupMembers.isEmpty()) {
        Box(modifier = modifier) {
            EmptyPlaceholder(
                title = stringResource(R.string.group_detail_member_tab_empty_title),
                buttonText = stringResource(R.string.group_detail_member_tab_empty_button),
                onClickButton = onInviteClick,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        CompositionLocalProvider(
            LocalAikuTextStyle provides AiKUTheme.typography.body2SemiBold,
        ) {
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(40.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(28.dp)
            ) {
                item {
                    GroupMemberProfile(
                        image = painterResource(UiR.drawable.img_char_head_unknown),
                        label = stringResource(R.string.group_detail_member_tab_invite),
                        backgroundColor = AiKUTheme.colors.gray02,
                        contentDescription = stringResource(R.string.group_detail_member_tab_invite),
                        onClick = onInviteClick,
                        imagePadding = PaddingValues(8.dp)
                    )
                }

                items(items = groupMembers, key = { it.id }) { member ->
                    GroupMemberProfile(
                        image = member.memberProfileImage.painter(),
                        label = member.nickname,
                        backgroundColor = member.memberProfileImage.backgroundColor(),
                        contentDescription = stringResource(R.string.group_detail_member_profile_image_description),
                        onClick = { onMemberClick(member.id) },
                        imagePadding = if (member.memberProfileImage is MemberProfileImage.Avatar) {
                            PaddingValues(8.dp)
                        } else {
                            PaddingValues(0.dp)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun GroupMemberTabPreview(
    @PreviewParameter(GroupMemberListPreviewParameterProvider::class)
    groupMembers: List<GroupMember>
) {
    AiKUTheme {
        GroupMemberTab(
            groupMembers = groupMembers,
            onMemberClick = {},
            onInviteClick = {},
            modifier = Modifier
        )
    }
}