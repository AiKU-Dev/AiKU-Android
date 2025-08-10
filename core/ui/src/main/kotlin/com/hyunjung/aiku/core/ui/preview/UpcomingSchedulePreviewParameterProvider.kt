package com.hyunjung.aiku.core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.hyunjung.aiku.core.model.schedule.UpcomingSchedule
import com.hyunjung.aiku.core.ui.preview.PreviewParameterData.upcomingSchedules

class UpcomingSchedulePreviewParameterProvider : PreviewParameterProvider<List<UpcomingSchedule>> {

    override val values: Sequence<List<UpcomingSchedule>> =
        sequenceOf(emptyList(), upcomingSchedules)
}