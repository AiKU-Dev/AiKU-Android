package com.hyunjung.aiku.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuButtonDefaults
import com.hyunjung.aiku.core.designsystem.component.AikuClickableSurface
import com.hyunjung.aiku.core.designsystem.component.AikuHorizontalDivider
import com.hyunjung.aiku.core.designsystem.component.AikuIcon
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.ProfileBackgroundColor
import com.hyunjung.aiku.core.model.profile.UserProfileImage
import com.hyunjung.aiku.core.ui.extension.backgroundColor
import com.hyunjung.aiku.core.ui.extension.painter
import java.text.NumberFormat

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = AiKUTheme.colors.gray01)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    AiKUTheme.colors.green02,
                    shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
                )
                .padding(horizontal = 20.dp)
        ) {
            AikuText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.profile_my_page_title),
                style = AiKUTheme.typography.subtitle3G
            )
            Spacer(
                modifier = Modifier.height(34.dp)
            )
            ProfileImageRow(
                userProfileImage = UserProfileImage.Avatar(
                    AvatarType.BOY,
                    ProfileBackgroundColor.GREEN
                ), modifier = Modifier
            )
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = AiKUTheme.colors.white, shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                AikuText(
                    text = stringResource(R.string.profile_my_page_my_point),
                    style = AiKUTheme.typography.body2Medium,
                    color = AiKUTheme.colors.gray03
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AikuIcon(
                        painter = AikuIcons.Aku,
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    AikuText(
                        text = NumberFormat.getNumberInstance().format(10000),
                        style = AiKUTheme.typography.subtitle3SemiBold
                    )
                }
            }
            AikuButton(
                onClick = {},
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 24.dp),
                colors = AikuButtonDefaults.buttonColors(
                    containerColor = AiKUTheme.colors.cobaltBlue,
                    contentColor = AiKUTheme.colors.white
                ),
                contentPadding = PaddingValues(vertical = 14.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                AikuText(
                    text = stringResource(R.string.profile_my_page_shop_button),
                    style = AiKUTheme.typography.subtitle3SemiBold
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(top = 44.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(12.dp),
                    ambientColor = Color.Black.copy(alpha = 0.08f),
                    spotColor = AiKUTheme.colors.gray03
                )
        ) {
            item {
                ProfileMenuSection { menuAction ->
                    when (menuAction) {
                        MenuAction.NOTIFICATION -> {
                            // 알림 화면으로 이동
                        }

                        MenuAction.ACCOUNT -> {
                            // 계정 화면으로 이동
                        }

                        MenuAction.NOTIFICATION_CHECK -> {
                            // 알림 확인 화면으로 이동
                        }

                        MenuAction.PERMISSION_SETTING -> {
                            // 권한 설정 화면으로 이동
                        }

                        MenuAction.HELP -> {
                            // 도움말 화면으로 이동
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileImageRow(userProfileImage: UserProfileImage, modifier: Modifier = Modifier) {
    // TODO: 프로필 이미지 받아오기
    Row {
        Box(modifier = modifier) {
            AikuClickableSurface(
                modifier = Modifier.size(100.dp),
                onClick = {},
                color = userProfileImage.backgroundColor(),
                shape = CircleShape,
                contentPadding = if (userProfileImage is UserProfileImage.Avatar) PaddingValues(12.dp) else PaddingValues(
                    0.dp
                )
            ) {
                Image(
                    painter = userProfileImage.painter(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

            }
            Image(
                painter = AikuIcons.Edit,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .background(color = AiKUTheme.colors.white, shape = CircleShape)
                    .border(
                        width = 1.dp,
                        color = AiKUTheme.colors.gray02,
                        shape = CircleShape
                    )
                    .padding(4.dp)
                    .clip(CircleShape)
                    .align(Alignment.BottomEnd),
                alignment = Alignment.BottomEnd
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 20.dp)
        ) {
            AikuText(
                text = "사용자1",
                style = AiKUTheme.typography.subtitle2G
            )
            AikuText(
                text = "aiku@naver.com",
                style = AiKUTheme.typography.body2,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun ProfileMenuSection(
    modifier: Modifier = Modifier,
    onMenuItemClick: (MenuAction) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = AiKUTheme.colors.white, shape = RoundedCornerShape(10.dp)),
    ) {
        MenuAction.entries.forEachIndexed { index, menuAction ->
            MenuItemRow(
                menuAction = menuAction,
                onClick = { onMenuItemClick(menuAction) }
            )

            if (index < MenuAction.entries.size - 1) {
                AikuHorizontalDivider(
                    thickness = 1.dp,
                    color = AiKUTheme.colors.gray02
                )
            }
        }
    }
}

@Composable
private fun MenuItemRow(
    menuAction: MenuAction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AikuText(
            text = menuAction.title,
            style = AiKUTheme.typography.body2,
            color = Color.Black,
            fontWeight = FontWeight.Normal
        )
        AikuIcon(
            painter = AikuIcons.ArrowRight,
            contentDescription = menuAction.title,
            tint = Color.Unspecified
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    AiKUTheme {
        MyPageScreen()
    }
}

@Preview
@Composable
private fun ProfileImageRowPreview() {
    AiKUTheme {
        ProfileImageRow(
            userProfileImage = UserProfileImage.Avatar(
                type = AvatarType.BOY,
                backgroundColor = ProfileBackgroundColor.GREEN
            )
        )
    }
}