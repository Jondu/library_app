package ee.smit.library.dao;

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
        return jdbcTemplate.query("SELECT * FROM people Where not exists(select * from raamatud where raamatud.id = people.book_id )",
                (rs, rowNum) -> {
                    Person person = new Person();
                    person.setName(rs.getString("name"));
                    return person;
                }
        );
    }

    public void lendBook(Person book){
        jdbcTemplate.update(
                "UPDATE raamatud SET status=(?) WHERE title=(?)",
                false, book.getName()
        );

    }
}
