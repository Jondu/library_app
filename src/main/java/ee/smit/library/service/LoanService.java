package ee.smit.library.service;

import ee.smit.library.dao.BooksDao;
import ee.smit.library.dao.PeopleDao;
import ee.smit.library.entity.Book;
import ee.smit.library.entity.LoanedBook;
import ee.smit.library.entity.Person;
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
    private PeopleDao peopleDao;

    public void loan (LoanedBook loanedBook){
        Book book = loanedBook.getBook();
        Person person = loanedBook.getLoanedTo();
        booksDao.lendBook(book);
        peopleDao.lendBook(person,booksDao.findBookByName(book));

    }


    public List<Book> getAllBooks() {
        return booksDao.getAllBooks();
    }

    public List<LoanedBook> getUnavailableBooks() {
        return booksDao.getUnavailableBooks();
    }


    public void returnBook(LoanedBook loanedBook) {
        Book book = loanedBook.getBook();
        Person person = loanedBook.getLoanedTo();
        booksDao.returnBook(book);
        peopleDao.returnBook(person);
    }
}
