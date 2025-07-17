package com.hyunjung.aiku.core.terms

import androidx.annotation.RawRes

enum class TermsType(
    val title: String,
    @RawRes val resId: Int,
    val isRequired: Boolean
) {
    SERVICE(
        title = "서비스 이용약관",
        resId = R.raw.terms_service_ko,
        isRequired = true
    ),
    PRIVACY(
        title = "개인정보 보호 동의",
        resId = R.raw.terms_privacy_ko,
        isRequired = true
    ),
    LOCATION(
        title = "위치기반 서비스 이용약관",
        resId = R.raw.terms_location_ko,
        isRequired = true
    ),
    MARKETING(
        title = "마케팅 수신 동의",
        resId = R.raw.terms_marketing_ko,
        isRequired = false
    )
}