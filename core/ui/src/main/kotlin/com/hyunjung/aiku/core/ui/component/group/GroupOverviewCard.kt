package com.hyunjung.aiku.core.ui.component.group

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuClickableSurface
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.designsystem.theme.AikuTypography
import com.hyunjung.aiku.core.ui.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun GroupOverviewCard(
    onClick: () -> Unit,
    groupName: String,
    time: LocalDateTime?,
    memberSize: Int,
    modifier: Modifier = Modifier,
) {
    val formatter = DateTimeFormatter.ofPattern("yy.MM.dd  HH:mm")
    val formattedTime =
        time?.format(formatter) ?: stringResource(R.string.schedule_empty_message)
    AikuClickableSurface(
        onClick = onClick,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        color = AiKUTheme.colors.white,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .height(82.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val fontScale = LocalDensity.current.fontScale
            val maxScale = 1.3f
            val effectiveScale = minOf(fontScale, maxScale)
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = LocalDensity.current.density,
                    fontScale = effectiveScale,
                ),
            ) {
                Column {
                    Text(
                        text = groupName,
                        style = AikuTypography.Body1_SemiBold,
                        color = AiKUTheme.colors.typo,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = stringResource(
                            R.string.group_card_recent_schedule,
                            formattedTime,
                        ),
                        style = AikuTypography.Body2,
                        color = AiKUTheme.colors.typo
                    )
                }
                Spacer(Modifier.weight(1f))
                ProfileWithBadge(
                    memberSize = memberSize,
                    avatar = painterResource(R.drawable.img_char_head_nohair),
                    contentDescription = stringResource(R.string.char_head_no_hair_description)
                )
            }
        }
    }
}

/**
 * 프로필 이미지와 뱃지(멤버 수) UI
 */
@Composable
private fun ProfileWithBadge(
    memberSize: Int,
    avatar: Painter,
    contentDescription: String?,
) {
    val badgeLabel = if (memberSize >= 100) {
        stringResource(R.string.group_card_badge_member_over_limit)
    } else {
        "$memberSize"
    }
    Box(
        modifier = Modifier.padding(end = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(
                    color = AiKUTheme.colors.gray02,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = avatar,
                contentDescription = contentDescription,
                modifier = Modifier.size(32.dp)
            )
        }
        Text(
            text = badgeLabel,
            style = AikuTypography.Caption1_Medium,
            color = AiKUTheme.colors.white,
            modifier = Modifier
                .offset(x = 40.dp, y = 4.dp)
                .background(
                    color = AiKUTheme.colors.gray06,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 4.dp)
        )
    }
}


@Preview
@Composable
private fun GroupCardPreview() {
    AiKUTheme {
        GroupOverviewCard(
            groupName = "그룹 1",
            time = null,
            onClick = {},
            memberSize = 18
        )
    }
}