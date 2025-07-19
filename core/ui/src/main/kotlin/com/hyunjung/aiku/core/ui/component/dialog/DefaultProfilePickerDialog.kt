package com.hyunjung.aiku.core.ui.component.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuClickableSurface
import com.hyunjung.aiku.core.designsystem.component.AikuDialog
import com.hyunjung.aiku.core.designsystem.component.AikuSurface
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.MemberProfile
import com.hyunjung.aiku.core.model.ProfileBackground
import com.hyunjung.aiku.core.model.ProfileCharacter
import com.hyunjung.aiku.core.ui.R
import com.hyunjung.aiku.core.ui.extension.getDescription
import com.hyunjung.aiku.core.ui.extension.toColor
import com.hyunjung.aiku.core.ui.extension.toPainter

@Composable
fun DefaultProfilePickerDialog(
    charProfile: MemberProfile.CharProfile,
    onDismiss: () -> Unit,
    onCharacterProfileSelected: (MemberProfile.CharProfile) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedCharacter by remember { mutableStateOf(charProfile.profileCharacter) }
    var selectedBackgroundColor by remember { mutableStateOf(charProfile.profileBackground) }

    AikuDialog(onDismiss = onDismiss) {
        Column(
            modifier = modifier
                .background(
                    color = AiKUTheme.colors.white,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AikuText(
                text = stringResource(R.string.character_picker_dialog_title),
                style = AiKUTheme.typography.body1
            )

            AikuSurface(
                color = selectedBackgroundColor.toColor(),
                shape = CircleShape,
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .size(104.dp)

            ) {
                Image(
                    painter = selectedCharacter.toPainter(),
                    contentDescription = stringResource(R.string.profile_image_picker_description),
                    contentScale = ContentScale.Crop
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileCharacter.entries.forEach { character ->
                    AikuClickableSurface(
                        onClick = { selectedCharacter = character },
                        color = AiKUTheme.colors.gray02,
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(8.dp),
                        border = if (selectedCharacter == character) {
                            BorderStroke(
                                width = 2.dp,
                                color = AiKUTheme.colors.cobaltBlue
                            )
                        } else null,
                        modifier = Modifier.size(52.dp)
                    ) {
                        Image(
                            painter = character.toPainter(),
                            contentDescription = selectedCharacter.getDescription(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                ProfileBackground.entries.forEach { background ->
                    AikuClickableSurface(
                        onClick = { selectedBackgroundColor = background },
                        color = background.toColor(),
                        shape = CircleShape,
                        border = if (selectedBackgroundColor == background) {
                            BorderStroke(
                                width = 2.dp,
                                color = AiKUTheme.colors.cobaltBlue
                            )
                        } else null,
                        modifier = Modifier.size(40.dp),
                        content = {}
                    )
                }
            }

            AikuButton(
                onClick = {
                    onCharacterProfileSelected(
                        MemberProfile.CharProfile(
                            profileCharacter = selectedCharacter,
                            profileBackground = selectedBackgroundColor,
                        )
                    )
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                AikuText(
                    text = stringResource(R.string.confirm),
                    style = AiKUTheme.typography.body1SemiBold,
                    color = AiKUTheme.colors.white
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateGroupDialogPreview() {
    AiKUTheme {
        DefaultProfilePickerDialog(
            MemberProfile.CharProfile(
                profileCharacter = ProfileCharacter.C01,
                profileBackground = ProfileBackground.GREEN,
            ),
            onDismiss = {},
            onCharacterProfileSelected = {}
        )
    }
}