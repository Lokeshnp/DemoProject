package files;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class ReUsableMethods {


	public static JsonPath rawToJson(String response) {
		JsonPath js1 = new JsonPath(response);
		return js1;
	}

	//Login Scenario

	public static String getSessionKEY() {

	String response = given().relaxedHTTPSValidation().header("Content-Type", "application/json").body("{\r\n" +

			"    \"username\": \"RahulShetty\",\r\n" +

			"    \"password\": \"XXXX11\"\r\n" +

			"}").log().all().when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
     return rawToJson(response).get("session.value");
	}
}
