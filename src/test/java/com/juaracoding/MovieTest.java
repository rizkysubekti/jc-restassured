package com.juaracoding;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class MovieTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://api.themoviedb.org/3";
    }

    @Test
    public void testNowPlaying() {
        given()
                .header("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwODAxN2ZjNDFlZDRhZjk0ZjFiMGM5ZTZkMDZkMDZmNiIsIm5iZiI6MTcyNDc2OTM1NC41Mzg4NzgsInN1YiI6IjY2Y2M4YzFlMjVmMzEwYzNlZmNhZWZkNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.RCh_Q9ckeNGlXxUlIhXdJuxp0A2EUXz92OK_qcXmMN8")
                .queryParam("language", "en-US")
                .queryParam("page", "2")
                .when()
                .get("movie/now_playing")
                .then()
                .statusCode(200)
                .body("results.title[0]",equalTo("The Mouse Trap"));

    }
}

