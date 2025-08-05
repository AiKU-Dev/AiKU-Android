package com.hyunjung.aiku.core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.hyunjung.aiku.core.model.group.GroupSchedule
import com.hyunjung.aiku.core.ui.preview.PreviewParameterData.groupSchedules

class GroupSchedulePreviewParameterProvider : PreviewParameterProvider<GroupSchedule> {
    override val values: Sequence<GroupSchedule> = groupSchedules.asSequence()
}

class GroupScheduleListPreviewParameterProvider : PreviewParameterProvider<List<GroupSchedule>> {
    override val values: Sequence<List<GroupSchedule>> = sequenceOf(emptyList(), groupSchedules)
}