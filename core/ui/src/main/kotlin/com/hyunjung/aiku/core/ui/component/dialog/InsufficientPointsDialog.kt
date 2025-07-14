package com.hyunjung.aiku.core.ui.component.dialog

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuDialog
import com.hyunjung.aiku.core.designsystem.component.AikuSurface
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography
import com.hyunjung.aiku.core.ui.R

@Composable
fun InsufficientPointsDialog(
    points: Int,
    onGoToRecharge: () -> Unit,
    onDismiss: () -> Unit,
) {
    AikuDialog(
        onDismiss = onDismiss,
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = AiKUTheme.colors.white,
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.insufficient_points_dialog_title),
                style = AikuTypography.Subtitle1,
                color = AiKUTheme.colors.typo,
            )
            Text(
                text = stringResource(R.string.insufficient_points_dialog_message),
                textAlign = TextAlign.Center,
                style = AikuTypography.Caption1_Medium,
                color = AiKUTheme.colors.typo,
                modifier = Modifier.padding(top = 12.dp),
            )
            Column(
                modifier = Modifier.padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val textStyle = AikuTypography.Body2_SemiBold
                val iconSize = with(LocalDensity.current) { textStyle.lineHeight.toDp() }
                Text(
                    text = stringResource(R.string.insufficient_points_dialog_current_points_label),
                    style = AikuTypography.Caption1_Medium,
                    color = AiKUTheme.colors.typo,
                )
                AikuSurface(
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(width = 2.dp, color = AiKUTheme.colors.cobaltBlue),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Icon(
                            painter = AikuIcons.Aku,
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(iconSize)
                        )
                        Text(
                            text = stringResource(R.string.point_unit, points),
                            style = textStyle,
                            color = AiKUTheme.colors.cobaltBlue,
                        )
                    }
                }
            }
            AikuButton(
                onClick = onGoToRecharge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(12.dp),
            ) {
                Text(
                    text = stringResource(R.string.insufficient_points_dialog_button),
                    style = AikuTypography.Body1_SemiBold,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InsufficientPointsDialogPreview() {
    AiKUTheme {
        InsufficientPointsDialog(
            points = 50,
            onGoToRecharge = {},
            onDismiss = {},
        )
    }
}