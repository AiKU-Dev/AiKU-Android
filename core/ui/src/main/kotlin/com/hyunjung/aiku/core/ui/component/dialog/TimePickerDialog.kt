package com.hyunjung.aiku.core.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuDialog
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.component.Picker
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.ui.R
import java.util.Calendar

@Composable
fun TimePickerDialog(
    calendar: Calendar,
    onDismiss: () -> Unit,
    onTimeChange: (year: Int, month: Int, day: Int) -> Unit,
    modifier: Modifier = Modifier,
    visibleItemsCount: Int = 3,
    itemPadding: PaddingValues = PaddingValues(8.dp),
    textStyle: TextStyle = AiKUTheme.typography.subtitle3,
    selectedTextStyle: TextStyle = AiKUTheme.typography.subtitle2,
) {
    val currentAMPM = calendar.get(Calendar.AM_PM)
    val currentHour = calendar.get(Calendar.HOUR)
    val currentMin = calendar.get(Calendar.MINUTE)

    var selectedAMPM by remember { mutableIntStateOf(currentAMPM) }
    var selectedHour by remember { mutableIntStateOf(currentHour) }
    var selectedMin by remember { mutableIntStateOf(currentMin) }

    val hourRange = (1..12).map { it.toString().padStart(2, '0') }
    val minRange = (0..59).map { it.toString().padStart(2, '0') }

    val itemHeight = with(LocalDensity.current) {
        selectedTextStyle.fontSize.toDp()
    }

    AikuDialog(
        onDismiss = onDismiss
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = AiKUTheme.colors.white,
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Picker(
                    items = listOf("오전", "오후"),
                    onItemSelected = { selectedAMPM = if (it == "오전") 0 else 1 },
                    modifier = Modifier.weight(1f),
                    startIndex = selectedAMPM,
                    visibleItemsCount = visibleItemsCount,
                    textStyle = textStyle,
                    selectedTextStyle = selectedTextStyle,
                    itemPadding = itemPadding,
                )
                VerticalDivider(
                    modifier = Modifier.height(itemHeight),
                    color = AiKUTheme.colors.typo
                )
                Picker(
                    items = hourRange,
                    onItemSelected = { selectedHour = it.toInt() },
                    modifier = Modifier.weight(1f),
                    startIndex = selectedHour - 1,
                    visibleItemsCount = visibleItemsCount,
                    textStyle = textStyle,
                    selectedTextStyle = selectedTextStyle,
                    itemPadding = itemPadding,
                    isInfinity = true,
                )
                VerticalDivider(
                    modifier = Modifier.height(itemHeight),
                    color = AiKUTheme.colors.typo
                )
                Picker(
                    items = minRange,
                    onItemSelected = { selectedMin = it.toInt() },
                    modifier = Modifier.weight(1f),
                    startIndex = selectedMin,
                    visibleItemsCount = visibleItemsCount,
                    textStyle = textStyle,
                    selectedTextStyle = selectedTextStyle,
                    itemPadding = itemPadding,
                    isInfinity = true,
                )
            }

            Spacer(Modifier.height(20.dp))
            AikuButton(
                onClick = {
                    onTimeChange(selectedAMPM, selectedHour, selectedMin)
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 54.dp)
                    .padding(horizontal = 20.dp)
            ) {
                AikuText(text = stringResource(R.string.date_picker_dialog_button))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TimePickerDialogPreview() {
    AiKUTheme {
        val calendar = remember { Calendar.getInstance() }
        TimePickerDialog(
            calendar = calendar,
            onDismiss = {},
            onTimeChange = { _, _, _ -> }
        )
    }
}