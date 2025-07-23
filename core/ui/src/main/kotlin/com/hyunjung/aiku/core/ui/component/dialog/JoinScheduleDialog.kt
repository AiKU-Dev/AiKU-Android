package com.hyunjung.aiku.core.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuDialog
import com.hyunjung.aiku.core.designsystem.component.AikuIcon
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.schedule.ScheduleStatus
import com.hyunjung.aiku.core.ui.R
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun JoinScheduleDialog(
    schedule: GroupSchedule,
    onJoin: () -> Unit,
    onDismiss: () -> Unit,
) {
    val dateTimeFormatter = remember { SimpleDateFormat("yyyy. MM. dd E | a hh:mm", Locale.KOREAN) }

    AikuDialog(onDismiss = onDismiss) {
        Column(
            modifier = Modifier
                .background(
                    color = AiKUTheme.colors.white,
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(16.dp),
        ) {
            AikuText(
                text = schedule.scheduleName,
                style = AiKUTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(vertical = 28.dp, horizontal = 8.dp)
            ) {
                TextWithLeadingIcon(
                    text = stringResource(
                        R.string.join_schedule_dialog_participant_format,
                        schedule.hostName,
                        schedule.participantCount,
                    ),
                    painter = AikuIcons.Account,
                )
                TextWithLeadingIcon(
                    text = schedule.location,
                    painter = AikuIcons.Location,
                )
                TextWithLeadingIcon(
                    text = dateTimeFormatter.format(schedule.time),
                    painter = AikuIcons.Calendar,
                )
            }
            Column {
                AikuText(
                    text = stringResource(R.string.join_schedule_dialog_point_notice),
                    style = AiKUTheme.typography.body2,
                    color = AiKUTheme.colors.red01,
                    modifier = Modifier.padding(8.dp)
                )
                AikuButton(
                    onClick = onJoin,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    AikuText(text = stringResource(R.string.join_schedule_dialog_button))
                }
            }
        }
    }
}

data class GroupSchedule(
    val id: Long,
    val hostName: String,
    val scheduleName: String,
    val location: String,
    val participantCount: Int,
    val scheduleStatus: ScheduleStatus,
    val time: Long,
)

@Composable
private fun TextWithLeadingIcon(
    text: String,
    painter: Painter,
    modifier: Modifier = Modifier
) {
    val textStyle = AiKUTheme.typography.body1
    val iconColor = AiKUTheme.colors.gray03
    val iconSize = with(LocalDensity.current) { textStyle.lineHeight.toDp() }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AikuIcon(
            painter = painter,
            contentDescription = null,
            tint = iconColor,
            modifier = modifier.size(iconSize)
        )
        AikuText(
            text = text,
            style = textStyle,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JoinScheduleDialogPreview() {
    AiKUTheme {
        JoinScheduleDialog(
            schedule = GroupSchedule(
                id = 0,
                hostName = "사용자1",
                participantCount = 5,
                scheduleName = "약속 이름",
                location = "홍대 입구역 1번 출구",
                scheduleStatus = ScheduleStatus.BEFORE_JOIN,
                time = 1734048000000L,
            ),
            onJoin = {},
            onDismiss = {},
        )
    }
}