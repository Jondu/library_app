package ee.smit.library;

import com.jayway.restassured.RestAssured;
import ee.smit.library.entity.Book;
import ee.smit.library.entity.LoanedBook;
import ee.smit.library.entity.User;
import ee.smit.library.service.BooksService;
import ee.smit.library.service.LoanService;
import ee.smit.library.service.UserService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

/**
 * Created by Hando.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookListIntegrationTests {


    private static Book testbook;
    private static User testUser;

    @Autowired
    private LoanService loanService;

    @Autowired
    private BooksService booksService;

    @Autowired
    private UserService userService;


    @BeforeClass
    public static void setup() {
        testbook = new Book();
        testbook.setTitle("testbook");
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
        given().when().get("/books").then().statusCode(200);
        given().when().get("/unavailable").then().statusCode(200);
        given().when().post("/loan").then().statusCode(415);
        given().when().post("/return").then().statusCode(415);
        given().when().post("/addbook").then().statusCode(415);
    }

    @Test
    public void availableBooksTest() {
        booksService.addBook(testbook);
        given().when().get("/books").then()
                .body(containsString(testbook.getTitle()));
    }

    @Test
    public void unavailableBooksTest() {
        booksService.addBook(testbook);
        userService.addUser(testUser);
        List<User> users = userService.getAvailablePeople();
        for (User user : users) {
            if (user.getName().equals(testUser.getName())) {
                testUser.setId(user.getId());
            }
        }
        loanService.loan(new LoanedBook(testbook, testUser));
        given().when().get("/unavailable").then()
                .body(containsString(testbook.getTitle()));
    }

    @Test
    public void lendBookTest() {
        booksService.addBook(testbook);
        userService.addUser(testUser);
        List<User> users = userService.getAvailablePeople();
        for (User user : users) {
            if (user.getName().equals(testUser.getName())) {
                testUser.setId(user.getId());
            }
        }
        given().contentType("application/json")
                .body(new LoanedBook(testbook, testUser))
                .when().post("/loan").then()
                .statusCode(200);
    }

    @Test
    public void returnBookTest() {
        booksService.addBook(testbook);
        userService.addUser(testUser);
        List<User> users = userService.getAvailablePeople();
        for (User user : users) {
            if (user.getName().equals(testUser.getName())) {
                testUser.setId(user.getId());
            }
        }
        LoanedBook testLoan = new LoanedBook(testbook, testUser);
        loanService.loan(testLoan);
        given().contentType("application/json")
                .body(testLoan)
                .when().post("/return").then()
                .statusCode(200);
    }

    @Test
    public void addbookTest() {
        given()
                .contentType("application/json")
                .body(testbook)
                .when().post("/addbook").then()
                .statusCode(200);
    }

    @After
    public void clearData() {
        userService.deleteUser(testUser);
        booksService.deleteBook(testbook);
    }

}
