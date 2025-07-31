package com.hyunjung.aiku.core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.hyunjung.aiku.core.model.group.GroupMember
import com.hyunjung.aiku.core.ui.preview.PreviewParameterData.groupMembers

class GroupMemberPreviewParameterProvider : PreviewParameterProvider<List<GroupMember>> {

    override val values: Sequence<List<GroupMember>> = sequenceOf(groupMembers)
}
