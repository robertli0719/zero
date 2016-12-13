/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller;

import org.springframework.http.HttpStatus;

/**
 * A RuntimeExcption which is handled by GenericRestController
 *
 * @see robertli.zero.controller.GenericRestController
 * @version 2016-12-12 1.0.2
 * @author Robert Li
 */
public class RestException extends RuntimeException {

    private String status;
    private HttpStatus httpStatus;
    private String detail;

    public RestException(String status, String message, String detail, HttpStatus httpStatus) {
        super(message);
        this.status = status;
        this.httpStatus = httpStatus;
        this.detail = detail;
    }

    public RestException(String status, String message, String detail) {
        this(status, message, detail, HttpStatus.FORBIDDEN);
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
