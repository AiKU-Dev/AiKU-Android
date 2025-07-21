package com.hyunjung.aiku.core.network.extension

import com.hyunjung.aiku.core.model.MemberProfile
import com.hyunjung.aiku.core.model.SignUpForm
import com.hyunjung.aiku.core.model.TermsType
import io.ktor.client.request.forms.FormBuilder
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

internal fun FormBuilder.appendBaseFields(form: SignUpForm) {
    append("nickname", form.nickname)
    append("provider", form.socialType.name)
    append("email", form.email)
    append("idToken", form.idToken)
}

internal fun FormBuilder.appendProfileFields(profile: MemberProfile) {
    when (profile) {
        is MemberProfile.GalleryImage -> {
            append("memberProfile.profileType", "IMG")
            append(
                "memberProfile.profileImg",
                profile.file.readBytes(),
                Headers.build {
                    append(HttpHeaders.ContentType, "image/jpeg")
                    append(HttpHeaders.ContentDisposition, "filename=\"profile.jpg\"")
                }
            )
        }

        is MemberProfile.Character -> {
            append("memberProfile.profileType", "CHAR")
            append("memberProfile.profileCharacter", profile.profileCharacter.code)
            append("memberProfile.profileBackground", profile.profileBackground.name)
        }

        is MemberProfile.RemoteImage -> {
            append("memberProfile.profileType", "IMG")
        }
    }
}

internal fun FormBuilder.appendAgreementFields(terms: List<TermsType>) {
    append("isServicePolicyAgreed", TermsType.SERVICE in terms)
    append("isPersonalInformationPolicyAgreed", TermsType.PRIVACY in terms)
    append("isLocationPolicyAgreed", TermsType.LOCATION in terms)
    append("isMarketingPolicyAgreed", TermsType.MARKETING in terms)
}