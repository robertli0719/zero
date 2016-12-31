/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dto.user;

import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @version 1.0 2016-12-30
 * @author Robert Li
 */
public class UserRoleDto {

    private String name;

    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
