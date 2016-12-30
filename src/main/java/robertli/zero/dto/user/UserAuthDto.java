/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dto.user;

import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @version 1.0.1 2016-12-10
 * @author Robert Li
 */
public class UserAuthDto {

    @NotBlank
    private String userTypeName;
    @NotBlank
    private String userPlatformName;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getUserPlatformName() {
        return userPlatformName;
    }

    public void setUserPlatformName(String userPlatformName) {
        this.userPlatformName = userPlatformName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
