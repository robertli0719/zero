/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dto.user;

import javax.validation.constraints.AssertTrue;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * For POST /me/auth/password to reset user own password
 *
 * @author Robert Li
 */
public class UserAuthPasswordDto {

    private String oldPassword;
    private String newPassword;
    private String reenterNewPassword;

    @AssertTrue(message = "Passwords do not match")
    public boolean isMatchPasswords() {
        return newPassword != null && newPassword.equals(reenterNewPassword);
    }

    @NotBlank
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @NotBlank
    @Length(min = 8, max = 20)
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @NotBlank
    public String getReenterNewPassword() {
        return reenterNewPassword;
    }

    public void setReenterNewPassword(String reenterNewPassword) {
        this.reenterNewPassword = reenterNewPassword;
    }

}
