package com.hyunjung.aiku.core.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.hyunjung.aiku.core.model.MemberProfile

@Composable
fun MemberProfile.painter(): Painter = when (this) {
    is MemberProfile.Character -> profileCharacter.toPainter()
    is MemberProfile.RemoteImage -> rememberAsyncImagePainter(model = imageUrl)
    is MemberProfile.GalleryImage -> rememberAsyncImagePainter(model = imageUri)
}

@Composable
fun MemberProfile.backgroundColor(): Color = when (this) {
    is MemberProfile.Character -> profileBackground.toColor()
    else -> Color.Unspecified
}

fun MemberProfile.padding(): Dp = when (this) {
    is MemberProfile.Character -> 20.dp
    else -> 0.dp
}
