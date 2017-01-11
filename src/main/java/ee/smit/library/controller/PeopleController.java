package ee.smit.library.controller;

import ee.smit.library.dao.PeopleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hando.
 */
@RestController
public class PeopleController {
    @Autowired
    private PeopleDao peopleDao;

    @RequestMapping(value = "/people", method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public ResponseEntity returnBook(){
        return ResponseEntity.ok(peopleDao.getAvailablePeople());
    }
}
