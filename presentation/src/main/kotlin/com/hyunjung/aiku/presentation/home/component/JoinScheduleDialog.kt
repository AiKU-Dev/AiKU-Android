package com.hyunjung.aiku.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.hyunjung.aiku.core.data.model.ScheduleStatus
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuDialog
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.designsystem.theme.AikuColors
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography
import com.hyunjung.aiku.presentation.R
import com.hyunjung.aiku.presentation.home.screen.GroupSchedule
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
                    color = AikuColors.White,
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(16.dp),
        ) {
            Text(
                text = schedule.scheduleName,
                style = AikuTypography.Subtitle1,
                color = AikuColors.Typo,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(vertical = 28.dp, horizontal = 8.dp)
            ) {
                TextWithLeadingIcon(
                    text = stringResource(
                        R.string.presentation_member_detail_join_schedule_dialog_participant_format,
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
                Text(
                    text = stringResource(R.string.presentation_member_detail_join_schedule_dialog_point_notice),
                    style = AikuTypography.Body2,
                    color = AikuColors.Red01,
                    modifier = Modifier.padding(8.dp)
                )
                AikuButton(
                    onClick = onJoin,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.presentation_member_detail_join_schedule_dialog_button),
                        style = AikuTypography.Body1_SemiBold,
                        color = AikuColors.White,
                    )
                }
            }
        }
    }
}

@Composable
private fun TextWithLeadingIcon(
    text: String,
    painter: Painter,
    modifier: Modifier = Modifier
) {
    val textStyle = AikuTypography.Body1
    val iconColor = AikuColors.Gray03
    val iconSize = with(LocalDensity.current) { textStyle.lineHeight.toDp() }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = iconColor,
            modifier = modifier.size(iconSize)
        )
        Text(
            text = text,
            style = textStyle,
            color = AikuColors.Typo,
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