/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dto.user;

/**
 * after user loged in, the font-end system will keep this dto
 *
 * @version 1.0.2 2016-12-29
 * @author Robert Li
 */
public class UserProfileDto {

    private String authLabel;
    private String userTypeName;
    private String userPlatformName;
    private String name;
    private String telephone;

    public String getAuthLabel() {
        return authLabel;
    }

    public void setAuthLabel(String authLabel) {
        this.authLabel = authLabel;
    }

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
