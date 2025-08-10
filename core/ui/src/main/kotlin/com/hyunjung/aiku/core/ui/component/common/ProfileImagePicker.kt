package com.hyunjung.aiku.core.ui.component.common

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.hyunjung.aiku.core.designsystem.component.AikuClickableSurface
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.ProfileBackgroundColor
import com.hyunjung.aiku.core.model.profile.PendingProfileImage
import com.hyunjung.aiku.core.ui.R
import com.hyunjung.aiku.core.ui.component.dialog.CharacterProfilePickerDialog
import com.hyunjung.aiku.core.ui.extension.toColor
import com.hyunjung.aiku.core.ui.extension.toPainter
import com.hyunjung.aiku.core.ui.preview.AikuPreviewTheme

@Composable
fun ProfileImagePicker(
    pendingProfileImage: PendingProfileImage,
    isOptionMenuVisible: Boolean,
    onProfileImageOptionMenuDisMiss: () -> Unit,
    onEditClick: () -> Unit,
    onCharacterProfileSelected: (PendingProfileImage.Avatar) -> Unit,
    onAlbumImageSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showCharacterPicker by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> uri?.let { onAlbumImageSelected(uri) } }

    if (showCharacterPicker) {
        val avatar = pendingProfileImage as? PendingProfileImage.Avatar
            ?: PendingProfileImage.Avatar(
                type = AvatarType.BOY,
                backgroundColor = ProfileBackgroundColor.GREEN
            )
        CharacterProfilePickerDialog(
            avatar = avatar,
            onDismiss = { showCharacterPicker = false },
            onCharacterProfileSelected = onCharacterProfileSelected,
        )
    }

    Box(modifier = modifier) {
        Box(modifier = Modifier.align(Alignment.Center)) {
            AikuClickableSurface(
                onClick = onEditClick,
                color = pendingProfileImage.backgroundColor(),
                shape = CircleShape,
            ) {
                Image(
                    painter = pendingProfileImage.painter(),
                    contentDescription = stringResource(R.string.profile_image_picker_description),
                    modifier = Modifier
                        .size(148.dp)
                        .padding(pendingProfileImage.padding()),
                    contentScale = ContentScale.Crop
                )
            }
            Image(
                painter = AikuIcons.Edit,
                modifier = Modifier
                    .size(40.dp)
                    .background(color = AiKUTheme.colors.white, shape = CircleShape)
                    .border(
                        width = 1.dp,
                        color = AiKUTheme.colors.gray02,
                        shape = CircleShape,
                    )
                    .padding(8.dp)
                    .clip(CircleShape)
                    .align(Alignment.BottomEnd),
                contentDescription = stringResource(R.string.ic_edit_description)
            )
        }
        ProfileImageOptionMenu(
            visible = isOptionMenuVisible,
            onCharacterMenuClick = {
                showCharacterPicker = true
            },
            onGalleryMenuClick = {
                imagePickerLauncher.launch("image/*")
            },
            onDismiss = onProfileImageOptionMenuDisMiss
        )
    }
}

@Composable
private fun ProfileImageOptionMenu(
    visible: Boolean,
    onCharacterMenuClick: () -> Unit,
    onGalleryMenuClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + scaleIn(initialScale = 0.9f),
        exit = fadeOut() + scaleOut(targetScale = 0.9f),
        modifier = Modifier.padding(start = 12.dp)
    ) {
        Column(
            modifier = modifier
                .background(AiKUTheme.colors.gray04, shape = RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileImageOptionItem(
                text = stringResource(R.string.profile_image_option_Menu_character),
                onClick = {
                    onCharacterMenuClick()
                    onDismiss()
                }
            )
            ProfileImageOptionItem(
                text = stringResource(R.string.profile_image_option_Menu_gallery),
                onClick = {
                    onGalleryMenuClick()
                    onDismiss()
                }
            )
        }
    }
}

@Composable
private fun ProfileImageOptionItem(
    text: String,
    onClick: () -> Unit,
) {
    AikuClickableSurface(
        onClick = onClick,
        contentColor = AiKUTheme.colors.white
    ) {
        AikuText(
            text = text,
            style = AiKUTheme.typography.subtitle3,
        )
    }
}

@Preview(name = "옵션 메뉴 OFF")
@Composable
private fun ProfileImagePickerCollapsedPreview() {
    AikuPreviewTheme {
        ProfileImagePicker(
            pendingProfileImage = PendingProfileImage.Avatar(
                type = AvatarType.BOY,
                backgroundColor = ProfileBackgroundColor.GREEN
            ),
            isOptionMenuVisible = false,
            onProfileImageOptionMenuDisMiss = {},
            onEditClick = {},
            onCharacterProfileSelected = {},
            onAlbumImageSelected = {},
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Preview(name = "옵션 메뉴 ON")
@Composable
private fun ProfileImagePickerExpandedPreview() {
    AikuPreviewTheme {
        ProfileImagePicker(
            pendingProfileImage = PendingProfileImage.Avatar(
                type = AvatarType.BOY,
                backgroundColor = ProfileBackgroundColor.GREEN
            ),
            isOptionMenuVisible = true,
            onProfileImageOptionMenuDisMiss = {},
            onEditClick = {},
            onCharacterProfileSelected = {},
            onAlbumImageSelected = {},
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Composable
private fun PendingProfileImage.painter(): Painter = when (this) {
    is PendingProfileImage.Avatar -> type.toPainter()
    is PendingProfileImage.Photo -> rememberAsyncImagePainter(model = file)
}

@Composable
private fun PendingProfileImage.backgroundColor(): Color = when (this) {
    is PendingProfileImage.Avatar -> backgroundColor.toColor()
    else -> Color.Unspecified
}

private fun PendingProfileImage.padding(): Dp = when (this) {
    is PendingProfileImage.Avatar -> 20.dp
    else -> 0.dp
}