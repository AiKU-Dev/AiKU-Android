package com.hyunjung.aiku.core.model

import com.hyunjung.aiku.core.model.profile.AvatarBackground
import com.hyunjung.aiku.core.model.profile.AvatarCharacter
import com.hyunjung.aiku.core.model.profile.UserProfile

data class SignUpForm(
    val idToken: String,
    val socialType: SocialType,
    val email: String,
    val nickname: String = "",
    val userProfile: UserProfile = UserProfile.Avatar(
        avatarCharacter = AvatarCharacter.BOY,
        avatarBackground = AvatarBackground.GREEN,
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