package ee.smit.library.dao;

import ee.smit.library.entity.Book;
import ee.smit.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Hando.
 */
@Repository
public class UserDao {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public List<User> getAvailablePeople() {
        return jdbcTemplate.query("SELECT name, phone, id FROM people WHERE NOT EXISTS (SELECT * FROM raamatud WHERE raamatud.id = people.book_id )",
                (rs, rowNum) -> {
                    User user = new User();
                    user.setName(rs.getString("name"));
                    user.setId(rs.getLong("id"));
                    user.setPhone(rs.getLong("phone"));
                    return user;
                }
        );
    }

    public void lendBook(User user, Book book) {
        jdbcTemplate.update(
                "UPDATE people SET book_id=(?) WHERE id=(?)",
                book.getId(), user.getId()
        );

    }

    public void returnBook(User user) {
        jdbcTemplate.update(
                "UPDATE people SET book_id=NULL WHERE id=(?)",
                user.getId()
        );

    }

    public void addUser(User user) {
        jdbcTemplate.update(
                "INSERT INTO people (name, phone) VALUES (?,?)",
                user.getName(), user.getPhone()
        );
    }

    public void deleteUser(User user) {
        jdbcTemplate.update(
                "DELETE FROM people WHERE name=(?)",
                user.getName()
        );
    }
}
