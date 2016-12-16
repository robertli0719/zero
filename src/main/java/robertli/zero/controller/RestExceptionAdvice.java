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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import robertli.zero.dto.RestErrorDto;

/**
 * This advice will be using for mapping exceptions to JSON response.
 *
 * @version 2016-12-12 1.0
 * @author Robert Li
 */
@RestController
@ControllerAdvice
public class RestExceptionAdvice {

    private void appendFieldErrors(List<RestErrorDto> errorList, BindingResult result) {
        //getFieldErrors return errors in rand order, so we have to keep the 'big' one
        Map<String, String> messageMap = new HashMap();
        for (FieldError error : result.getFieldErrors()) {
            String name = error.getField();
            String message = error.getDefaultMessage();
            if (messageMap.containsKey(name) && messageMap.get(name).compareTo(message) <= 0) {
                continue;
            }
            messageMap.put(name, message);
            RestErrorDto errorDto = new RestErrorDto();
            errorDto.setType("FIELD_ERROR");
            errorDto.setSource(name);
            errorDto.setMessage(message);
            errorDto.setDetail("BindingResult gets validation errors");
            errorList.add(errorDto);
        }
    }

    private void appendGlobalErrors(List<RestErrorDto> errorList, BindingResult result) {
        for (ObjectError error : result.getGlobalErrors()) {
            RestErrorDto errorDto = new RestErrorDto();
            errorDto.setType("GLOBAL_ERROR");
            errorDto.setSource(error.getObjectName());
            errorDto.setMessage(error.getDefaultMessage());
            errorDto.setDetail("BindingResult gets validation errors");
            errorList.add(errorDto);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<RestErrorDto> errorList = new ArrayList<>();
        appendFieldErrors(errorList, bindingResult);
        appendGlobalErrors(errorList, bindingResult);

        Map<String, Object> map = new HashMap<>();
        map.put("status", "INVALID_REQUEST");
        map.put("errors", errorList);
        return map;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleRuntimeException(RuntimeException exception) {
        RestErrorDto errorDto = new RestErrorDto();
        errorDto.setType("RUNTIME_EXCEPTION");
        errorDto.setSource(null);
        errorDto.setMessage("There are some errors in server.");
        errorDto.setDetail(exception.getMessage());
        List<RestErrorDto> errorList = new ArrayList<>();
        errorList.add(errorDto);

        Map<String, Object> map = new HashMap<>();
        map.put("status", "RUNTIME_EXCEPTION");
        map.put("errors", errorList);
        return map;
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity handleRestException(RestException exception) {
        String status = exception.getStatus();
        String message = exception.getMessage();
        String detail = exception.getDetail();

        HttpStatus httpStatus = exception.getHttpStatus();

        RestErrorDto errorDto = new RestErrorDto();
        errorDto.setType(status);
        errorDto.setSource(null);
        errorDto.setMessage(message);
        errorDto.setDetail(detail);
        List<RestErrorDto> errorList = new ArrayList<>();
        errorList.add(errorDto);

        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("errors", errorList);
        return new ResponseEntity(map, httpStatus);
    }
}
