package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import utility.PropertyUtil_1;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class APIControlActions {
    protected static String token;//driver
    private static RequestSpecBuilder requestSpecBuilder;
    private final PropertyUtil_1 propertyUtil = new PropertyUtil_1("src/main/resources/config/envConfig.properties");

    private void buildRequestSpecBuilder() {
        if (requestSpecBuilder == null) {
            requestSpecBuilder = new RequestSpecBuilder();
        }
//        requestSpecBuilder.log(LogDetail.ALL);
    }

    protected void setToken(String tokenValue) {
        token = tokenValue;
    }

    protected void setHeader(String key, String value) {
        buildRequestSpecBuilder();
        requestSpecBuilder.addHeader(key, value);
    }

    protected void setHeaders(Map<String, String> headers) {
        buildRequestSpecBuilder();
        requestSpecBuilder.addHeaders(headers);
    }

    public void setBody(String body) {
        buildRequestSpecBuilder();
        requestSpecBuilder.setBody(body);
    }

    public void setBaseUri(String baseUri) {
        buildRequestSpecBuilder();
        requestSpecBuilder.setBaseUri(baseUri);
    }

    public void setQueryParam(String key, String value) {
        buildRequestSpecBuilder();
        requestSpecBuilder.addQueryParam(key, value);
    }

    public void setQueryParams(Map<String, String> queryParams) {
        buildRequestSpecBuilder();
        requestSpecBuilder.addQueryParams(queryParams);
    }

    public Response executePatchAPI(String endPoint) {
        String baseURI = propertyUtil.getProperty(ApplicationConfig.getEnvironment());

        Response response = given()
                .spec(requestSpecBuilder.build())
                .baseUri(baseURI)
                .when()
                .patch(endPoint)
                .then()
                .extract()
                .response();
        requestSpecBuilder = null; // Reset the builder after request execution
        return response;
    }

    public Response executePostAPI(String endPoint) {
        String baseURI = propertyUtil.getProperty(ApplicationConfig.getEnvironment());

        Response response = given()
                .spec(requestSpecBuilder.build())
                .baseUri(baseURI)
                .when()
                .post(endPoint)
                .then()
                .extract()
                .response();
        requestSpecBuilder = null; // Reset the builder after request execution
        return response;
    }

    public Response executeGetAPI(String endPoint) {
        String baseURI = propertyUtil.getProperty(ApplicationConfig.getEnvironment());

        Response response = given()
                .log().all()
                .spec(requestSpecBuilder.build())
                .baseUri(baseURI)
                .when()
                .get(endPoint)
                .then()
                .extract()
                .response();
        requestSpecBuilder = null; // Reset the builder after request execution
        return response;
    }
}
