import files.ReUsableMethods;
import files.librarypayload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class DynamicJson {
    ArrayList<String> booksID=new ArrayList<>();

    @Test(dataProvider="BooksData")
    public void addBook(String isbn, String aisle){
        RestAssured.baseURI="http://216.10.245.166";
        String resp=given().log().all().
                header("Content-Type","application/json").
                body(librarypayload.AddBook(isbn,aisle)).
                when().
                post("/Library/Addbook.php").
                then().log().all().assertThat().statusCode(200).
                extract().response().asString();
        JsonPath js= ReUsableMethods.rawToJson(resp);
        String id=js.get("ID");
        booksID.add(id);
        System.out.println(id);
    }

    //deleteBOok
    //delete Book using ID
    @Test(dataProvider="BooksDataForDel")
    public void delBook(String id){
        RestAssured.baseURI="http://216.10.245.166";
        String resp=given().log().all().
                header("Content-Type","application/json").
                body(librarypayload.delBook(id)).
                when().
                post("/Library/DeleteBook.php").
                then().log().all().assertThat().statusCode(200).
                extract().response().asString();
        JsonPath js= ReUsableMethods.rawToJson(resp);
        String msg=js.get("msg");
        System.out.println(msg);
    }


    @DataProvider(name="BooksDataForDel")
    public Object[]  getBooksData()
    {
      return  booksID.toArray();
    }

    @DataProvider(name="BooksData")
    public Object[][]  getData()
    {
//array=collection of elements
//multidimensional array= collection of arrays
        return new Object[][] {{"Max","789"},{"Carl","420"}, {"Bak","537"} };

    }

    }

