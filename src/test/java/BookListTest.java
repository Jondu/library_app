import ee.smit.library.dao.BooksDao;
import ee.smit.library.entity.Book;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertFalse;

/**
 * Created by Hando.
 */

public class BookListTest {

    @Autowired
    private BooksDao booksDao;

    @Test
    public void getAllBooks() {
        Book book = new Book();
        book.setTitle("Batman");
        booksDao.addBook(book);
        assertFalse(book.getTitle().equals("jän"));
    }

}
