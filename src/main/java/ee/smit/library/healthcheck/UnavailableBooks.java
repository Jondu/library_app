package ee.smit.library.healthcheck;

import ee.smit.library.controller.BookListController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Created by Hando.
 */
@Component
public class UnavailableBooks extends AbstractHealthIndicator {
    @Autowired
    private BookListController bookListController;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        HttpStatus unAvailableBooks = bookListController.listUnavailableBooks().getStatusCode();
        if (unAvailableBooks.equals(HttpStatus.OK)) {
            builder.up();
        } else {
            builder.down();
        }
    }
}
