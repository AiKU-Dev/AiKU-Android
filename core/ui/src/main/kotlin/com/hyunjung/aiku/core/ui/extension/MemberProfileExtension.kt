package com.hyunjung.aiku.core.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.hyunjung.aiku.core.model.profile.UserProfile

@Composable
fun UserProfile.painter(): Painter = when (this) {
    is UserProfile.Avatar -> avatarCharacter.toPainter()
    is UserProfile.Image -> rememberAsyncImagePainter(model = file)
}

@Composable
fun UserProfile.backgroundColor(): Color = when (this) {
    is UserProfile.Avatar -> avatarBackground.toColor()
    else -> Color.Unspecified
}

fun UserProfile.padding(): Dp = when (this) {
    is UserProfile.Avatar -> 20.dp
    else -> 0.dp
}
