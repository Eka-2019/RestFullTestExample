import io.restassured.RestAssured;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.ParseException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class TestApodApi {
    WebDriver driver;
    private static final String URL_KEY = "https://api.nasa.gov/";
    private static final String API_KEY = "DEMO_KEY";
    private static final String API_KEY_Low = "demo_key";
    String date;

    @Test
    /**
     * Check API for page which is changed each day automatically:
     * so test takes data from UI and compare them with API data
      */

    public void testApodDefault() throws InterruptedException, ParseException {
        RestAssured.baseURI = URL_KEY;
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ebolotova\\webdrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://apod.nasa.gov/apod/astropix.html");
        Thread.sleep(2000);
        APODTodayData today = new APODTodayData(driver);

        RestAssured.useRelaxedHTTPSValidation();
        given()
                .header("User-Agent", "Chrome")
                //  .header("secret_key", "superpassword").
                .when()
                .get(EndPointUrl.APOD.addPath(String.format("?api_key=%s", API_KEY)))
                .then()
                .assertThat()
                .statusCode(200)
                .header("Server", "openresty")
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .body("copyright", equalTo(today.getCopyright()))
                .body("title", equalTo(today.getTitle()))
                .body("url", equalTo(today.getUrl()))
                .body("date", equalTo(today.getDate()))
                .body("media_type", equalTo(today.getMediaType()))
                .body("explanation", containsString(today.getExplanation2()))
        ;
        driver.close();
    }

    @Test
    // check data for particular date
    public void testApodForParticularDate() {
        RestAssured.baseURI = URL_KEY;
        date = "2019-12-30";
        RestAssured.useRelaxedHTTPSValidation();
        given()
                .when()
                .get(EndPointUrl.APOD.addPath(String.format("?api_key=%s&date=%s", API_KEY, date)))
                .then()
                .assertThat()
                .statusCode(200)
                .header("Server", "openresty")
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .body("copyright", equalTo("Stanislav Volskiy"))
                .body("title", equalTo("Messier 20 and 21"))
                .body("date", equalTo("2019-12-30"))
                .body("explanation", startsWith("The beautiful Trifid Nebula, "))
                .body("explanation", containsString("Nebula"))
                .body("hdurl", equalTo("https://apod.nasa.gov/apod/image/1912/M20_volskiy.jpg"))
                .body("media_type", equalTo("image"))
                .body("service_version", equalTo("v1"))
                .body("url", equalTo("https://apod.nasa.gov/apod/image/1912/M20_volskiy1024.jpg"))
        ;
    }


    @Test
    /*
    check that parameter ApiKey is case sensitive
    !!! the same can be done for all parameters if necessary in sensitive/insensitive way)
     */

    public void testApodApiKeyLowCase() {
        RestAssured.baseURI = URL_KEY;
        date = "2019-12-30";
        RestAssured.useRelaxedHTTPSValidation();
        given()
                .when()
                .get(EndPointUrl.APOD.addPath(String.format("?api_key=%s&date=%s", API_KEY_Low, date)))
                .then()
                .assertThat()
                .statusCode(403)
                .statusLine("HTTP/1.1 403 Forbidden")
                .header("Server", "openresty")
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")

        ;

    }

    @Test
    /*
    check that hd parameter is possible as described and there is no exception, because we do not have means to check hd in test
     */
    public void testApodHdPatameter() {
        RestAssured.baseURI = URL_KEY;
        date = "2019-12-30";
        boolean hd1 = true;
        RestAssured.useRelaxedHTTPSValidation();
        given()
                .when()
                .get(EndPointUrl.APOD.addPath(String.format("?api_key=%s&date=%s&hd=%b", API_KEY, date, hd1)))
                .then()
                .assertThat()
                .statusCode(200)
                .header("Server", "openresty")
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .body("copyright", equalTo("Stanislav Volskiy"))
                .body("title", equalTo("Messier 20 and 21"))
                .body("date", equalTo("2019-12-30"))
                .body("explanation", startsWith("The beautiful Trifid Nebula, "))
                .body("explanation", containsString("Nebula"))
                .body("hdurl", equalTo("https://apod.nasa.gov/apod/image/1912/M20_volskiy.jpg"))
                .body("media_type", equalTo("image"))
                .body("service_version", equalTo("v1"))
                .body("url", equalTo("https://apod.nasa.gov/apod/image/1912/M20_volskiy1024.jpg"))
        ;
    }


    @Test
    /*
     check that access is not possible without api_key
     */
    public void testApodWithoutApiKey() {
        RestAssured.baseURI = URL_KEY;
        RestAssured.useRelaxedHTTPSValidation();
        given()
                .when()
                .get(EndPointUrl.APOD.addPath("/"))
                .then()
                .assertThat()
                .statusCode(403)
                .statusLine("HTTP/1.1 403 Forbidden")
                .header("Server", "openresty")
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
        ;
    }

}
