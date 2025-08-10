package com.hyunjung.aiku.core.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.rememberAsyncImagePainter
import com.hyunjung.aiku.core.model.profile.ProfileImage

@Composable
fun ProfileImage.painter(): Painter = when (this) {
    is ProfileImage.Photo -> rememberAsyncImagePainter(model = url)
    is ProfileImage.Avatar -> type.toPainter()
}

@Composable
fun ProfileImage.backgroundColor(): Color = when (this) {
    is ProfileImage.Avatar -> backgroundColor.toColor()
    else -> Color.Unspecified
}