package com.jpm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static io.restassured.config.RestAssuredConfig.config;

public class ApiHelper {
    private static Gson gson;

    protected static Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gson(gsonBuilder);
        return gson;
    }

    private static Gson gson(GsonBuilder gsonBuilder) {
        gson = gsonBuilder.create();
        return gson;
    }

    protected static RequestSpecification givenConfig() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config = config().redirect(redirectConfig().followRedirects(true));
        RestAssured.config = config().sslConfig(new SSLConfig().allowAllHostnames());

        return given()
                .relaxedHTTPSValidation()
                .header("Accept-Language", "en")
                .header("Content-Type", "application/json")
                .urlEncodingEnabled(false)
                .baseUri("https://jsonplaceholder.typicode.com");
    }
}
