import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import static io.restassured.RestAssured.given;

import files.ReUsableMethods;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
public class JIRATest {
    Properties prop=new Properties();
    @BeforeTest
    public void getData() throws IOException
    {

        FileInputStream fis=new FileInputStream("C:\\Users\\rahul\\workspace\\DemoProject\\src\\files\\env.properties");
        prop.load(fis);

        //prop.get("HOST");
    }
    @Test
    public void JiraAPICreateIssue()
    {
        //Creating Issue/Defect

        RestAssured.baseURI= "http://localhost:8080";
        String res=given().header("Content-Type", "application/json").
                header("Cookie","JSESSIONID="+ ReUsableMethods.getSessionKEY()).
                body("{"+
                        "\"fields\": {"+
                        "\"project\":{"+
                        "\"key\": \"RES\""+
                        "},"+
                        "\"summary\": \"Issue 5 for adding comment\","+
                        "\"description\": \"Creating my second bug\","+
                        "\"issuetype\": {"+
                        "\"name\": \"Bug\""+
                        "}"+
                        "}}").when().
                post("/rest/api/2/issue").then().statusCode(201).extract().response().asString();

        JsonPath js= ReUsableMethods.rawToJson(res);
        String id=js.get("id");
        System.out.println(id);

    }
}
