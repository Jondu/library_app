package ee.smit.library.service;

import ee.smit.library.dao.BooksDao;
import ee.smit.library.entity.Book;
import ee.smit.library.entity.LoanedBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Hando.
 */
@Service
public class BooksService {

    @Autowired
    private BooksDao booksDao;

    public void addBook(Book book) {
        booksDao.addBook(book);
    }

}
