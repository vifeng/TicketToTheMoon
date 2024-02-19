package com.vf.tickettothemoon_BackEnd.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Validated
public class indexController {

    @GetMapping("/")
    public String getDocumentation() {
        return "This is the home page of the API. Please refer to the documentation for more information.";
        // return "redirect:classpath:build/docs/asciidoc/api-guide.html";
    }
}
