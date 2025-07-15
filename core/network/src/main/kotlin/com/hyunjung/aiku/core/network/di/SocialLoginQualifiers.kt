package com.hyunjung.aiku.core.network.di

import com.hyunjung.aiku.core.model.SocialType
import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class SocialLogin(val type: SocialType)