/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dto;

import java.util.List;

/**
 *
 * @version 2016-12-22 1.0
 * @author Robert Li
 */
public class RestErrorDto {

    private String status;
    private List<RestErrorItemDto> errors;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RestErrorItemDto> getErrors() {
        return errors;
    }

    public void setErrors(List<RestErrorItemDto> errors) {
        this.errors = errors;
    }
    
    
}
