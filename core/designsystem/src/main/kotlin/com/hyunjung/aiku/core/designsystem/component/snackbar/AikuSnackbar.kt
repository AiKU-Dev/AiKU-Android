package com.hyunjung.aiku.core.designsystem.component.snackbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuSurface
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme

@Composable
fun AikuSnackbar(
    message: String,
    textStyle: TextStyle = AiKUTheme.typography.caption1,
    containerColor: Color = AiKUTheme.colors.gray04,
    contentColor: Color = AiKUTheme.colors.white,
    shape: RoundedCornerShape = AikuSnackbarDefaults.Shape,
    contentPadding: PaddingValues = AikuSnackbarDefaults.ContentPadding,
    modifier: Modifier = Modifier,
) {
    AikuSurface(
        color = containerColor,
        contentColor = contentColor,
        shape = shape,
        contentPadding = contentPadding,
        modifier = modifier,
    ) {
        AikuText(
            text = message,
            style = textStyle,
        )
    }
}

object AikuSnackbarDefaults {
    private val HorizontalPadding = 16.dp
    private val VerticalPadding = 16.dp

    val Shape = RoundedCornerShape(32.dp)

    val ContentPadding: PaddingValues
        get() = PaddingValues(
            horizontal = HorizontalPadding,
            vertical = VerticalPadding
        )
}

@Preview(showBackground = true)
@Composable
private fun AikuSnackbarPreview() {
    AiKUTheme {
        AikuSnackbar(
            message = "이것은 스낵바 메시지입니다!"
        )
    }
}