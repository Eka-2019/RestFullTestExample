import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestNeoLookUp {
    private static final String URL_KEY = "https://api.nasa.gov/";
    private static final String API_KEY = "DEMO_KEY";

    @Test
    // check data for particular asteroid (# 2000433)
    public void testNeoFeed() {
        RestAssured.baseURI = URL_KEY;
        String asteroidNum = "2000433";
        //JsonPath.params();
        RestAssured.useRelaxedHTTPSValidation();
        given()
                .when()
                .get(EndPointUrl.NEOLOOKUP.addPath(String.format(asteroidNum + "?api_key=%s", API_KEY)))
                .then()
                .assertThat()
                .statusCode(200)
                .header("Server", "openresty")
                //.header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .body("links.self", equalTo("http://www.neowsapp.com/rest/v1/neo/2000433?api_key=DEMO_KEY"))
                .body("id", equalTo("2000433"))
                .body("neo_reference_id", equalTo("2000433"))
                .body("name", equalTo("433 Eros (A898 PA)"))
                .body("name_limited", equalTo("Eros"))
                .body("designation",equalTo("433"))
                .body("nasa_jpl_url", equalTo("http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=2000433"))
                .body("absolute_magnitude_h", is(11.16f))
                .body("estimated_diameter.kilometers.estimated_diameter_min", is(15.5795524128f))// should be 15.5795524128
                .body("is_potentially_hazardous_asteroid", equalTo(false))
                .body("close_approach_data.close_approach_date", hasItem("1900-12-27"))

                //TODO - why integer number too large !!!
                //.body("close_approach_data.epoch_date_close_approach", is(BigDecimal.valueOf(-2177879400000)))//
                .body("close_approach_data.close_approach_date_full", hasItem("1900-Dec-27 01:30"))
        ;
    }


    @Test
    // if we want to check whole or part of body as String for particular asteroid (# 2000433)
    public void testNeoFeedBody() {
        RestAssured.baseURI = URL_KEY;
        RequestSpecification httpRequest = RestAssured.given();
        String asteroidNum = "2000433";
        Response response = httpRequest.get(EndPointUrl.NEOLOOKUP.addPath(String.format(asteroidNum + "?api_key=%s", API_KEY)));

        //JsonPath.params();
        RestAssured.useRelaxedHTTPSValidation();

        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();

        org.testng.Assert.assertTrue(bodyAsString.contains("\"id\":\"2000433\""),"Response body contains id = 2000433");

    }


}
