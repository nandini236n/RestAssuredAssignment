package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class DeleteBookingTest extends BaseTest {
    @Test
    public void deleteBookingTest() {

        //create booking
        JSONObject requestbody = createBooking();
        Response responseCreate = RestAssured.given().spec(spec).
                contentType(ContentType.JSON).body(requestbody.toString())
                .post("/booking");
        responseCreate.print();

        //Get booking id
        int bookingid = responseCreate.jsonPath().getInt("bookingid");
        System.out.println(bookingid);

        //Delete booking
        Response responseDelete = RestAssured.given().spec(spec).auth().preemptive().basic("admin", "password123").
                delete("/booking/" + bookingid);
        responseDelete.print();

        if (responseDelete.getStatusCode() != 201) {

            System.out.println("The expected status code is 201");
        }
        Response response = RestAssured.given().spec(spec).get("/booking/" + bookingid);
        if (response.getStatusCode() == 404) {
           // Assert.assertEquals(response.getBody().asString(),"Not Found","Body should be not be found ");
            System.out.println("Delete validation success");
        }

    }
}
