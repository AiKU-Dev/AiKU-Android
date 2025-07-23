package com.hyunjung.aiku.core.auth.social.di

import com.hyunjung.aiku.core.model.auth.SocialType
import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class SocialAuth(val type: SocialType)