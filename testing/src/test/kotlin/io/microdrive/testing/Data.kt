package io.microdrive.testing

import com.google.gson.annotations.SerializedName

data class CreateAccountRequest(
        val username: String,
        val firstName: String,
        val lastName: String,
        val password: String,
        val role: String
)

data class Account(
        val id: String,
        val username: String,
        val firstName: String,
        val lastName: String,
        val role: String,
        val enabled: Boolean,
        val authorities: List<Map<String, String>>
)

data class TokenResponse(
        @SerializedName("access_token") val accessToken: String,
        @SerializedName("token_type") val tokenType: String,
        @SerializedName("refresh_token") val refreshToken: String,
        @SerializedName("expires_in") val expiresIn: Int,
        val scope: String,
        val jti: String
)
