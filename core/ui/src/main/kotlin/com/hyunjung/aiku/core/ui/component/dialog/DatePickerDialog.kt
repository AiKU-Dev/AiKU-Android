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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import com.hyunjung.aiku.core.designsystem.component.AikuVerticalDivider
import com.hyunjung.aiku.core.designsystem.component.Picker
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.ui.R
import java.util.Calendar

@Composable
fun DatePickerDialog(
    calendar: Calendar,
    onDismiss: () -> Unit,
    onDateChange: (year: Int, month: Int, day: Int) -> Unit,
    modifier: Modifier = Modifier,
    visibleItemsCount: Int = 3,
    itemPadding: PaddingValues = PaddingValues(8.dp),
    textStyle: TextStyle = AiKUTheme.typography.subtitle3,
    selectedTextStyle: TextStyle = AiKUTheme.typography.subtitle2,
) {

    val newCalendar = remember { Calendar.getInstance() }
    val currentYear = newCalendar.get(Calendar.YEAR)

    var selectedYear by remember { mutableIntStateOf(calendar.get(Calendar.YEAR)) }
    var selectedMonth by remember { mutableIntStateOf(calendar.get(Calendar.MONTH)) }
    var selectedDay by remember { mutableIntStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    val yearRange = (currentYear..currentYear + 9).toList()
    val monthRange = (1..12).toList()
    val dayRange by remember(selectedYear, selectedMonth) {
        derivedStateOf {
            val tempCal = Calendar.getInstance().apply {
                set(Calendar.YEAR, selectedYear)
                set(Calendar.MONTH, selectedMonth)
                set(Calendar.DAY_OF_MONTH, 1)
            }
            val daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH)
            (1..daysInMonth).toList()
        }
    }

    val yearStartIndex = remember { yearRange.indexOf(selectedYear) }
    val monthStartIndex = remember { monthRange.indexOf(selectedMonth + 1) }
    val dayStartIndex = remember { dayRange.indexOf(selectedDay) }

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
                    items = yearRange,
                    onItemSelected = { selectedYear = it },
                    modifier = Modifier.weight(1f),
                    startIndex = yearStartIndex,
                    visibleItemsCount = visibleItemsCount,
                    textStyle = textStyle,
                    selectedTextStyle = selectedTextStyle,
                    itemPadding = itemPadding,
                )
                AikuVerticalDivider(modifier = Modifier.height(itemHeight))
                Picker(
                    items = monthRange,
                    onItemSelected = { selectedMonth = it - 1 },
                    modifier = Modifier.weight(1f),
                    startIndex = monthStartIndex,
                    visibleItemsCount = visibleItemsCount,
                    textStyle = textStyle,
                    selectedTextStyle = selectedTextStyle,
                    itemPadding = itemPadding
                )
                AikuVerticalDivider(modifier = Modifier.height(itemHeight))
                Picker(
                    items = dayRange,
                    onItemSelected = { selectedDay = it },
                    modifier = Modifier.weight(1f),
                    startIndex = dayStartIndex,
                    visibleItemsCount = visibleItemsCount,
                    textStyle = textStyle,
                    selectedTextStyle = selectedTextStyle,
                    itemPadding = itemPadding,
                )
            }

            Spacer(Modifier.height(20.dp))
            AikuButton(
                onClick = {
                    onDateChange(selectedYear, selectedMonth, selectedDay)
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
private fun DatePickerDialogPreview() {
    AiKUTheme {
        val calendar = remember { Calendar.getInstance() }
        DatePickerDialog(
            calendar = calendar,
            onDismiss = {},
            onDateChange = { _, _, _ -> }
        )
    }
}