/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dto;

/**
 *
 * @author Robert Li
 */
public class UserProfileDto {

    private String authLabel;
    private String userType;
    private String name;
    private String telephone;

    public String getAuthLabel() {
        return authLabel;
    }

    public void setAuthLabel(String authLabel) {
        this.authLabel = authLabel;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

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

}
