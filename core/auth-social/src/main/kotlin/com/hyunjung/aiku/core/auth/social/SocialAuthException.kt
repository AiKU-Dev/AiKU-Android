package com.hyunjung.aiku.core.auth.social

internal sealed class SocialAuthException(
    override val message: String,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    class Cancelled(cause: Throwable? = null) : SocialAuthException("Sign in Cancelled", cause)
    class MissingIdToken(cause: Throwable? = null) :
        SocialAuthException("ID Token not provided", cause)

    class UserInfoFetchFailed(cause: Throwable? = null) :
        SocialAuthException("Failed to fetch user info", cause)

    class ProviderError(cause: Throwable? = null) :
        SocialAuthException("Social provider error", cause)

    class Unknown(cause: Throwable? = null) : SocialAuthException("Unknown auth error", cause)
}