package com.hyunjung.aiku.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.hyunjung.aiku.core.designsystem.R

@Immutable
data class AikuColors(
    val cobaltBlue: Color,
    val green01: Color,
    val green02: Color,
    val green03: Color,
    val green04: Color,
    val green05: Color,
    val purple01: Color,
    val purple02: Color,
    val purple03: Color,
    val purple04: Color,
    val purple05: Color,
    val yellow01: Color,
    val yellow02: Color,
    val yellow03: Color,
    val yellow04: Color,
    val yellow05: Color,
    val akuYellow: Color,
    val gray00: Color,
    val gray01: Color,
    val gray02: Color,
    val gray03: Color,
    val gray04: Color,
    val gray05: Color,
    val gray06: Color,
    val white: Color,
    val typo: Color,
    val red01: Color,
    val orange01: Color,
    val hurryRed: Color,
    val lovePink: Color,
    val sorryGreen: Color,
    val babyPink: Color,
    val skyBlue: Color
) {
    companion object {
        /**
         * Provides the default colors for the light mode of the app.
         */
        @Composable
        fun defaultColors(): AikuColors = AikuColors(
            cobaltBlue = colorResource(id = R.color.cobalt_blue),
            green01 = colorResource(id = R.color.green_01),
            green02 = colorResource(id = R.color.green_02),
            green03 = colorResource(id = R.color.green_03),
            green04 = colorResource(id = R.color.green_04),
            green05 = colorResource(id = R.color.green_05),
            purple01 = colorResource(id = R.color.purple_01),
            purple02 = colorResource(id = R.color.purple_02),
            purple03 = colorResource(id = R.color.purple_03),
            purple04 = colorResource(id = R.color.purple_04),
            purple05 = colorResource(id = R.color.purple_05),
            yellow01 = colorResource(id = R.color.yellow_01),
            yellow02 = colorResource(id = R.color.yellow_02),
            yellow03 = colorResource(id = R.color.yellow_03),
            yellow04 = colorResource(id = R.color.yellow_04),
            yellow05 = colorResource(id = R.color.yellow_05),
            akuYellow = colorResource(id = R.color.aku_yellow),
            gray00 = colorResource(id = R.color.gray_00),
            gray01 = colorResource(id = R.color.gray_01),
            gray02 = colorResource(id = R.color.gray_02),
            gray03 = colorResource(id = R.color.gray_03),
            gray04 = colorResource(id = R.color.gray_04),
            gray05 = colorResource(id = R.color.gray_05),
            gray06 = colorResource(id = R.color.gray_06),
            white = colorResource(id = R.color.white),
            typo = colorResource(id = R.color.typo),
            red01 = colorResource(id = R.color.red_01),
            orange01 = colorResource(id = R.color.orange_01),
            hurryRed = colorResource(id = R.color.hurry_red),
            lovePink = colorResource(id = R.color.love_pink),
            sorryGreen = colorResource(id = R.color.sorry_green),
            babyPink = colorResource(id = R.color.baby_pink),
            skyBlue = colorResource(id = R.color.sky_blue)
        )
    }
}
