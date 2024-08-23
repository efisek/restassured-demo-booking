package base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import tests.TokenManager;

public class BaseSetup {
    protected String token;
    @BeforeClass
    public void setup () {
        RestAssured.baseURI="https://restful-booker.herokuapp.com";
        token= TokenManager.getToken();
    }


}
