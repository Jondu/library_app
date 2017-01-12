package ee.smit.library;

import ee.smit.library.entity.Book;
import ee.smit.library.entity.LoanedBook;
import ee.smit.library.service.BooksService;
import ee.smit.library.service.LoanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

/**
 * Created by Hando.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookListTest {

    @Autowired
    private BooksService booksService;

    @Autowired
    private LoanService loanService;

    @Test
    public void addBook() {
        Book book = new Book();
        book.setTitle("TestRaamat");
        booksService.addBook(book);
        List<Book> testList = new ArrayList<>();
        testList.add(book);
        List<Book> allAvailableBooks = loanService.getAllBooks();
        testList.forEach(listBook -> assertFalse(allAvailableBooks.contains(listBook)));
    }

    @Test
    public void getUnavailableBooks(){
        List<LoanedBook> allLoanedBooks = loanService.getUnavailableBooks();
        List<Book> allUnavailableBooks = new ArrayList<>();
        for (LoanedBook loanedBook : allLoanedBooks) {
            allUnavailableBooks.add(loanedBook.getBook());
        }
        List<Book> allAvailableBooks = loanService.getAllBooks();
        allUnavailableBooks.forEach(unavavailable -> assertFalse(allAvailableBooks.contains(unavavailable)));
    }
}
