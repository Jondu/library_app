package ee.smit.library.healthcheck;

import ee.smit.library.controller.BookListController;
import ee.smit.library.dto.Book;
import ee.smit.library.dto.LoanedBook;
import ee.smit.library.dto.User;
import ee.smit.library.service.BooksService;
import ee.smit.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Hando.
 */
@Component
public class LendBook extends AbstractHealthIndicator {

    @Autowired
    private BookListController bookListController;

    @Autowired
    private BooksService booksService;

    @Autowired
    private UserService userService;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Book testBook = new Book();
        testBook.setTitle("testbook");
        User testUser = new User();
        testUser.setPhone((long) 3434343);
        testUser.setName("testuser");
        booksService.addBook(testBook);
        userService.addUser(testUser);
        userService.getAvailablePeople();
        List<User> users = userService.getAvailablePeople();
        for (User user : users) {
            if (user.getName().equals(testUser.getName())) {
                testUser.setId(user.getId());
            }
        }
        LoanedBook lentbook = new LoanedBook(testBook, testUser);
        HttpStatus loanBook = bookListController.loanBook(lentbook).getStatusCode();
        if (loanBook.equals(HttpStatus.OK)) {
            builder.up();
        } else {
            builder.down();
        }
        userService.deleteUser(testUser);
        booksService.deleteBook(testBook);

    }
}
