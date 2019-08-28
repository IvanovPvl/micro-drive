package io.microdrive.testing

import io.restassured.RestAssured.get
import org.junit.jupiter.api.Test as test

class Application {
    @test fun getCurrentAccountWithoutTokenShouldReturnUnauthorized() {
        get("http://localhost:8080/accounts/current")
                .then()
                .assertThat()
                .statusCode(401)
    }
}