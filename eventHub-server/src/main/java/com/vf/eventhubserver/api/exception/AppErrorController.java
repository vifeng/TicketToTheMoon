package com.vf.eventhubserver.api.exception;

import org.springframework.web.bind.annotation.RestController;

/**
 * Basic Controller which is called for unhandled errors. Testing with
 * http://localhost:8080/api/employees/ it should replace the : Whitelabel Error Page This
 * application has no explicit mapping for /error, so you are seeing this as a fallback. Fri Sep 08
 * 19:38:27 CEST 2023 There was an unexpected error (type=Not Found, status=404). No message
 * available.
 */
@RestController
public class AppErrorController {
    // TODO_LOW: This is working, but we should get the type and status of the error. Instead here,
    // we hard write it.


    // implements ErrorController {


    // @RequestMapping("/error")
    // public ResponseEntity<GlobalErrorResponse> handleError(Exception exception,
    // HttpServletRequest request) {
    // GlobalErrorResponse errorResponse = new GlobalErrorResponse(ZonedDateTime.now(),
    // HttpStatus.BAD_REQUEST.value(), request.getRequestURI(),
    // "A generic error occurred " + exception.getMessage());
    // return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    // }
}
