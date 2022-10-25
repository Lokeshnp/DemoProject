import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
       // validate if Add Place API is workimg as expected
        //Add place-> Update Place with New Address -> Get Place to validate if New address is present in response

        //given - all input details
        //when - Submit the API -resource,http method
        //Then - validate the response



//        RestAssured.baseURI="http://rahulshettyacademy.com";
//        String response = given().log().all().queryParam("qaclick123").header("Content-Type", "application/json")
//                .body(payload.AddPlace()).when().post("maps/api/place/add/json")
//                .then().assertThat().statusCode(200).body("scope",equalTo("APP")).
//                header("Server","Apache/2.4.41 (Ubuntu)").extract().response().asString();
//        System.out.println(response);
//        JsonPath js=new JsonPath(response); //for parsing Json
//        String placeId=js.getString("place_id");
//        System.out.println(placeId);


// use static json file from external location
// content of the file to String -> content of the file can conbert into Byte -> Byte data to String
        RestAssured.baseURI="http://rahulshettyacademy.com";
        String response = given().log().all().queryParam("qaclick123").header("Content-Type", "application/json")
                .body(new String(Files.readAllBytes(Paths.get("D:\\REST API Automation\\AddPlace.json")))).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope",equalTo("APP")).
                header("Server","Apache/2.4.41 (Ubuntu)").extract().response().asString();
        System.out.println(response);
        JsonPath js=new JsonPath(response); //for parsing Json
        String placeId=js.getString("place_id");

        System.out.println(placeId);
        //Update Place
        String newAddress = "Summer Walk, Africa";

        given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
                .body("{\r\n" +
                        "\"place_id\":\""+placeId+"\",\r\n" +
                        "\"address\":\""+newAddress+"\",\r\n" +
                        "\"key\":\"qaclick123\"\r\n" +
                        "}").
                when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

//Get Place

        String getPlaceResponse=	given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id",placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();
        JsonPath js1= ReUsableMethods.rawToJson(getPlaceResponse);
        String actualAddress =js1.getString("address");
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress, newAddress);

        //Cucumber Junit, Testng


    }
}
