package com.vf.eventhubserver.api.Exceptions;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestErrorController for testing the GlobalExceptionHandler
 */
@RestController
@RequestMapping("/test/error")
public class TestErrorController {

    @GetMapping
    public String test() throws Exception {
        // Simulate a custom exception being thrown
        throw new Exception("This is a test for a create exception!");
    }
}
