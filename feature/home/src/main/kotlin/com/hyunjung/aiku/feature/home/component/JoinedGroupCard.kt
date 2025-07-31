package com.hyunjung.aiku.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.hyunjung.aiku.core.designsystem.component.AikuClickableSurface
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.feature.home.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.hyunjung.aiku.core.ui.R as UiR

@Composable
internal fun JoinedGroupCard(
    onClick: () -> Unit,
    groupName: String,
    time: LocalDateTime?,
    memberSize: Int,
    modifier: Modifier = Modifier,
) {
    val formatter = DateTimeFormatter.ofPattern("yy.MM.dd  HH:mm")
    val formattedTime = time?.format(formatter)
        ?: stringResource(R.string.feature_home_upcoming_schedule_placeholder_message)

    AikuClickableSurface(
        onClick = onClick,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        color = AiKUTheme.colors.white,
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .height(80.dp),
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
                    AikuText(
                        text = groupName,
                        style = AiKUTheme.typography.body1SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(4.dp))
                    AikuText(
                        text = stringResource(
                            R.string.feature_home_group_card_recent_schedule,
                            formattedTime,
                        ),
                        style = AiKUTheme.typography.body2,
                    )
                }
                Spacer(Modifier.weight(1f))
                AvatarWithBadge(
                    memberSize = memberSize,
                    contentDescription = stringResource(UiR.string.char_head_no_hair_description)
                )
            }
        }
    }
}

@Composable
private fun AvatarWithBadge(
    memberSize: Int,
    contentDescription: String?,
) {
    val badgeLabel = if (memberSize >= 100) {
        stringResource(R.string.feature_home_group_card_badge_member_over_limit)
    } else {
        "$memberSize"
    }
    Box(
        modifier = Modifier.padding(end = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .background(
                    color = AiKUTheme.colors.gray02,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(UiR.drawable.img_char_head_nohair),
                contentDescription = contentDescription,
                modifier = Modifier.size(32.dp)
            )
        }
        AikuText(
            text = badgeLabel,
            style = AiKUTheme.typography.caption1Medium,
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
        JoinedGroupCard(
            groupName = "그룹 1",
            time = null,
            onClick = {},
            memberSize = 18,
            modifier = Modifier.padding(20.dp)
        )
    }
}