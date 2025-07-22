package com.hyunjung.aiku.core.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hyunjung.aiku.core.model.profile.AvatarCharacter
import com.hyunjung.aiku.core.ui.R

@Composable
fun AvatarCharacter.toPainter(): Painter =
    painterResource(
        when (this) {
            AvatarCharacter.BOY -> R.drawable.img_char_head_boy
            AvatarCharacter.BABY -> R.drawable.img_char_head_baby
            AvatarCharacter.SCRATCH -> R.drawable.img_char_head_scratch
            AvatarCharacter.GIRL -> R.drawable.img_char_head_girl
        }
    )


@Composable
fun AvatarCharacter.getDescription(): String =
    stringResource(
        when (this) {
            AvatarCharacter.BOY -> R.string.char_head_boy
            AvatarCharacter.BABY -> R.string.char_head_baby
            AvatarCharacter.SCRATCH -> R.string.char_head_scratch
            AvatarCharacter.GIRL -> R.string.char_head_girl
        }
    )