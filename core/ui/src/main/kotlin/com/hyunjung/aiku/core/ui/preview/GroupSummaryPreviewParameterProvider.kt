package com.hyunjung.aiku.core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.hyunjung.aiku.core.model.group.GroupSummary
import com.hyunjung.aiku.core.ui.preview.PreviewParameterData.groupSummaries

class GroupSummaryPreviewParameterProvider : PreviewParameterProvider<List<GroupSummary>> {

    override val values: Sequence<List<GroupSummary>> = sequenceOf(groupSummaries)
}