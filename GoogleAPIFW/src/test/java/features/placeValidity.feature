Feature: Validating Place API's
@AddPlace
Scenario Outline: Verify if Place is being successfully added using AddPlaceAPI
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When user calls "AddPlaceAPI" with "Post" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And Verify place_Id created maps to "<name>" using "getPlaceAPI"
    
Examples: 
      | name    | language | address						|
      | MyHouse | English  | World Cross Center |
  #   | UrHouse | Hindi 	 | Church Gate				|

@DeletePlace
Scenario: Verify if Delete place functionality is working
 		Given Delete Place payload
 		When user calls "deletePlaceAPI" with "Post" http request
 		Then the API call got success with status code 200
 		And "status" in response body is "OK"