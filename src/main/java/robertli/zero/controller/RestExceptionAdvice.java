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
import robertli.zero.dto.RestErrorItemDto;

/**
 * This advice will be using for mapping exceptions to JSON response.
 *
 * @version 2016-12-12 1.0
 * @author Robert Li
 */
@RestController
@ControllerAdvice
public class RestExceptionAdvice {

    private void appendFieldErrors(List<RestErrorItemDto> errorList, BindingResult result) {
        //getFieldErrors return errors in rand order, so we have to keep the 'big' one
        Map<String, String> messageMap = new HashMap();
        for (FieldError error : result.getFieldErrors()) {
            String name = error.getField();
            String message = error.getDefaultMessage();
            if (messageMap.containsKey(name) && messageMap.get(name).compareTo(message) <= 0) {
                continue;
            }
            messageMap.put(name, message);
            RestErrorItemDto errorItemDto = new RestErrorItemDto();
            errorItemDto.setType("FIELD_ERROR");
            errorItemDto.setSource(name);
            errorItemDto.setMessage(message);
            errorItemDto.setDetail("BindingResult gets validation errors");
            errorList.add(errorItemDto);
        }
    }

    private void appendGlobalErrors(List<RestErrorItemDto> errorList, BindingResult result) {
        for (ObjectError error : result.getGlobalErrors()) {
            RestErrorItemDto errorItemDto = new RestErrorItemDto();
            errorItemDto.setType("GLOBAL_ERROR");
            errorItemDto.setSource(error.getObjectName());
            errorItemDto.setMessage(error.getDefaultMessage());
            errorItemDto.setDetail("BindingResult gets validation errors");
            errorList.add(errorItemDto);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public RestErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<RestErrorItemDto> errorList = new ArrayList<>();
        appendFieldErrors(errorList, bindingResult);
        appendGlobalErrors(errorList, bindingResult);

        RestErrorDto restError = new RestErrorDto();
        restError.setStatus("INVALID_REQUEST");
        restError.setErrors(errorList);
        return restError;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorDto handleRuntimeException(RuntimeException exception) {
        RestErrorItemDto errorItemDto = new RestErrorItemDto();
        errorItemDto.setType("RUNTIME_EXCEPTION");
        errorItemDto.setSource(null);
        errorItemDto.setMessage("There are some errors in server.");
        errorItemDto.setDetail(exception.getMessage());
        List<RestErrorItemDto> errorList = new ArrayList<>();
        errorList.add(errorItemDto);

        RestErrorDto restError = new RestErrorDto();
        restError.setStatus("RUNTIME_EXCEPTION");
        restError.setErrors(errorList);
        return restError;
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity handleRestException(RestException exception) {
        String status = exception.getStatus();
        String message = exception.getMessage();
        String detail = exception.getDetail();

        HttpStatus httpStatus = exception.getHttpStatus();

        RestErrorItemDto errorItemDto = new RestErrorItemDto();
        errorItemDto.setType(status);
        errorItemDto.setSource(null);
        errorItemDto.setMessage(message);
        errorItemDto.setDetail(detail);
        List<RestErrorItemDto> errorList = new ArrayList<>();
        errorList.add(errorItemDto);

        RestErrorDto restError = new RestErrorDto();
        restError.setStatus(status);
        restError.setErrors(errorList);

        return new ResponseEntity(restError, httpStatus);
    }
}
