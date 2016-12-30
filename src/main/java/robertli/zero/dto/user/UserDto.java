/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dto.user;

import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotBlank;

/**
 * we use this dto to add or delete user in system. when client get the dto, the
 * password will be protected by MD5+salt<br>
 *
 * A user may has more than one usernames, but we only include first one in DTO.
 *
 * @version 1.0.2 2016-12-29
 * @author Robert Li
 */
public class UserDto {

    private Integer id;
    private String userPlatformName;
    private String password;
    private String name;
    private String telephone;
    private boolean locked;
    private String username;
    private String usernameType;
    private String label;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotBlank
    public String getUserPlatformName() {
        return userPlatformName;
    }

    public void setUserPlatformName(String userPlatformName) {
        this.userPlatformName = userPlatformName;
    }

    @NotBlank
    @Min(8)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @NotBlank
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank
    public String getUsernameType() {
        return usernameType;
    }

    public void setUsernameType(String usernameType) {
        this.usernameType = usernameType;
    }

    @NotBlank
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
