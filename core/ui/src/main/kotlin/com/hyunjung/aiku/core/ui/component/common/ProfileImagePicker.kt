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
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuClickableSurface
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.MemberProfile
import com.hyunjung.aiku.core.model.ProfileBackground
import com.hyunjung.aiku.core.model.ProfileCharacter
import com.hyunjung.aiku.core.ui.R
import com.hyunjung.aiku.core.ui.component.dialog.CharacterProfilePickerDialog
import com.hyunjung.aiku.core.ui.extension.backgroundColor
import com.hyunjung.aiku.core.ui.extension.padding
import com.hyunjung.aiku.core.ui.extension.painter
import com.hyunjung.aiku.core.ui.preview.AikuPreviewTheme

@Composable
fun ProfileImagePicker(
    memberProfile: MemberProfile,
    isOptionMenuVisible: Boolean,
    onProfileImageOptionMenuDisMiss: () -> Unit,
    onEditClick: () -> Unit,
    onCharacterProfileSelected: (MemberProfile.Character) -> Unit,
    onAlbumImageSelected: (MemberProfile.GalleryImage) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showCharacterPicker by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            onAlbumImageSelected(
                MemberProfile.GalleryImage(it.toString())
            )
        }
    }

    if (showCharacterPicker) {
        val character = memberProfile as? MemberProfile.Character
            ?: MemberProfile.Character(
                profileCharacter = ProfileCharacter.BOY,
                profileBackground = ProfileBackground.GREEN
            )
        CharacterProfilePickerDialog(
            character = character,
            onDismiss = { showCharacterPicker = false },
            onCharacterProfileSelected = onCharacterProfileSelected,
        )
    }

    Box(modifier = modifier.fillMaxWidth()) {
        Box(modifier = Modifier.align(Alignment.Center)) {
            AikuClickableSurface(
                onClick = onEditClick,
                color = memberProfile.backgroundColor(),
                shape = CircleShape,
            ) {
                Image(
                    painter = memberProfile.painter(),
                    contentDescription = stringResource(R.string.profile_image_picker_description),
                    modifier = Modifier
                        .size(148.dp)
                        .padding(memberProfile.padding()),
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
            memberProfile = MemberProfile.Character(
                profileCharacter = ProfileCharacter.BOY,
                profileBackground = ProfileBackground.GREEN
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
            memberProfile = MemberProfile.Character(
                profileCharacter = ProfileCharacter.BOY,
                profileBackground = ProfileBackground.GREEN
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