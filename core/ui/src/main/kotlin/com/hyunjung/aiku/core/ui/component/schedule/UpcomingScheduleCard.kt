package com.hyunjung.aiku.core.ui.component.schedule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuClickableSurface
import com.hyunjung.aiku.core.designsystem.component.AikuSurface
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.ui.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun UpcomingScheduleCard(
    groupName: String,
    location: String,
    time: LocalDateTime,
    isRunning: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    // todo : 상태 표시를 위한 로직 개선 필요
    val statusLabel = stringResource(
        if (isRunning) R.string.schedule_status_running
        else R.string.schedule_status_waiting
    )
    val formatter = remember { DateTimeFormatter.ofPattern("HH:mm") }

    AikuClickableSurface(
        modifier = modifier
            .size(
                width = 140.dp,
                height = 130.dp,
            ),
        onClick = onClick,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        color = if (isRunning) AiKUTheme.colors.green05 else AiKUTheme.colors.purple05
    ) {
        val fontScale = LocalDensity.current.fontScale
        val maxScale = 1.3f
        val effectiveScale = minOf(fontScale, maxScale)

        CompositionLocalProvider(
            LocalDensity provides Density(
                density = LocalDensity.current.density,
                fontScale = effectiveScale,
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = groupName,
                    style = AiKUTheme.typography.caption1,
                    color = AiKUTheme.colors.gray04,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = formatter.format(time),
                    style = AiKUTheme.typography.subtitle3SemiBold,
                )
                Text(
                    text = location,
                    style = AiKUTheme.typography.caption1Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.weight(1f))
                AikuSurface(
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = AiKUTheme.colors.white
                    ),
                ) {
                    Text(
                        text = statusLabel,
                        style = AiKUTheme.typography.caption1SemiBold,
                        color = AiKUTheme.colors.white,
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true, name = "Schedule Card")
@Composable
private fun ScheduleCardPreview() {
    AiKUTheme {
        Box(
            modifier = Modifier.padding(20.dp)
        ) {
            UpcomingScheduleCard(
                onClick = {},
                groupName = "가나다라마바사아자차카타파하",
                location = "홍대입구역1번 출구 스타벅스 앞",
                time = LocalDateTime.parse("2025-06-30T12:12:12"),
                isRunning = true
            )
        }
    }
}