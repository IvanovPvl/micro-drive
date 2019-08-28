package io.microdrive.testing

import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import io.restassured.RestAssured.*
import org.junit.jupiter.api.Test as test

fun createAccount(account: Account) {
    val gson = Gson()
    "http://localhost:8080/accounts/create".httpPost()
            .jsonBody(gson.toJson(account))
            .response()
}

class Application {
    @test fun getCurrentAccountWithoutTokenShouldReturnUnauthorized() {
        get("http://localhost:8080/accounts/current")
                .then()
                .assertThat()
                .statusCode(401)
    }

    @test fun createdWithExistingUsernameShouldReturnBadRequest() {
        val user = Account(
                "paul",
                "Paul",
                "Stanley",
                "12345",
                "user"
        )

        createAccount(user)

        given().contentType("application/json")
                .body(user)
                .`when`()
                .post("http://localhost:8080/accounts/create")
                .then()
                .assertThat()
                .statusCode(400)

    }
}