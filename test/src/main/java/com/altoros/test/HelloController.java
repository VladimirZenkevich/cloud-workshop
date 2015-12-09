package com.altoros.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by uladzimir.ziankevich on 12/9/2015.
 */
@RestController
@RequestMapping(value = "/test")
public class HelloController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello from PWS!!!";
    }

}
