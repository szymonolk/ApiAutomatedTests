import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostRequestTests {

    Map<String, Object> requestBody = new HashMap<>();

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    public void createNewUserAndReturn201() {
        requestBody.put("name", "Michael Corleone");
        requestBody.put("job", "Godfather");

        given().contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("Michael Corleone"));
    }

    @Test
    public void registerNewUser(){
        requestBody.put("email", "eve.holt@reqres.in");
        requestBody.put("password", "pistol");

        given().contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/register")
                .then()
                .statusCode(200);

    }

    @Test
    public void unsuccessfulRegisterNewUser(){
        requestBody.put("email", "wrong@email");

        given().contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/register")
                .then()
                .statusCode(400) // we can do smth like HttpStatus.SC_BAD_REQUEST
                .body("error", equalTo("Missing password"));

    }

    @Test
    public void loginSuccessfulAndReturnToken(){
        requestBody.put("email", "eve.holt@reqres.in");
        requestBody.put("password", "cityslicka");

        given().contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

}
