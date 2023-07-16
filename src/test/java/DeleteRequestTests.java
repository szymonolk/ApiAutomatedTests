import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class DeleteRequestTests {

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    public void shouldDeleteUserAndReturn204(){
        given().delete("/api/users/2")
                .then()
                .statusCode(204);

    }
}
