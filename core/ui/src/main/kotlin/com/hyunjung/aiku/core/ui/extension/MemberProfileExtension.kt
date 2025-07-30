package com.hyunjung.aiku.core.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.hyunjung.aiku.core.model.profile.MemberProfileImage
import com.hyunjung.aiku.core.model.profile.UserProfileImage

@Composable
fun UserProfileImage.painter(): Painter = when (this) {
    is UserProfileImage.Avatar -> type.toPainter()
    is UserProfileImage.Photo -> rememberAsyncImagePainter(model = file)
}

@Composable
fun UserProfileImage.backgroundColor(): Color = when (this) {
    is UserProfileImage.Avatar -> backgroundColor.toColor()
    else -> Color.Unspecified
}

fun UserProfileImage.padding(): Dp = when (this) {
    is UserProfileImage.Avatar -> 20.dp
    else -> 0.dp
}

@Composable
fun MemberProfileImage.painter(): Painter = when (this) {
    is MemberProfileImage.Photo -> rememberAsyncImagePainter(model = url)
    is MemberProfileImage.Avatar -> type.toPainter()
}

@Composable
fun MemberProfileImage.backgroundColor(): Color = when (this) {
    is MemberProfileImage.Avatar -> backgroundColor.toColor()
    else -> Color.Unspecified
}