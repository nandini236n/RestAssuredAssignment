package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class UpdateBookingTest extends BaseTest {
    @Test
    public boolean updateBookingTest(int bookingid) {
        // create booking
        JSONObject requestBody = createBooking();

        //Get booking id
        System.out.println(bookingid);

        //create Json body
        JSONObject body = new JSONObject();
        body.put("firstname", "Nandu");
        body.put("lastname", "N.Raj");
        body.put("totalprice", 12300);
        body.put("depositpaid", true);

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2023-02-04");
        bookingDates.put("checkout", "2023-02-06");
        body.put("bookingDates", bookingDates);
        body.put("additionalneeds", "Private taxi");

        //update booking
        Response responseUpdate = RestAssured.given().spec(spec).auth().preemptive().basic("admin", "password123").
                contentType(ContentType.JSON).body(body.toString())
                .put("/booking/"+ bookingid);

        System.out.println(responseUpdate.statusCode());
        responseUpdate.print();
        System.out.println(responseUpdate);


        //validate
        String responseBody = responseUpdate.then().extract().body().asString();
        System.out.println(responseBody);

        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = responseUpdate.then().extract().body().asString();
        softAssert.assertEquals(actualFirstName, "Nandu", "firstname in response is not expected");

        String actualLastName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "N.Raj", "lastname in response is not expected");

        int price = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 150, "totalprice in response is not expected");

        boolean depositpaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2020-03-25", "checkin in response is not expected");

        String actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2020-03-27", "checkout in response is not expected");

        String actualAdditionalneeds = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Baby crib", "additionalneeds in response is not expected");

        softAssert.assertAll();


     return false;
    }
}
