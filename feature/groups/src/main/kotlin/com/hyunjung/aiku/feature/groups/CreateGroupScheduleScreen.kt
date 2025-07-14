package com.hyunjung.aiku.feature.groups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuButtonDefaults
import com.hyunjung.aiku.core.designsystem.component.textfield.AikuLimitedTextField
import com.hyunjung.aiku.core.designsystem.component.textfield.AikuTextFieldDefaults
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.ui.component.dialog.DatePickerDialog
import com.hyunjung.aiku.core.ui.component.dialog.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private val ScheduleFormPadding = 16.dp
private val ScheduleButtonHeight = 52.dp

data class ScheduleFormState(
    val name: String,
    val time: Long,
    val location: String,
)

private enum class ScheduleNameValidationError(val stringResId: Int) {
    NONE(R.string.create_group_schedule_name_default_supporting_text),
    EMPTY(R.string.create_group_schedule_name_error_blank),
    TOO_SHORT(R.string.create_group_schedule_name_error_too_short),
    INVALID_CHARACTERS(R.string.create_group_schedule_name_error_special_chars)
}

@Composable
fun CreateGroupScheduleScreen(
    scheduleFormState: ScheduleFormState,
    modifier: Modifier = Modifier
) {
    val formatterDate = remember { SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN) }
    val formatterTime = remember { SimpleDateFormat("a hh:mm", Locale.KOREAN) }
    var nameValidationError by remember { mutableStateOf(ScheduleNameValidationError.NONE) }

    val calendar = remember { Calendar.getInstance() }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showTimePickerDialog by remember { mutableStateOf(false) }

    if (showDatePickerDialog) {
        DatePickerDialog(
            calendar = calendar,
            onDismiss = { showDatePickerDialog = false },
            onDateChange = { year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
            }
        )
    }

    if (showTimePickerDialog) {
        TimePickerDialog(
            calendar = calendar,
            onDismiss = { showTimePickerDialog = false },
            onTimeChange = { amPm, hourOfDay, minute ->
                calendar.set(Calendar.AM_PM, amPm)
                calendar.set(Calendar.HOUR, hourOfDay % 12)
                calendar.set(Calendar.MINUTE, minute)
            }
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 64.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            ScheduleForm(
                title = stringResource(R.string.create_group_schedule_name_title),
            ) {
                AikuLimitedTextField(
                    value = scheduleFormState.name,
                    onValueChange = {},
                    maxLength = 15,
                    placeholder = stringResource(R.string.create_group_schedule_name_placeholder),
                    showIndicator = false,
                    colors = AikuTextFieldDefaults.colors(containerColor = AiKUTheme.colors.gray01),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(ScheduleFormPadding),
                    isError = nameValidationError != ScheduleNameValidationError.NONE,
                    supporting = {
                        Text(
                            text = stringResource(nameValidationError.stringResId),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                )
            }

            ScheduleForm(
                title = stringResource(R.string.create_group_schedule_datetime_title),
                supporting = stringResource(R.string.create_group_schedule_datetime_supporting),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    ScheduleDropdownSelector(
                        text = formatterDate.format(scheduleFormState.time),
                        onClick = { showDatePickerDialog = true },
                        contentDescription = stringResource(R.string.create_group_schedule_date_description),
                        modifier = Modifier.weight(1f)
                    )
                    ScheduleDropdownSelector(
                        text = formatterTime.format(scheduleFormState.time),
                        onClick = { showTimePickerDialog = true },
                        contentDescription = stringResource(R.string.create_group_schedule_time_description),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            ScheduleForm(
                title = stringResource(R.string.create_group_schedule_location_title)
            ) {
                val textStyle = AiKUTheme.typography.caption1Medium
                val iconSize = with(LocalDensity.current) { textStyle.lineHeight.toDp() * 1.2f }

                val locationName = scheduleFormState.location.takeIf { it.isNotBlank() }
                    ?: stringResource(R.string.create_group_schedule_location_placeholder)

                AikuButton(
                    onClick = {},
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = AikuButtonDefaults.buttonColors(
                        containerColor = AiKUTheme.colors.gray01,
                        contentColor = AiKUTheme.colors.gray03,
                    )
                ) {
                    Box(Modifier.fillMaxWidth()) {
                        Text(
                            text = locationName,
                            style = AiKUTheme.typography.body2,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                        Icon(
                            painter = AikuIcons.Search,
                            contentDescription = stringResource(R.string.create_group_schedule_location_description),
                            modifier = Modifier
                                .size(iconSize)
                                .align(Alignment.CenterEnd)
                        )
                    }
                }
            }
        }

        AikuButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(ScheduleButtonHeight),
            enabled = scheduleFormState.name.isNotBlank() && nameValidationError == ScheduleNameValidationError.NONE,
        ) {
            Text(
                text = stringResource(R.string.create_group_schedule_button_create),
                style = AiKUTheme.typography.subtitle3SemiBold,
            )
        }
    }
}

@Composable
private fun ScheduleForm(
    title: String,
    modifier: Modifier = Modifier,
    supporting: String? = null,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = AiKUTheme.typography.subtitle2,
            color = AiKUTheme.colors.typo,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        content()
        supporting?.let {
            Text(
                text = it,
                style = AiKUTheme.typography.caption1,
                color = AiKUTheme.colors.typo,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
private fun ScheduleDropdownSelector(
    text: String,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textStyle = AiKUTheme.typography.caption1Medium
    val iconSize = with(LocalDensity.current) { textStyle.lineHeight.toDp() * 1.2f }

    AikuButton(
        onClick = onClick,
        contentPadding = PaddingValues(16.dp),
        modifier = modifier,
        colors = AikuButtonDefaults.buttonColors(
            containerColor = AiKUTheme.colors.gray01,
            contentColor = AiKUTheme.colors.gray03,
        )
    ) {
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = text,
                style = textStyle,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Icon(
                painter = rememberVectorPainter(AikuIcons.ChevronDown),
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(iconSize)
                    .align(Alignment.CenterEnd)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateScheduleScreenPreview() {
    val scheduleFormState = ScheduleFormState(
        name = "",
        time = 1742331600000L,
        location = ""
    )
    AiKUTheme {
        CreateGroupScheduleScreen(
            scheduleFormState = scheduleFormState,
        )
    }
}

private fun validateScheduleName(name: String): ScheduleNameValidationError {
    return when {
        name.isBlank() -> ScheduleNameValidationError.EMPTY
        name.length < 2 -> ScheduleNameValidationError.TOO_SHORT
        !name.matches(Regex("^[가-힣a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ ]+$")) -> ScheduleNameValidationError.INVALID_CHARACTERS
        else -> ScheduleNameValidationError.NONE
    }
}
