package com.hyunjung.aiku.core.model.auth

import com.hyunjung.aiku.core.model.profile.ProfileBackgroundColor
import com.hyunjung.aiku.core.model.profile.AvatarType
import com.hyunjung.aiku.core.model.profile.UserProfileImage

data class SignUpForm(
    val idToken: String,
    val socialType: SocialType,
    val email: String,
    val nickname: String = "",
    val userProfileImage: UserProfileImage = UserProfileImage.Avatar(
        type = AvatarType.BOY,
        backgroundColor = ProfileBackgroundColor.GREEN,
    ),
    val isNicknameDuplicated: Boolean = false,
    val recommenderNickname: String = "",
    val agreedTerms: List<TermsType> = emptyList(),
) {
    val isNicknameValid: Boolean
        get() = nickname.matches(Regex("^[가-힣a-zA-Z ]{1,6}$"))
    val isSignUpEnabled: Boolean
        get() = isNicknameValid &&
                isNicknameDuplicated &&
                TermsType.entries
                    .filter { it.isRequired }
                    .all { it in agreedTerms }
}