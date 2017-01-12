package ee.smit.library.healthcheck;

import ee.smit.library.controller.BookListController;
import ee.smit.library.dto.Book;
import ee.smit.library.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Hando.
 */
@Component
@Transactional
public class AddBook extends AbstractHealthIndicator{

    @Autowired
    private BookListController bookListController;

    @Autowired
    private BooksService booksService;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Book book = new Book();
        book.setTitle("testbook");
        HttpStatus addBook = bookListController.addBook(book).getStatusCode();
        if (addBook.equals(HttpStatus.OK)) {
            builder.up();
        } else {
            builder.down();
        }
        booksService.deleteBook(book);
    }
}
