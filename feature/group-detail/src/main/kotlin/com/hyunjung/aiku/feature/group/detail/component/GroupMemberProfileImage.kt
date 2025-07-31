package com.hyunjung.aiku.feature.group.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuClickableSurface
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.ui.R

@Composable
internal fun GroupMemberProfile(
    image: Painter,
    label: String,
    backgroundColor: Color,
    contentDescription: String?,
    onClick: () -> Unit,
    imagePadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        AikuClickableSurface(
            onClick = onClick,
            color = backgroundColor,
            shape = CircleShape,
            contentPadding = imagePadding,
            modifier = Modifier.aspectRatio(1f)
        ) {
            Image(
                painter = image,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
        }
        AikuText(
            text = label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun GroupMemberProfilePreview() {
    AiKUTheme {
        GroupMemberProfile(
            image = painterResource(R.drawable.img_char_head_boy),
            label = "Nickname",
            backgroundColor = AiKUTheme.colors.green05,
            contentDescription = null,
            onClick = {},
            imagePadding = PaddingValues(8.dp),
            modifier = Modifier
                .width(120.dp)
                .padding(12.dp)
        )
    }
}