import com.github.dockerjava.api.model.Driver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static io.restassured.RestAssured.given;

public class OAuthTest {
    public static void main(String[] args) throws InterruptedException {

       // System.setProperty("webdriver.chrome.driver","")
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
      // String url = driver.getCurrentUrl();
        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0ARtbsJpuJRMH5rjxXfn_hdLDKKGaQ9RA9vjFDUFvjp46-FYbUwbGYR2aRgUsINnlxbNaug&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=3&prompt=none";
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




        String response= given().queryParam("access_token", accessToken)
                .when().log().all().
                get("https://rahulshettyacademy.com/getCourse.php").asString();
        System.out.println(response);
    }
}
