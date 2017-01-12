package ee.smit.library.service;

import ee.smit.library.dao.BooksDao;
import ee.smit.library.dao.UserDao;
import ee.smit.library.entity.Book;
import ee.smit.library.entity.LoanedBook;
import ee.smit.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hando.
 */
@Service
public class LoanService {

    @Autowired
    private BooksDao booksDao;

    @Autowired
    private UserDao userDao;

    public void loan (LoanedBook loanedBook){
        Book book = loanedBook.getBook();
        User user = loanedBook.getLoanedTo();
        booksDao.lendBook(book);
        userDao.lendBook(user,booksDao.findBookByName(book));

    }

    public List<Book> getAllBooks() {
        return booksDao.getAllAvailableBooks();
    }

    public List<LoanedBook> getUnavailableBooks() {
        return booksDao.getUnavailableBooks();
    }

    public void returnBook(LoanedBook loanedBook) {
        Book book = loanedBook.getBook();
        User user = loanedBook.getLoanedTo();
        booksDao.returnBook(book);
        userDao.returnBook(user);
    }
}
