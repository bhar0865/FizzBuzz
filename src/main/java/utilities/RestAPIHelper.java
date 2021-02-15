package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

/**
 * This class contains methods which help in making POST call for a REST endpoint.
 */
public class RestAPIHelper {

    public static final String CONTENT_TYPE = "application/json";

    // This method is used to do the POST call
    public static Response doPost(String url, String body, int status) {

        Response response = getRequestSpec(null, null, url).body(body).when().post(url).then()
                .extract().response();
        assertStatus(status, response);
        return response;
    }

    // This method is to get the request specification for making POST call
    private static RequestSpecification getRequestSpec(String acceptHeader, String acceptHeaderValue, String url) {
        RequestSpecification requestSpecification = RestAssured.given()
                .contentType(CONTENT_TYPE);

        if (acceptHeader != null) {
            requestSpecification.header(acceptHeader, acceptHeaderValue);
        }
        return requestSpecification;
    }

    //This method is used for asserting the status code of POST call
    public static void assertStatus(int status, Response response) {
        if (status > 0) {
            if (response.getStatusCode() >= 400) {
                System.out.println("Error Message:" + response.asString());
            }
            Assert.assertEquals(response.getStatusCode(), status, "Response :" + response.asString());
        }
    }
}
