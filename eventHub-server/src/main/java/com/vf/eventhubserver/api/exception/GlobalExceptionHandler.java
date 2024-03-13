package com.vf.eventhubserver.api.exception;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.vf.eventhubserver.exception.CreateException;
import com.vf.eventhubserver.exception.DataAccessException;
import com.vf.eventhubserver.exception.DuplicateKeyException;
import com.vf.eventhubserver.exception.FinderException;
import com.vf.eventhubserver.exception.GlobalErrorResponse;
import com.vf.eventhubserver.exception.NullException;
import com.vf.eventhubserver.exception.ObjectNotFoundException;
import com.vf.eventhubserver.exception.PatchException;
import com.vf.eventhubserver.exception.RemoveException;
import com.vf.eventhubserver.exception.UpdateException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

/**
 * GlobalExceptionHandler for handling the Application exceptions
 */
@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<GlobalErrorResponse> genericHandler(Exception exception,
                        HttpServletRequest request) {
                GlobalErrorResponse errorResponse = new GlobalErrorResponse(ZonedDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(), request.getRequestURI(),
                                "A generic error occurred : " + exception.getMessage());
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        /**
         * 422 Unprocessable Entity.
         * 
         * @param exception
         * @param request
         * @return
         */
        @ExceptionHandler(IllegalArgumentException.class)
        @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        @ResponseBody
        public ResponseEntity<Object> handleIllegalArgumentException(
                        IllegalArgumentException exception, HttpServletRequest request) {
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("timestamp", LocalDateTime.now());
                body.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());
                body.put("path", request.getRequestURI());
                body.put("message", "An illegal argument error occurred : ");

                List<String> errors = Arrays.stream(exception.getStackTrace())
                                .map(StackTraceElement::toString).collect(Collectors.toList());

                body.put("errors", errors);

                return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        /**
         * 422 UNPROCESSABLE_ENTITY
         * 
         * @param exception
         * @param request
         * @return
         */
        @ExceptionHandler(CreateException.class)
        @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        @ResponseBody
        public ResponseEntity<GlobalErrorResponse> handleCreateException(CreateException exception,
                        HttpServletRequest request) {
                GlobalErrorResponse errorResponse = new GlobalErrorResponse(ZonedDateTime.now(),
                                HttpStatus.UNPROCESSABLE_ENTITY.value(), request.getRequestURI(),
                                "A create object error occurred : " + exception.getMessage());
                return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        /**
         * 422 Unprocessable Entity.
         * 
         * @param exception
         * @param request
         * @return
         */
        @ExceptionHandler(UpdateException.class)
        @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        @ResponseBody
        public ResponseEntity<GlobalErrorResponse> handleUpdateException(UpdateException exception,
                        HttpServletRequest request) {
                GlobalErrorResponse errorResponse = new GlobalErrorResponse(ZonedDateTime.now(),
                                HttpStatus.UNPROCESSABLE_ENTITY.value(), request.getRequestURI(),
                                "Update error : " + exception.getMessage());
                return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        /**
         * 422 Unprocessable Entity.
         * 
         * @param exception
         * @param request
         * @return
         */
        @ExceptionHandler(PatchException.class)
        @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        @ResponseBody
        public ResponseEntity<GlobalErrorResponse> handlePatchException(PatchException exception,
                        HttpServletRequest request) {
                GlobalErrorResponse errorResponse = new GlobalErrorResponse(ZonedDateTime.now(),
                                HttpStatus.UNPROCESSABLE_ENTITY.value(), request.getRequestURI(),
                                "Patch error : " + exception.getMessage());
                return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        /**
         * 409 Conflict.
         * 
         * @param exception
         * @param request
         * @return
         */
        @ExceptionHandler(RemoveException.class)
        @ResponseStatus(HttpStatus.CONFLICT)
        @ResponseBody
        public ResponseEntity<GlobalErrorResponse> handleRemoveException(RemoveException exception,
                        HttpServletRequest request) {
                GlobalErrorResponse errorResponse = new GlobalErrorResponse(ZonedDateTime.now(),
                                HttpStatus.CONFLICT.value(), request.getRequestURI(),
                                "Remove error : " + exception.getMessage());
                return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }

        /**
         * 404 Not Found.
         * 
         * @param exception
         * @param request
         * @return
         */
        @ExceptionHandler(FinderException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ResponseBody
        public ResponseEntity<GlobalErrorResponse> handleFinderException(FinderException exception,
                        HttpServletRequest request) {
                GlobalErrorResponse errorResponse = new GlobalErrorResponse(ZonedDateTime.now(),
                                HttpStatus.NOT_FOUND.value(), request.getRequestURI(),
                                "Finder error occured : " + exception.getMessage());
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        /**
         * 409 Conflict.
         * 
         * @param exception
         * @param request
         * @return
         */
        @ExceptionHandler(DuplicateKeyException.class)
        @ResponseStatus(HttpStatus.CONFLICT)
        @ResponseBody
        public ResponseEntity<GlobalErrorResponse> handleDuplicateKeyException(
                        DuplicateKeyException exception, HttpServletRequest request) {
                GlobalErrorResponse errorResponse = new GlobalErrorResponse(ZonedDateTime.now(),
                                HttpStatus.CONFLICT.value(), request.getRequestURI(),
                                "A duplicate key error occurred : " + exception.getMessage());
                return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }

        /**
         * 400 Bad Request.
         * 
         * @param exception
         * @param request
         * @return
         */
        @ExceptionHandler(NullException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ResponseBody
        public ResponseEntity<GlobalErrorResponse> handleNullException(NullException exception,
                        HttpServletRequest request) {

                GlobalErrorResponse errorResponse = new GlobalErrorResponse(ZonedDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(), request.getRequestURI(),
                                "Data should not be null - error : " + exception.getMessage());
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        /**
         * 404 Not Found.
         * 
         * @param exception
         * @param request
         * @return
         */
        @ExceptionHandler(ObjectNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ResponseBody
        public ResponseEntity<GlobalErrorResponse> handleObjectNotFoundException(
                        ObjectNotFoundException exception, HttpServletRequest request) {
                GlobalErrorResponse errorResponse = new GlobalErrorResponse(ZonedDateTime.now(),
                                HttpStatus.NOT_FOUND.value(), request.getRequestURI(),
                                "Object not found - error : " + exception.getMessage());
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        /**
         * 500 Internal Server Error.
         * 
         * @param exception
         * @param request
         * @return
         */
        @ExceptionHandler(DataAccessException.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        @ResponseBody
        public ResponseEntity<GlobalErrorResponse> handleDataAccessException(
                        DataAccessException exception, HttpServletRequest request) {
                GlobalErrorResponse errorResponse = new GlobalErrorResponse(ZonedDateTime.now(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(),
                                "A data access error occurred : " + exception.getMessage());
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        public ResponseEntity<Object> handleValidationExceptions(
                        MethodArgumentNotValidException ex) {
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("timestamp", LocalDateTime.now());
                body.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());

                List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.toList());

                body.put("errors", errors);

                return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
        public ResponseEntity<Object> handleConstraintViolationException(
                        ConstraintViolationException ex) {
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("timestamp", LocalDateTime.now());
                body.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());

                List<String> errors = ex.getConstraintViolations().stream()
                                .map(ConstraintViolation::getMessage).collect(Collectors.toList());

                body.put("errors", errors);

                return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity<Object> handleHttpMessageNotReadableException(
                        org.springframework.http.converter.HttpMessageNotReadableException ex) {
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("timestamp", LocalDateTime.now());
                body.put("status", HttpStatus.BAD_REQUEST.value());
                body.put("message",
                                "Peobably a malformed JSON request. Please check your request body and its data types.");

                List<String> errors = Arrays.stream(ex.getStackTrace())
                                .map(StackTraceElement::toString).collect(Collectors.toList());

                body.put("errors", errors);

                return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(java.util.NoSuchElementException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ResponseEntity<Object> handleNoSuchElementException(
                        java.util.NoSuchElementException ex) {
                Map<String, Object> body = new LinkedHashMap<>();
                body.put("timestamp", LocalDateTime.now());
                body.put("status", HttpStatus.NOT_FOUND.value());
                body.put("message", "No such element found in the database.");

                List<String> errors = Arrays.stream(ex.getStackTrace())
                                .map(StackTraceElement::toString).collect(Collectors.toList());

                body.put("errors", errors);

                return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
}
