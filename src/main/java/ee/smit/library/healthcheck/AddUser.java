package ee.smit.library.healthcheck;

import ee.smit.library.controller.PeopleController;
import ee.smit.library.dto.User;
import ee.smit.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Created by Hando.
 */
@Component
public class AddUser extends AbstractHealthIndicator {
    @Autowired
    private PeopleController peopleController;

    @Autowired
    private UserService userService;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        User testUser = new User();
        testUser.setPhone((long) 3434343);
        testUser.setName("testuser");
        HttpStatus addUser = peopleController.addUser(testUser).getStatusCode();
        if (addUser.equals(HttpStatus.OK)) {
            builder.up();
        } else {
            builder.down();
        }
        userService.deleteUser(testUser);

    }
}
