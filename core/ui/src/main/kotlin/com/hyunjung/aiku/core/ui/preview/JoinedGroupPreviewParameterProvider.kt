package com.hyunjung.aiku.core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.hyunjung.aiku.core.model.group.JoinedGroup
import com.hyunjung.aiku.core.ui.preview.PreviewParameterData.JoinedGroups

class JoinedGroupPreviewParameterProvider : PreviewParameterProvider<List<JoinedGroup>> {

    override val values: Sequence<List<JoinedGroup>> = sequenceOf(JoinedGroups)
}