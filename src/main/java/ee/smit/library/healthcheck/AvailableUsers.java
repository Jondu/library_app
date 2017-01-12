package ee.smit.library.healthcheck;

import ee.smit.library.controller.PeopleController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Created by Hando.
 */
@Component
public class AvailableUsers extends AbstractHealthIndicator {

    @Autowired
    private PeopleController peopleController;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        HttpStatus availableUser = peopleController.availablePeople().getStatusCode();
        if (availableUser.equals(HttpStatus.OK)) {
            builder.up();
        } else {
            builder.down();
        }

    }
}
