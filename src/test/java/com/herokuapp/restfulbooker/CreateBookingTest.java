package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class CreateBookingTest extends BaseTest {


    @Test
    public void createBookingTest() {
        boolean validationFlag=true;

        JSONObject requestBody  = createBooking();


        Response responseCreate = RestAssured.given().spec(spec).
                contentType(ContentType.JSON).body(requestBody.toString())
                .post("/booking");
        responseCreate.print();
        Integer bookingid = responseCreate.jsonPath().getInt("bookingid");


        //Verify all the fields
        if(responseCreate.getStatusCode()==200) {
            SoftAssert softAssert = new SoftAssert();
            String actualFirstName = responseCreate.jsonPath().getString("booking.firstname");
           // softAssert.assertEquals(actualFirstName, "Dmitry", "firstname in response is not expected");
            if(!(actualFirstName.equals(requestBody.getString("firstname")))){
               //return Arrays.asList(bookingid,false);
                validationFlag=false;
                System.out.println("firstname mismatch");
            }


            String actualLastName = responseCreate.jsonPath().getString("booking.lastname");
            if(!(actualLastName.equals(requestBody.getString("lastname")))){
                validationFlag=false;
                System.out.println("lastname mismatch");
            }

            int price = responseCreate.jsonPath().getInt("booking.totalprice");
            if(price!=requestBody.getInt("totalprice")){
                validationFlag=false;
                System.out.println("totalprice mismatch");
            }

            boolean depositpaid = responseCreate.jsonPath().getBoolean("booking.depositpaid");
            if(depositpaid!=requestBody.getBoolean("depositpaid")){
                validationFlag=false;
                System.out.println("depositpaid mismatch");
            }


            String actualCheckin = responseCreate.jsonPath().getString("booking.bookingdates.checkin");

            JSONObject bookingDates = requestBody.getJSONObject("bookingdates");
            if(!(actualCheckin.equals(bookingDates.get("checkin")))) {
                validationFlag=false;
                System.out.println("checkin mismatch");
            }

            String actualCheckOut = responseCreate.jsonPath().getString("booking.bookingdates.checkout");
            if(!(actualCheckOut.equals(bookingDates.get("checkout")))) {
                // return Arrays.asList(bookingid,false);
                validationFlag=false;
                System.out.println("checkout mismatch");
            }
            //softAssert.assertEquals(actualCheckout, "2020-03-27", "checkout in response is not expected");

            String actualAdditionalneeds = responseCreate.jsonPath().getString("booking.additionalneeds");
            //softAssert.assertEquals(actualAdditionalneeds, "Baby crib", "additionalneeds in response is not expected");
            if(!(actualAdditionalneeds.equals(requestBody.getString("additionalneeds")))) {
                validationFlag=false;
                System.out.println("additionalneeds mismatch");
            }
            if(validationFlag) {
                System.out.println("validation success");
                System.out.println(responseCreate.jsonPath().getInt("bookingid"));
            }

           // softAssert.assertAll();
        }else{
            System.out.println("Validation Failure ");
        }

    }

    @Test(enabled = false)
    public void createBookingWithPOJOTest() {

        // Create  requestBody with POJOs
        Bookingdates bookingdates = new Bookingdates("2020-03-25", "2020-03-27");
        Booking booking = new Booking("Nandini" , "Sukanya", 12890, true, bookingdates, "Private taxi");

        // Get response
        Response response = RestAssured.given().spec(spec).
                contentType(ContentType.JSON).body(booking)
                .post("/booking");
        response.print();

        Bookingid bookingId = response.as(Bookingid.class);

        //Verify all the fields
        SoftAssert softAssert = new SoftAssert();

        //verifying status code
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code is 200");

        System.out.println("booking request requestBody : "+ booking.toString());
        System.out.println("booking response  requestBody : "+ bookingId.getBooKing().toString());

        //verifying all fields
        Assert.assertEquals(bookingId.getBooKing().toString(),booking.toString());



        softAssert.assertAll();

    }
}
