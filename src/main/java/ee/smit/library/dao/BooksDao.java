package ee.smit.library.dao;

import ee.smit.library.entity.Book;
import ee.smit.library.entity.LoanedBook;
import ee.smit.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Hando.
 */
@Repository
public class BooksDao {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public List<Book> getAllAvailableBooks() {
        return jdbcTemplate.query("SELECT * FROM raamatud WHERE not exists(select * FROM people " +
                        "WHERE people.book_id = raamatud.id )",
                (rs, rowNum) -> {
                    Book book = new Book();
                    book.setTitle(rs.getString("title"));
                    book.setAvailable(rs.getBoolean("status"));
                    return book;
                }
        );
    }

    public List<LoanedBook> getUnavailableBooks() {
        return jdbcTemplate.query("SELECT raamatud.title, raamatud.status, raamatud.id, people.name, people.phone, people.id as personId, " +
                        "people.book_id FROM raamatud INNER JOIN people ON raamatud.id = people.book_id",
                (rs, rowNum) -> {
                    Book book = new Book();
                    User user = new User();
                    user.setName(rs.getString("name"));
                    user.setPhone(rs.getLong("phone"));
                    user.setId(rs.getLong("personId"));
                    book.setTitle(rs.getString("title"));
                    book.setAvailable(rs.getBoolean("status"));
                    return new LoanedBook(book, user);
                }
        );
    }

    public void lendBook(Book book) {
        jdbcTemplate.update(
                "UPDATE raamatud SET status=(?) WHERE title=(?)",
                false, book.getTitle()
        );

    }

    public void returnBook(Book book) {
        jdbcTemplate.update(
                "UPDATE raamatud SET status=(?) WHERE title=(?)",
                true, book.getTitle()
        );

    }


    public void addBook(Book book) {
        jdbcTemplate.update(
                "INSERT INTO raamatud (title, status) VALUES (?,?) " +
                        "ON CONFLICT DO NOTHING",
                book.getTitle().toLowerCase(), true
        );
    }

    public void deleteBook(String book) {
        jdbcTemplate.update(
                "DELETE FROM raamatud WHERE title=(?)",
                book
        );

    }
    public Book findBookByName(Book book) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM raamatud WHERE title = ?",
                new Object[]{book.getTitle()},
                new RowMapper<Book>() {
                    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Book book = new Book();
                        book.setTitle(rs.getString("title"));
                        book.setId(rs.getLong("id"));
                        return book;
                    }
                });
    }
}
