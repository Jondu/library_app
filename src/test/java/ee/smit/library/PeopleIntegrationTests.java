package ee.smit.library;

import com.jayway.restassured.RestAssured;
import ee.smit.library.entity.User;
import ee.smit.library.service.UserService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

/**
 * Created by Hando.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PeopleIntegrationTests {
    private static User testUser;

    @Autowired
    private UserService userService;


    @BeforeClass
    public static void setup() {
        testUser = new User();
        testUser.setName("Testuser");
        testUser.setPhone((long) 445325);

        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = Integer.valueOf(8080);
        } else {
            RestAssured.port = Integer.valueOf(port);
        }

        String basePath = System.getProperty("server.base");
        if (basePath == null) {
            basePath = "/raamatukogu/";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;

    }

    @Test
    public void healthCheck() {
        given().when().get("/people").then().statusCode(200);
        given().when().post("/adduser").then().statusCode(415);
    }


    @Test
    public void availablePeople() {
        userService.addUser(testUser);
        given().when().get("/people").then()
                .body(containsString(testUser.getName()));
    }

    @Test
    public void addUser() {
        given()
                .contentType("application/json")
                .body(testUser)
                .when().post("/adduser").then()
                .statusCode(200);
    }

    @After
    public void clearData() {
        userService.deleteUser(testUser);
    }
}
