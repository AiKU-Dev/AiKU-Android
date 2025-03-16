package com.hyunjung.aiku.presentation.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuSurface
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.designsystem.theme.AikuColors
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography
import com.hyunjung.aiku.presentation.R

@Composable
fun EmptyScheduleCard(
    modifier: Modifier = Modifier
) {
    DashBorderSurface(
        cornerRadius = 10.dp,
        border = BorderStroke(
            color = AikuColors.Gray03,
            width = 1.dp
        ),
        modifier = modifier.size(
            width = 140.dp,
            height = 130.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.presentation_char_head_unknown),
                contentDescription = stringResource(id = R.string.presentation_char_head_unknown_description)
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(id = R.string.presentation_home_no_schedule),
                style = AikuTypography.Caption1_Medium
            )
        }
    }
}

@Composable
private fun DashBorderSurface(
    cornerRadius: Dp,
    border: BorderStroke,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AikuSurface(
        modifier = modifier
            .drawBehind {
                val strokeWidthPx = border.width.toPx()
                val cornerRadiusPx = cornerRadius.toPx()

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
            },
        content = content
    )
}

@Preview(showBackground = true, name = "Empty Schedule Card")
@Composable
private fun EmptyScheduleCardPreview() {
    AiKUTheme {
        Box(
            modifier = Modifier.padding(20.dp)
        ) {
            EmptyScheduleCard()
        }
    }
}