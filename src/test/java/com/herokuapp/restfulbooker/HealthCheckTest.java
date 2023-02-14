package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HealthCheckTest extends BaseTest {

    @Test
    public void healthCheckTest() {

        given().
                spec(spec).
                when().
                get("/ping").
                then().
                assertThat().
                statusCode(201);
    }

    @Test
    public void headersAndCookiesTest() {

        //adding header and cookie
        Header header1 = new Header("header1", "header1");
        spec.header(header1);

        Cookie cookie1 = new Cookie.Builder("cookie1" , "some cookie value").build();
        spec.cookie(cookie1);

        Response response = RestAssured.given(spec).
                cookies("Test cookie name ", "Test cookie value ").
                headers("Test header name ", "Test header value").
                log().all().
                get("/ping");

        //Get Headers
        Headers headers = response.getHeaders();
        System.out.println("Headers: " + headers);

        Header serverHeader1 =  headers.get("Server");
        System.out.println(serverHeader1.getName() + ": " + serverHeader1.getValue());

        String serverHeader2= response.getHeader("Server");
        System.out.println("Server:  "+ serverHeader2);

        //get cookies
        Cookies cookies = response.getDetailedCookies();
        System.out.println("Cookies: " + cookies);

    }

}
