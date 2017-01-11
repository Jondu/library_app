package ee.smit.library.dao;

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
public class PeopleDao {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public List<Person> getAvailablePeople() {
        return jdbcTemplate.query("SELECT name, phone, id FROM people WHERE NOT EXISTS (SELECT * FROM raamatud WHERE raamatud.id = people.book_id )",
                (rs, rowNum) -> {
                    Person person = new Person();
                    person.setName(rs.getString("name"));
                    person.setId(rs.getLong("id"));
                    person.setPhone(rs.getLong("phone"));
                    return person;
                }
        );
    }

    public void lendBook(Person person, Book book) {
        jdbcTemplate.update(
                "UPDATE people SET book_id=(?) WHERE id=(?)",
                book.getId(), person.getId()
        );

    }

    public void returnBook(Person person) {
        jdbcTemplate.update(
                "UPDATE people SET book_id=NULL WHERE id=(?)",
                person.getId()
        );

    }
}
