package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;


public class BaseTest {
    protected   RequestSpecification spec;
    @BeforeMethod
    public void setUp(){

            spec = new RequestSpecBuilder().
                    setBaseUri("https://restful-booker.herokuapp.com").
                    build();


    }

    protected JSONObject createBooking() {

        // Create JSON body
        Scanner sc = new Scanner(System.in);
        JSONObject body = new JSONObject();
       // System.out.println("enter the firstname");
        body.put("firstname", "Nandini");
       // System.out.println("enter the lastname");
        body.put("lastname", "Sukanya");
       // System.out.println("Enter the totalprice");
        body.put("totalprice",12300 );
       // System.out.println("Enter the depositpaid boolean values");
        body.put("depositpaid", true);

        JSONObject bookingdates = new JSONObject();
        //System.out.println("Enter the checkin date");
        bookingdates.put("checkin", "2023-08-23");
        //System.out.println("Enter the checkout date ");
        bookingdates.put("checkout", "2023-09-28");
        body.put("bookingdates", bookingdates);
       // System.out.println("Enter the additional needs for the booking ");
        body.put("additionalneeds", "Private taxi");
       // System.out.println(body);
        return body;


    }

    @Test
    public static void main(String[] args) {





     //   UpdateBookingTest obj2 = new UpdateBookingTest();
     //   boolean result2 = obj2.updateBookingTest(bookingid);

        //PartialUpdateBookingTest obj3 = new PartialUpdateBookingTest();
  //      boolean result3 = obj3.partialUpdateTest();



    }


}