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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuClickableSurface
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.ScheduleStatus
import com.hyunjung.aiku.core.ui.R
import java.text.SimpleDateFormat
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
    location: String,
    time: Long,
    scheduleStatus: ScheduleStatus,
    modifier: Modifier = Modifier
) {
    val formatter = remember { SimpleDateFormat("yyyy. MM. dd E | a hh:mm", Locale.KOREAN) }

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
            VerticalDivider(
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
                    Icon(
                        painter = AikuIcons.Location,
                        contentDescription = stringResource(R.string.ic_location_description),
                        tint = AiKUTheme.colors.gray00
                    )
                    AikuText(
                        text = location,
                        style = AiKUTheme.typography.caption1,
                    )
                }
                HorizontalDivider(
                    color = AiKUTheme.colors.gray02,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                AikuText(
                    text = formatter.format(time),
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
            location = "홍대 입구역 1번 출구",
            time = 1734048000000L,
            scheduleStatus = ScheduleStatus.WAITING
        )
    }
}