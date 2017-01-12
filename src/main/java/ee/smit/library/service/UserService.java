package ee.smit.library.service;

import ee.smit.library.dao.UserDao;
import ee.smit.library.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hando.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> getAvailablePeople() {
        return userDao.getAvailablePeople();
    }


    public void addUser(User user) {
        userDao.addUser(user);
    }

    public void deleteUser(User user){
        userDao.deleteUser(user);
    }
}
