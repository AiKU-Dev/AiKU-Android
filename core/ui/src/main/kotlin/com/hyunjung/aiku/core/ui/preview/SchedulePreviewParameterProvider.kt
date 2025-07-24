package com.hyunjung.aiku.core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.hyunjung.aiku.core.model.schedule.Schedule
import com.hyunjung.aiku.core.ui.preview.PreviewParameterData.schedules

class SchedulePreviewParameterProvider : PreviewParameterProvider<List<Schedule>> {

    override val values: Sequence<List<Schedule>> = sequenceOf(schedules)
}