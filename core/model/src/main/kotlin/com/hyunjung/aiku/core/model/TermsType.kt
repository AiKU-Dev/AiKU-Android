package com.hyunjung.aiku.core.model

enum class TermsType(
    val title: String,
    val isRequired: Boolean
) {
    SERVICE(
        title = "서비스 이용약관",
        isRequired = true
    ),
    PRIVACY(
        title = "개인정보 보호 동의",
        isRequired = true
    ),
    LOCATION(
        title = "위치기반 서비스 이용약관",
        isRequired = true
    ),
    MARKETING(
        title = "마케팅 수신 동의",
        isRequired = false
    )
}