package com.hyunjung.aiku.core.ui.component.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuClickableSurface
import com.hyunjung.aiku.core.designsystem.component.AikuHorizontalDivider
import com.hyunjung.aiku.core.designsystem.component.AikuIcon
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.component.AikuVerticalDivider
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.schedule.Location
import com.hyunjung.aiku.core.model.schedule.ScheduleStatus
import com.hyunjung.aiku.core.ui.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

val ScheduleStatus.label: String
    get() = when (this) {
        ScheduleStatus.WAITING -> "대기 중"
        ScheduleStatus.RUNNING -> "진행 중"
        ScheduleStatus.BEFORE_JOIN -> "참가 전"
        ScheduleStatus.TERMINATED -> "종료"
    }

@Composable
fun ScheduleCard(
    onClick: () -> Unit,
    scheduleName: String,
    location: Location,
    time: LocalDateTime,
    scheduleStatus: ScheduleStatus,
    modifier: Modifier = Modifier
) {
    val formatter = remember {
        DateTimeFormatter.ofPattern("yyyy. MM. dd E | a hh:mm", Locale.KOREAN)
    }

    val scheduleStatusColor = when (scheduleStatus) {
        ScheduleStatus.WAITING -> AiKUTheme.colors.purple05
        ScheduleStatus.RUNNING -> AiKUTheme.colors.green05
        ScheduleStatus.BEFORE_JOIN -> AiKUTheme.colors.yellow05
        ScheduleStatus.TERMINATED -> AiKUTheme.colors.gray03
    }
    AikuClickableSurface(
        onClick = onClick,
        color = AiKUTheme.colors.white,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
        ) {
            AikuVerticalDivider(
                thickness = 8.dp,
                color = scheduleStatusColor,
            )
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AikuText(
                        text = scheduleName,
                        style = AiKUTheme.typography.subtitle3Bold
                    )
                    AikuText(
                        text = scheduleStatus.label,
                        style = AiKUTheme.typography.caption1SemiBold,
                        color = AiKUTheme.colors.white,
                        modifier = Modifier
                            .background(
                                color = scheduleStatusColor,
                                shape = RoundedCornerShape(30.dp)
                            )
                            .padding(
                                horizontal = 12.dp,
                                vertical = 4.dp
                            )
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    AikuIcon(
                        painter = AikuIcons.Location,
                        contentDescription = stringResource(R.string.ic_location_description),
                        tint = AiKUTheme.colors.gray00
                    )
                    AikuText(
                        text = location.locationName,
                        style = AiKUTheme.typography.caption1,
                    )
                }
                AikuHorizontalDivider(
                    color = AiKUTheme.colors.gray02,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                AikuText(
                    text = time.format(formatter),
                    style = AiKUTheme.typography.caption1,
                )
            }
        }
    }
}

@Preview
@Composable
private fun GroupScheduleCardPreview() {
    AiKUTheme {
        ScheduleCard(
            onClick = {},
            scheduleName = "약속 이름",
            location = Location(
                locationName = "홍대 입구역 1번 출구",
                latitude = 37.566535,
                longitude = 126.977969
            ),
            time = LocalDateTime.of(2024, 12, 13, 18, 0),
            scheduleStatus = ScheduleStatus.WAITING
        )
    }
}