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
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hyunjung.aiku.core.designsystem.component.AikuButton
import com.hyunjung.aiku.core.designsystem.component.AikuButtonDefaults
import com.hyunjung.aiku.core.designsystem.component.AikuClickableSurface
import com.hyunjung.aiku.core.designsystem.component.AikuHorizontalDivider
import com.hyunjung.aiku.core.designsystem.component.AikuIcon
import com.hyunjung.aiku.core.designsystem.component.AikuLoadingWheel
import com.hyunjung.aiku.core.designsystem.component.AikuText
import com.hyunjung.aiku.core.designsystem.icon.AikuIcons
import com.hyunjung.aiku.core.designsystem.theme.AiKUTheme
import com.hyunjung.aiku.core.model.UserData
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.ProfileImage
import com.hyunjung.aiku.core.model.profile.ProfileBackgroundColor
import com.hyunjung.aiku.core.navigation.AikuRoute
import com.hyunjung.aiku.core.ui.component.common.AikuNavigationBar
import com.hyunjung.aiku.core.ui.extension.backgroundColor
import com.hyunjung.aiku.core.ui.extension.painter
import com.hyunjung.aiku.core.ui.preview.AikuPreviewTheme
import java.text.NumberFormat

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    onClickNotification: () -> Unit,
    onClickAccount: () -> Unit,
    onClickNotificationCheck: () -> Unit,
    onClickPermissionSetting: () -> Unit,
    onClickHelp: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MyPageScreen(
        uiState = uiState,
        onMenuItemClick = { menuAction ->
            when (menuAction) {
                MenuAction.NOTIFICATION -> onClickNotification()
                MenuAction.ACCOUNT -> onClickAccount()
                MenuAction.NOTIFICATION_CHECK -> onClickNotificationCheck()
                MenuAction.PERMISSION_SETTING -> onClickPermissionSetting()
                MenuAction.HELP -> onClickHelp()
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun MyPageScreen(
    uiState: MyPageUiState,
    onMenuItemClick: (MenuAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is MyPageUiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AikuText(
                    text = uiState.message ?: "Unknown Error"
                )
            }
        }

        is MyPageUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AikuLoadingWheel(
                    modifier = Modifier.size(80.dp),
                    contentDescription = null,
                )
            }
        }

        is MyPageUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = AiKUTheme.colors.gray01)
            ) {
                MyPageHeader(
                    userData = uiState.userData,
                )
                Spacer(Modifier.height(36.dp))
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .shadow(
                            elevation = 10.dp,
                            ambientColor = Color.Black.copy(alpha = 0.08f),
                            spotColor = AiKUTheme.colors.gray03
                        )
                        .background(
                            color = AiKUTheme.colors.white,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    MenuAction.entries.forEachIndexed { index, menuAction ->
                        item {
                            MenuItemRow(
                                menuAction = menuAction,
                                onClick = { onMenuItemClick(menuAction) },
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
                Spacer(Modifier.weight(1f))
                AikuNavigationBar(AikuRoute.MyPageRoute)
            }
        }
    }
}

@Composable
private fun MyPageHeader(
    modifier: Modifier = Modifier,
    userData: UserData,
) {
    Column(
        modifier = modifier
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
        UserProfileInfo(
            nickname = userData.nickname,
            email = userData.email,
            profileImage = userData.profileImage,
            modifier = Modifier
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
                    text = NumberFormat.getNumberInstance().format(userData.point),
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
}

@Composable
private fun UserProfileInfo(
    nickname: String,
    email: String,
    profileImage: ProfileImage,
    modifier: Modifier = Modifier
) {
    Row {
        Box(modifier = modifier) {
            AikuClickableSurface(
                modifier = Modifier.size(100.dp),
                onClick = {},
                color = profileImage.backgroundColor(),
                shape = CircleShape,
                contentPadding = if (profileImage is ProfileImage.Avatar) PaddingValues(12.dp) else PaddingValues(
                    0.dp
                )
            ) {
                Image(
                    painter = profileImage.painter(),
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
                text = nickname,
                style = AiKUTheme.typography.subtitle2G
            )
            AikuText(
                text = email,
                style = AiKUTheme.typography.body2,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
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
    val userData = UserData(
        id = 1L,
        email = "email@example.com",
        nickname = "아이쿠",
        kakaoId = "email@kakao.com",
        profileImage = ProfileImage.Avatar(
            type = AvatarType.BOY,
            backgroundColor = ProfileBackgroundColor.GREEN
        ),
        point = 10000,
    )

    AikuPreviewTheme {
        MyPageScreen(
            uiState = MyPageUiState.Success(userData),
            onMenuItemClick = {}
        )
    }
}