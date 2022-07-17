package com.kits.base;
import static io.restassured.RestAssured.baseURI;


public class BaseTest {
    public BaseTest() {
        baseURI = "http://localhost:8080/";
    }
}
