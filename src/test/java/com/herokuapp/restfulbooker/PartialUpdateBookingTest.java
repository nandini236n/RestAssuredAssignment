package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.Serializable;
import java.util.List;

import static java.lang.System.in;

public class PartialUpdateBookingTest extends BaseTest {

    @Test
    public boolean partialUpdateTest(){
        JSONObject requestBody  = createBooking();
        Response responseCreate = RestAssured.given().spec(spec).
                contentType(ContentType.JSON).body(requestBody.toString())
                .post("/booking");

        //responseCreate.print();

        //get the bookingid
        int bookingid = responseCreate.jsonPath().getInt("bookingid");
        System.out.println(bookingid);

        //create json body
        JSONObject body = new JSONObject();
        body.put("firstname", "sirisha Raj");

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2023-04-23");
        bookingDates.put("checkout", "2023004-23");
        body.put("bookingdates", bookingDates);
        System.out.println(body.getClass().getName());

        //partialUpdate booking
        Response responsePartialUpdate = RestAssured.given().spec(spec).auth().preemptive().basic("admin","password123").
                contentType(ContentType.JSON).body(body.toString()).patch("/booking/" + bookingid);
        responsePartialUpdate.print();


        //verify all the fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = responsePartialUpdate.jsonPath().getString("firstname");

        softAssert.assertEquals(actualFirstName, body.get("firstname"), "firstname in response is not expected");

        String actualLastName = responsePartialUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Shyshkin", "lastname in response is not expected");

        int price = responsePartialUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 150, "totalprice in response is not expected");

        boolean depositpaid = responsePartialUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = responsePartialUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2020-04-25", "checkin in response is not expected");

        String actualCheckout = responsePartialUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2020-04-27", "checkout in response is not expected");

        String actualAdditionalneeds = responsePartialUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Baby crib", "additionalneeds in response is not expected");

        softAssert.assertAll();

       return true;

    }
}
