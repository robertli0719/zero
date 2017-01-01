/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dto.user;

/**
 *
 * @version 1.0.1 2016-12-31
 * @author Robert Li
 */
public class StaffUserDto {

    private Integer id;
    private String username;
    private String password;
    private String userPlatformName;
    private boolean locked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUserPlatformName() {
        return userPlatformName;
    }

    public void setUserPlatformName(String userPlatformName) {
        this.userPlatformName = userPlatformName;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

}
