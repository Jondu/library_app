package ee.smit.library.controller;

import ee.smit.library.entity.Book;
import ee.smit.library.entity.LoanedBook;
import ee.smit.library.service.BooksService;
import ee.smit.library.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Hando.
 */

@RestController
public class BookListController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private BooksService booksService;


    @RequestMapping(value = "/books", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity listAllBooks() {
        return ResponseEntity.ok(loanService.getAllBooks());
    }

    @RequestMapping(value = "/unavailable", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity listUnavailableBooks() {
        return ResponseEntity.ok(loanService.getUnavailableBooks());
    }

    @RequestMapping(value = "/addbook", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addBook(@RequestBody @Validated Book book) {
        booksService.addBook(book);
        return ResponseEntity.ok(book);
    }

    @RequestMapping(value = "/loan", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity loanBook(@RequestBody LoanedBook book) {
        loanService.loan(book);
        return ResponseEntity.ok(book);
    }

    @RequestMapping(value = "/return", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity returnBook(@RequestBody LoanedBook book) {
        loanService.returnBook(book);
        return ResponseEntity.ok(book);
    }

}
