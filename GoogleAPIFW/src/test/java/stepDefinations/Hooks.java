package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		// Write a code that will give you placeID
		// Execute this scenario only when placeID is null
		PlaceValidity m = new PlaceValidity();
		if (PlaceValidity.placeId == null) {
			m.add_place_payload_with("Manoj", "Odia", "India");
			m.user_calls_with_http_request("AddPlaceAPI", "POST");
			m.verify_place_id_created_maps_to_using("Manoj", "getPlaceAPI");
		}

	}
}
