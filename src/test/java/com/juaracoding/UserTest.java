package com.juaracoding;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserTest {

    String baseUrl = "https://reqres.in/api";

    @Test
    public void testlistUsers() {
        Response response = RestAssured.get(baseUrl+("/user?page=2"));
        System.out.println(response.getBody().asString());
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        System.out.println(response.getHeader("content-type"));
        System.out.println(response.getTime());
        int actual = response.getStatusCode();
        Assert.assertEquals(actual, 200);
        String actualBody = response.getBody().asString();
        String email = response.getBody().jsonPath().getString("data.email[0]");
        System.out.println(email);
        Assert.assertTrue(actualBody.contains("\"id\":7"));
    }

    @Test
    public void testSingleUser() {
        given()
                .get(baseUrl+("/user/2"))
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2));
    }

    @Test
    public void testUserNotFound() {
        given()
                .get(baseUrl+("/users/23"))
                .then()
                .statusCode(404);
    }

    @Test
    public void testAddNewUser() {
        JSONObject request = new JSONObject();
        request.put("name", "John");
        request.put("job", "QA Engineer");
        System.out.println(request.toJSONString());

        given()
                .header("content-type", "application/json")
                .body(request.toJSONString())
                .when()
                .post(baseUrl+("/users"))
                .then()
                .statusCode(201)
                .body("name", equalTo("John"));
    }

    @Test
    public void testUpdateUser() {
        JSONObject request = new JSONObject();
        request.put("name", "John");
        request.put("job", "QA Manual");
        System.out.println(request.toJSONString());

        given()
                .header("content-type", "application/json")
                .body(request.toJSONString())
                .when()
                .put(baseUrl+("/users/2"))
                .then()
                .statusCode(200)
                .body("job", equalTo("QA Manual"));
    }

    @Test
    public void testDeleteUser() {
        given()
                .delete(baseUrl+("/users/2"))
                .then()
                .statusCode(204);
    }

}
