package com.vf.eventhubserver;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Validated
public class IndexController {

    @GetMapping("/")
    public String getDocumentation() {
        return "This is the home page of the API. Please refer to the documentation for more information.";
    }
}
