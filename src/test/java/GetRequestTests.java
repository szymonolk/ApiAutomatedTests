import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class GetRequestTests {



    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }


    @Test
    public void serverReturn200WithSingleUser() {
        when().
                get("/api/users/2")
                .then()
                .statusCode(200)
                .body("data.email", equalTo("janet.weaver@reqres.in"))
                .body("data.first_name", equalTo("Janet"));

    }

    @Test
    public void serverReturnListOfUsers() {
        when().get("/api/users?page=2")
                .then()
                .statusCode(200)
                .body("per_page", equalTo(6))
                .body("data", hasSize(6));
    }

    @Test
    public void serverReturnListOfUsersAndCheckContainingElements() {
        when().get("/api/users?page=2")
                .then()
                .statusCode(200)
                .body("data[0].first_name", equalTo("Michael"))
                .body("data[0].last_name", equalTo("Lawson"))
                .body("data[0].email", containsString("@"));

    }

    @Test
    public void serverReturnListOfUsersAndCheckEmailFormat() {
        when().get("/api/users?page=2")
                .then()
                .statusCode(200)
                .body("data.email", everyItem(containsString("@")));

    }

    @Test
    public void checkAvatarIsNotNull() {
        when().get("/api/users?page=2")
                .then()
                .statusCode(200)
                .body("data.avatar", everyItem(notNullValue()));
    }

    @Test
    public void checkIdIsInteger() {
        when().get("/api/users?page=2")
                .then()
                .statusCode(200)
                .body("data.id", everyItem(instanceOf(Integer.class)));

    }
}
