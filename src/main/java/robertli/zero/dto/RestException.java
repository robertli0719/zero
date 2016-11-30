/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dto;

import org.springframework.http.HttpStatus;

/**
 * A RuntimeExcption which is handled by GenericRestController
 *
 * @see robertli.zero.controller.GenericRestController
 * @version 2016-11-23 1.0
 * @author Robert Li
 */
public class RestException extends RuntimeException {

    private String status;
    private HttpStatus httpStatus;

    public RestException(String status, HttpStatus httpStatus) {
        this.status = status;
        this.httpStatus = httpStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
