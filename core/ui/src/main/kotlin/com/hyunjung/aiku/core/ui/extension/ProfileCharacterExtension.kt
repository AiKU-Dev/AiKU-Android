package com.hyunjung.aiku.core.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.ui.R

@Composable
fun AvatarType.toPainter(): Painter =
    painterResource(
        when (this) {
            AvatarType.BOY -> R.drawable.img_char_head_boy
            AvatarType.BABY -> R.drawable.img_char_head_baby
            AvatarType.SCRATCH -> R.drawable.img_char_head_scratch
            AvatarType.GIRL -> R.drawable.img_char_head_girl
        }
    )


@Composable
fun AvatarType.getDescription(): String =
    stringResource(
        when (this) {
            AvatarType.BOY -> R.string.char_head_boy
            AvatarType.BABY -> R.string.char_head_baby
            AvatarType.SCRATCH -> R.string.char_head_scratch
            AvatarType.GIRL -> R.string.char_head_girl
        }
    )