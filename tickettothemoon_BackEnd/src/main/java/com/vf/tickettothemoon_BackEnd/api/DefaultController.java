package com.vf.tickettothemoon_BackEnd.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DefaultController
 */
@RestController
public class DefaultController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
