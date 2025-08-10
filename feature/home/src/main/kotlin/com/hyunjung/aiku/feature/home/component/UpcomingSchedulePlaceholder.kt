package com.hyunjung.aiku.feature.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.feature.home.R
import com.hyunjung.aiku.core.ui.R as UiR

@Composable
internal fun UpcomingSchedulePlaceholder(
    modifier: Modifier = Modifier
) {

    val border = BorderStroke(
        color = AiKUTheme.colors.gray03,
        width = 1.dp
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .drawBehind {
                val strokeWidthPx = border.width.toPx()
                val cornerRadiusPx = 12.dp.toPx()

                val stroke = Stroke(
                    width = strokeWidthPx,
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(10f, 10f)
                    )
                )
                drawRoundRect(
                    brush = border.brush,
                    style = stroke,
                    cornerRadius = CornerRadius(cornerRadiusPx)
                )
            }
            .padding(horizontal = 24.dp),
    ) {
        Image(
            painter = painterResource(id = UiR.drawable.img_char_head_unknown),
            contentDescription = stringResource(id = UiR.string.char_head_unknown_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(48.dp)
        )
        AikuText(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(id = R.string.feature_home_upcoming_schedule_placeholder_message),
            style = AiKUTheme.typography.caption1Medium
        )
    }
}

@Preview
@Composable
private fun EmptyScheduleCardPreview() {
    AiKUTheme {
        UpcomingSchedulePlaceholder(
            modifier = Modifier
                .padding(20.dp)
                .height(132.dp)
        )
    }
}