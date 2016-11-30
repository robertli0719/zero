/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import robertli.zero.dto.RestException;

/**
 * This class will be extended by other Rest Controller, so that the controller
 * can map exceptions to JSON response.
 *
 * @version 2016-11-23 1.0
 * @author Robert Li
 */
public abstract class GenericRestController {

    private Map<String, String> getFieldErrorMap(BindingResult result) {
        Map<String, String> fieldError = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            String name = error.getField();
            String message = error.getDefaultMessage();
            //getFieldErrors return errors in rand order, so we have to keep the 'big' one
            if (fieldError.containsKey(name) == false) {
                fieldError.put(name, message);
            } else if (fieldError.get(name).compareTo(message) > 0) {
                fieldError.put(name, message);
            }
        }
        return fieldError;
    }

    private List<String> getGlobalErrorList(BindingResult result) {
        List<String> errorList = new ArrayList<>();
        for (ObjectError error : result.getGlobalErrors()) {
            String message = error.getDefaultMessage();
            errorList.add(message);
        }
        return errorList;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        Map<String, String> fieldErrors = getFieldErrorMap(bindingResult);
        List<String> globalErrors = getGlobalErrorList(bindingResult);
        Map<String, Object> map = new HashMap<>();
        map.put("status", "INVALID_REQUEST");
        map.put("fieldErrors", fieldErrors);
        map.put("globalErrors", globalErrors);
        return map;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleRuntimeException(RuntimeException exception) {
        List<String> globalErrors = new ArrayList<>();
        String error = exception.getMessage();
        globalErrors.add(error);
        Map<String, Object> map = new HashMap<>();
        map.put("status", "RuntimeException");
        map.put("fieldError", null);
        map.put("globalError", globalErrors);
        return map;
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity handleRestException(RestException exception) {
        List<String> globalErrors = new ArrayList<>();
        String error = exception.getStatus();
        globalErrors.add(error);
        Map<String, Object> map = new HashMap<>();
        map.put("status", "RestException");
        map.put("fieldError", null);
        map.put("globalError", globalErrors);
        return new ResponseEntity(map, exception.getHttpStatus());
    }
}
