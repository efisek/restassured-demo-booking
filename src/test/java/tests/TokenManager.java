package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.RequestAuth;
import model.ResponseAuth;

import static io.restassured.RestAssured.given;

public class TokenManager {

    public static String getToken() {
        RequestAuth requestAuth = new RequestAuth("admin", "password123");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestAuth)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .response();

        ResponseAuth responseAuth = response.as(ResponseAuth.class);
        return responseAuth.getToken();
    }

}
