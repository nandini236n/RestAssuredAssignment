package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTest extends BaseTest {
   @Test(enabled = false)
    public void getBookingTest() {

        //create booking
       JSONObject requestBody = createBooking();
       Response responseCreate = RestAssured.given().spec(spec).
               contentType(ContentType.JSON).body(requestBody.toString())
               .post("/booking");
        responseCreate.print();

        String enteredFirstname = responseCreate.jsonPath().get("booking.firstname");
        String enteredLastname = responseCreate.jsonPath().get("booking.lastname");
        int enteredTotalPrice = responseCreate.jsonPath().getInt("booking.totalprice");
        boolean enteredDepositPaid = responseCreate.jsonPath().getBoolean("booking.depositpaid");
        String enteredCheckIn = responseCreate.jsonPath().getString("booking.bookingdates.checkin");
        String enteredCheckOut = responseCreate.jsonPath().getString("booking.bookingdates.checkout");
        String enteredAdditionalNeeds = responseCreate.jsonPath().getString("booking.additionalneeds");

        //Set path parameter
        spec.pathParams("bookingId", responseCreate.jsonPath().getInt("bookingid"));

        //get response with booking
        Response response = RestAssured.given(spec).get("/booking/{bookingId}");
        response.print();


        //validating the status code
        Assert.assertEquals("Expected status code is 200", 200, response.getStatusCode());

        //Verify All fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(enteredFirstname, actualFirstName, "Firstname is not as per expected ");

        String actualLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(enteredLastname, actualLastName, "Lastname is not as expected");

        int actualTotalPrice = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(enteredTotalPrice, actualTotalPrice, "Totalprice is not as expected");

        boolean actualDepositPaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertEquals(enteredDepositPaid, actualDepositPaid, "The expected value of deposit is true ");

        String actualCheckIn = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(enteredCheckIn, actualCheckIn, "checkin dates are not s expected ");

        String actualCheckOut = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(enteredCheckOut, actualCheckOut, "the checkout dates are not as per expected");

        String actualAdditionalNeeds = response.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(enteredAdditionalNeeds, actualAdditionalNeeds, "The additional as not as per expected ");

        softAssert.assertAll();


    }

    @Test(enabled = false)
    public void getBookingXMLTest() {

        //create booking
        JSONObject requestBody = createBooking();
        Response responseCreate = RestAssured.given().spec(spec).
                contentType(ContentType.JSON).body(requestBody.toString())
                .post("/booking");
        //responseCreate.print();

        String enteredFirstname = responseCreate.jsonPath().getString("booking.firstname");
        String enteredLastname = responseCreate.jsonPath().getString("booking.lastname");
        int enteredTotalPrice = responseCreate.jsonPath().getInt("booking.totalprice");
        boolean enteredDepositPaid = responseCreate.jsonPath().getBoolean("booking.depositpaid");
        String enteredCheckIn = responseCreate.jsonPath().getString("booking.bookingdates.checkin");
        String enteredCheckOut = responseCreate.jsonPath().getString("booking.bookingdates.checkout");
        String enteredAdditionalNeeds = responseCreate.jsonPath().getString("booking.additionalneeds");

        //Set path parameter
        spec.pathParams("bookingId", responseCreate.jsonPath().getInt("bookingid"));

        //get response with booking
        Header xml= new Header("Accept", "application/xml");
        spec.header(xml);
        Response response = RestAssured.given(spec).get("/booking/{bookingId}");
        response.print();


        //validating the status code

        Assert.assertEquals("Expected status code is 200", 200, response.getStatusCode());

        //Verify All fields
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.xmlPath().getString("booking.firstname");
        softAssert.assertEquals(enteredFirstname, actualFirstName, "Firstname is not as per expected ");

        String actualLastName = response.xmlPath().getString("booking.lastname");
        softAssert.assertEquals(enteredLastname, actualLastName, "Lastname is not as expected");

        int actualTotalPrice = response.xmlPath().getInt("booking.totalprice");
        softAssert.assertEquals(enteredTotalPrice, actualTotalPrice, "Totalprice is not as expected");

        boolean actualDepositPaid = response.xmlPath().getBoolean("booking.depositpaid");
        softAssert.assertEquals(enteredDepositPaid, actualDepositPaid, "The expected value of deposit is true ");

        String actualCheckIn = response.xmlPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(enteredCheckIn, actualCheckIn, "checkin dates are not s expected ");

        String actualCheckOut = response.xmlPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(enteredCheckOut, actualCheckOut, "the checkout dates are not as per expected");

        String actualAdditionalNeeds = response.xmlPath().getString("booking.additionalneeds");
        softAssert.assertEquals(enteredAdditionalNeeds, actualAdditionalNeeds, "The additional as not as per expected ");

        softAssert.assertAll();


    }
}
