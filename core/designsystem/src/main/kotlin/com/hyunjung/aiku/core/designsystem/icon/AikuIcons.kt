package com.hyunjung.aiku.core.designsystem.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.hyunjung.aiku.core.designsystem.R

object AikuIcons {
    val Account: Painter
        @Composable get() = painterResource(id = R.drawable.core_designsystem_ic_account)
    val Add = Icons.Rounded.Add
    val Aku: Painter
        @Composable get() = painterResource(id = R.drawable.core_designsystem_ic_aku)
    val ArrowBackIosNew = Icons.Rounded.ArrowBackIosNew
    val Calendar: Painter
        @Composable get() = painterResource(id = R.drawable.core_designsystem_ic_calendar)
    val Check: Painter
        @Composable get() = painterResource(id = R.drawable.core_designsystem_ic_check)
    val Edit: Painter
        @Composable get() = painterResource(id = R.drawable.core_designsystem_ic_edit)
    val Location: Painter
        @Composable get() = painterResource(id = R.drawable.core_designsystem_ic_location)
    val ChevronDown = Icons.Rounded.KeyboardArrowDown
    val Search: Painter
        @Composable get() = painterResource(id = R.drawable.core_designsystem_ic_search)
    val MoreHoriz = Icons.Rounded.MoreHoriz
    val Home: Painter
        @Composable get() = painterResource(id = R.drawable.core_designsystem_ic_home)
    val Schedule: Painter
        @Composable get() = painterResource(id = R.drawable.core_designsystem_ic_schedule)
    val ArrowRight: Painter
        @Composable get() = painterResource(id = R.drawable.core_designsystem_arrow_right)
}