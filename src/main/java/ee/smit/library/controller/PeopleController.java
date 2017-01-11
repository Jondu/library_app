package ee.smit.library.controller;

import ee.smit.library.dao.PeopleDao;
import ee.smit.library.entity.Person;
import ee.smit.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Hando.
 */
@RestController
public class PeopleController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/people", method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public ResponseEntity returnBook(){
        return ResponseEntity.ok(userService.getAvailablePeople());
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addUser(@RequestBody Person person){
        userService.addUser(person);
        return ResponseEntity.ok(person);
    }
}
