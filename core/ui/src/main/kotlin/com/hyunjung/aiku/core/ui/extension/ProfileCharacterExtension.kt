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
            ProfileCharacter.BOY -> R.drawable.img_char_head_boy
            ProfileCharacter.BABY -> R.drawable.img_char_head_baby
            ProfileCharacter.SCRATCH -> R.drawable.img_char_head_scratch
            ProfileCharacter.GIRL -> R.drawable.img_char_head_girl
        }
    )


@Composable
fun ProfileCharacter.getDescription(): String =
    stringResource(
        when (this) {
            ProfileCharacter.BOY -> R.string.char_head_boy
            ProfileCharacter.BABY -> R.string.char_head_baby
            ProfileCharacter.SCRATCH -> R.string.char_head_scratch
            ProfileCharacter.GIRL -> R.string.char_head_girl
        }
    )