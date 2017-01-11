package ee.smit.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Hando.
 */
@Controller
public class FrontPageController {

    @RequestMapping("/")
    public String home() {
        return "index";
    }

}
