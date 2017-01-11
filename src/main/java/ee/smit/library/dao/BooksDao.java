package ee.smit.library.dao;
import ee.smit.library.entity.LoanedBook;
import ee.smit.library.entity.Book;
import ee.smit.library.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hando.
 */
@Repository
public class BooksDao {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public List<Book> getAllBooks() {
        return jdbcTemplate.query("SELECT * FROM raamatud Where not exists(select * from people where people.book_id = raamatud.id )",
                (rs, rowNum) -> {
                    Book book = new Book();
                    book.setTitle(rs.getString("title"));
                    book.setAvailable(rs.getBoolean("status"));
                    return book;
                }
        );
    }

    public List<LoanedBook> getUnavailableBooks() {
        return jdbcTemplate.query("SELECT raamatud.title, raamatud.status, raamatud.id, people.name, people.phone, people.book_id FROM raamatud INNER JOIN people ON raamatud.id = people.book_id",
                (rs, rowNum) -> {
                    Book book = new Book();
                    Person person = new Person();
                    person.setName(rs.getString("name"));
                    person.setPhone(rs.getLong("phone"));
                    book.setTitle(rs.getString("title"));
                    book.setAvailable(rs.getBoolean("status"));
                    book.setPerson(person);
                    return new LoanedBook(book, person);
                }
        );
    }

    public void lendBook(Book book){
        jdbcTemplate.update(
                "UPDATE raamatud SET status=(?) WHERE title=(?)",
                false, book.getTitle()
        );

    }

    public void returnBook(Book book){
        jdbcTemplate.update(
                "UPDATE raamatud SET status=(?) WHERE title=(?)",
                true, book.getTitle()
        );

    }


    public void addBook(Book book){
        jdbcTemplate.update(
                "INSERT INTO raamatud (title, status) VALUES (?,?) " +
                        "ON CONFLICT DO NOTHING",
                book.getTitle(), true
        );
    }

}