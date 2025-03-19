package com.hyunjung.aiku.presentation.home.component

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
import com.hyunjung.aiku.core.designsystem.theme.AikuColors
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography
import com.hyunjung.aiku.presentation.R
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ScheduleCard(
    groupName: String,
    location: String,
    time: Long,
    isRunning: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val statusLabel = stringResource(
        if (isRunning) R.string.presentation_schedule_card_status_running
        else R.string.presentation_schedule_card_status_waiting
    )

    AikuClickableSurface(
        modifier = modifier
            .size(
                width = 140.dp,
                height = 130.dp,
            ),
        onClick = onClick,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        color = if (isRunning) AikuColors.Green05 else AikuColors.Purple05
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
                    style = AikuTypography.Caption1,
                    color = AikuColors.Gray04,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = SimpleDateFormat("HH:mm", Locale.KOREAN).format(time),
                    style = AikuTypography.Subtitle3_SemiBold,
                )
                Text(
                    text = location,
                    style = AikuTypography.Caption1_Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.weight(1f))
                AikuSurface(
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = AikuColors.White
                    ),
                ) {
                    Text(
                        text = statusLabel,
                        style = AikuTypography.Caption1_SemiBold,
                        color = AikuColors.White,
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
            ScheduleCard(
                onClick = {},
                groupName = "가나다라마바사아자차카타파하",
                location = "홍대입구역1번 출구 스타벅스 앞",
                time = 1742889600000L,
                isRunning = true
            )
        }
    }
}