import com.github.dockerjava.api.model.Driver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OAuthTest {
    public static void main(String[] args) throws InterruptedException {
        String[] courseTitles={"Selenium Webdriver Java", "Cypress", "Protractor"};

       // System.setProperty("webdriver.chrome.driver","D:\\chromedriver.exe");
//        WebDriverManager.chromedriver().setup();
//        ChromeDriver driver = new ChromeDriver();
//        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//       driver.findElement(By.cssSelector("input[type='email']")).sendKeys("lakshmineelavar4567@gmail.com");
//        //tagname[attribute='value']
//       driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//        Thread.sleep(3000);
//        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("M0n$ter@3");
//        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//        Thread.sleep(3000);
//       String url = driver.getCurrentUrl();
        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0ARtbsJqcsLCqsUS7M4LIB2dpEKBwlcZ5y3-B5eaLtsbGTvdDaic6ukxfFEPR3Zwn4iVdMQ&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=3&prompt=none";
       String partialCode= url.split("code=")[1];
        String code=partialCode.split("&scope")[0];
        System.out.println(code);

        RestAssured.useRelaxedHTTPSValidation();
        String accessTokenResponse=given().queryParams("code", code)
                .urlEncodingEnabled(false)
                .queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type","authorization_code")
                .when().log().all().
                post("https://www.googleapis.com/oauth2/v4/token").asString();
        System.out.println(accessTokenResponse);
        JsonPath js= new JsonPath(accessTokenResponse);
        String accessToken=js.getString("access_token");



// If content type is application/json no need to give expect().defaultParser(Parser.JSON) method
        GetCourse gc= given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
                .when().
                get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);

        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());
       // System.out.println(response);
        System.out.println();
        System.out.println("*************************************");
        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
        List<Api> apiCourses=gc.getCourses().getApi();
        for(int i=0;i<apiCourses.size();i++){
            if(apiCourses.get(i).getCourseTitle().equals("SoapUI Webservices testing")){
                System.out.println(apiCourses.get(i).getPrice());
            }
        }

        ArrayList<String> a=new ArrayList<>();
        List<WebAutomation> webAutomationCourses=gc.getCourses().getWebAutomation();
        System.out.println();
        System.out.println("*************************************");
        System.out.println("Web Automation Course Title is :");
        for(int i=0;i<webAutomationCourses.size();i++){
            System.out.println(webAutomationCourses.get(i).getCourseTitle());
                a.add(webAutomationCourses.get(i).getCourseTitle());

        }
        List<String> expectedList= Arrays.asList(courseTitles);
        Assert.assertTrue(a.equals(expectedList));
    }
}
