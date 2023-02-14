package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetBookingIdsTest  extends BaseTest{

    @Test
    public void getBookingIdsWithoutFilterTest(){

        //get response with booking ids
        Response response = RestAssured.given().spec(spec).get("/booking");
        response.print();


        //verify status code 200
        Assert.assertEquals("Expected status code is 200",200, response.getStatusCode());

        //verify at least 1 booking id in response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse("List of booking ids is empty which should not to ", bookingIds.isEmpty());



    }
    @Test
    public void getBookingIdsWithFilterTest(){
        //add query parameter to spec
        spec.queryParam("firstname", "Susan");
       // spec.queryParam("lastname", "brown");

        //Get response
        Response response = RestAssured.given().spec(spec).get("/booking");
        response.print();


        //verify status code 200
        Assert.assertEquals("Expected status code is 200",200, response.getStatusCode());

        //verify at least 1 booking id in response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse("List of booking ids is empty which should not to ", bookingIds.isEmpty());
    }
}
