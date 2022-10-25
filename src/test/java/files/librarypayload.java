package files;

public class librarypayload {
    public static String AddBook(String isbn, String aisle){
        String payload= "{\n" +
                "\"name\":\"Learn Appium Automation with Java\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"John foer\"\n" +
                "}";
        return payload;
    }

    public static String delBook(String id){
        String payload= "{\n" +
                "    \"ID\": \""+id+"\"\n" +
                "}";
        return payload;
    }
}
