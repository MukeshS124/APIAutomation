package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.IOException;

import io.cucumber.java.en.*;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class PlaceValidity extends Utils {
	RequestSpecification requst;
	ResponseSpecification respns;
	Response response;
	static String placeId;

	TestDataBuild data = new TestDataBuild();

	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		requst = given().spec(requestSpecification()).body(data.addPlacePayload(name, language, address));
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		// Constructor will be called with value of Resource which to pass
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());

		respns = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if (method.equalsIgnoreCase("POST"))
			response = requst.when().post(resourceAPI.getResource()); // .then().spec(respns).extract().response()
		else if (method.equalsIgnoreCase("GET"))
			response = requst.when().get(resourceAPI.getResource());
	}

	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer code) {
		assertEquals(response.getStatusCode(), 200);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
		assertEquals(getJsonPath(response, keyValue), expectedValue);
	}

	@Then("Verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expName, String resource) throws IOException {
		// Need to hit GetAPI
		placeId = getJsonPath(response, "place_id");
		requst = given().spec(requestSpecification()).queryParam("place_id", placeId);
		user_calls_with_http_request(resource, "GET");
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName, expName);
	}

	@Given("Delete Place payload")
	public void delete_place_payload() throws IOException {
		requst = given().spec(requestSpecification()).body(data.deletePlacePayload(placeId));
	}

}
