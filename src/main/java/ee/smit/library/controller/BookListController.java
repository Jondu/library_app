package ee.smit.library.controller;

import ee.smit.library.dao.BooksDao;
import ee.smit.library.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Hando.
 */

@RestController
public class BookListController {

    @Autowired
    private BooksDao booksDao;



    @RequestMapping(value = "/books", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity listAllBooks(){
        return ResponseEntity.ok(booksDao.getAllBooks());
    }

    @RequestMapping(value = "/unavailable", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity listUnavailableBooks(){
        return ResponseEntity.ok(booksDao.getUnavailableBooks());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addBook(@RequestBody Book book){
        booksDao.addBook(book);
        return ResponseEntity.ok(book);
    }

    @RequestMapping(value = "/loan", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity loanBook(@RequestBody Book book){
        booksDao.lendBook(book);
        return ResponseEntity.ok(book);
    }

    @RequestMapping(value = "/return", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity returnBook(@RequestBody Book book){
        booksDao.returnBook(book);
        return ResponseEntity.ok(book);
    }

}
