package io.microdrive.testing

import io.restassured.RestAssured.get
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test as test

class Accounts {
    @test fun getCurrentAccountWithoutTokenShouldReturnUnauthorized() {
        get("http://localhost:8080/accounts/current")
                .then()
                .assertThat()
                .statusCode(401)
    }

    @test fun createdWithExistingUsernameShouldReturnBadRequest() {
        val accountData = createAccountData("user")
        createAccount(accountData)

        given().contentType("application/json")
                .body(accountData)
                .`when`()
                .post("http://localhost:8080/accounts/create")
                .then()
                .assertThat()
                .statusCode(400)
    }

    @test fun currentShouldReturnAuthenticatedUser() {
        val (user, password) = createUser()
        val token = login(user.username, password)

        given().auth()
                .oauth2(token)
                .`when`()
                .get("http://localhost:8080/accounts/current")
                .then()
                .assertThat()
                .statusCode(200)
    }

}