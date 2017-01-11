package ee.smit.library.service;

import ee.smit.library.dao.PeopleDao;
import ee.smit.library.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hando.
 */
@Service
public class UserService {

    @Autowired
    private PeopleDao peopleDao;

    public List<Person> getAvailablePeople() {
        return peopleDao.getAvailablePeople();
    }


    public void addUser(Person person) {
        peopleDao.addUser(person);
    }
}
