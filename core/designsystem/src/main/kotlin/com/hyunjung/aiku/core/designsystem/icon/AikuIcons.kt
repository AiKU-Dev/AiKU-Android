package com.hyunjung.aiku.core.designsystem.icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.hyunjung.aiku.core.designsystem.R

object AikuIcons {
    val Account: Painter
        @Composable get() = painterResource(id = R.drawable.core_designsystem_ic_account)
    val Aku: Painter
        @Composable get() = painterResource(id = R.drawable.core_designsystem_ic_aku)
    val Calendar: Painter
        @Composable get() = painterResource(id = R.drawable.core_designsystem_ic_calendar)
    val Location: Painter
        @Composable get() = painterResource(id = R.drawable.core_designsystem_ic_location)

}