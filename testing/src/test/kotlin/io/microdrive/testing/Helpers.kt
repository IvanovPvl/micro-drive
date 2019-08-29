package io.microdrive.testing

import com.github.javafaker.Faker
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

fun createAccountData(role: String): CreateAccountRequest {
    with(Faker()) {
        return CreateAccountRequest(
                name().username(),
                internet().password(),
                name().firstName(),
                name().lastName(),
                role
        )
    }
}

fun createUser() = createAccount(createAccountData("user"))

fun createDriver() = createAccount(createAccountData("driver"))

fun createAccount(account: CreateAccountRequest): Pair<Account, String> {
    val gson = Gson()
    val (_, _, result) = "http://localhost:8080/accounts/create".httpPost()
            .jsonBody(gson.toJson(account))
            .response()

    val reader = InputStreamReader(ByteArrayInputStream(result.get()))
    return Pair(gson.fromJson(reader, Account::class.java), account.password)
}

fun login(username: String, password: String): String {
    val params = listOf(
            "scope"      to "ui",
            "username"   to username,
            "password"   to password,
            "grant_type" to "password"
    )

    val (_, _, result) = "http://localhost:8080/accounts/oauth/token".httpPost(params)
            .authentication()
            .basic("mobile", "secret")
            .response()

    val reader = InputStreamReader(ByteArrayInputStream(result.get()))
    return Gson().fromJson(reader, TokenResponse::class.java).accessToken
}
