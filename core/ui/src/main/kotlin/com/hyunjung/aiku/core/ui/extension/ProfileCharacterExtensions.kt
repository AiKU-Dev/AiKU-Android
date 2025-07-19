package com.hyunjung.aiku.core.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hyunjung.aiku.core.model.ProfileCharacter
import com.hyunjung.aiku.core.ui.R

@Composable
fun ProfileCharacter.toPainter(): Painter =
    painterResource(
        when (this) {
            ProfileCharacter.C01 -> R.drawable.img_char_head_boy
            ProfileCharacter.C02 -> R.drawable.img_char_head_baby
            ProfileCharacter.C03 -> R.drawable.img_char_head_scratch
            ProfileCharacter.C04 -> R.drawable.img_char_head_girl
        }
    )


@Composable
fun ProfileCharacter.getDescription(): String =
    stringResource(
        when (this) {
            ProfileCharacter.C01 -> R.string.char_head_boy
            ProfileCharacter.C02 -> R.string.char_head_baby
            ProfileCharacter.C03 -> R.string.char_head_scratch
            ProfileCharacter.C04 -> R.string.char_head_girl
        }
    )