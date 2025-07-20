package com.hyunjung.aiku.core.model

data class SignUpForm(
    val idToken: String,
    val socialType: SocialType,
    val email: String,
    val nickname: String = "",
    val memberProfile: MemberProfile = MemberProfile.Character(
        profileCharacter = ProfileCharacter.BOY,
        profileBackground = ProfileBackground.GREEN,
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