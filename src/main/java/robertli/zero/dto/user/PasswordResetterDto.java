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
 *
 * @author Robert Li
 */
public class PasswordResetterDto {

    private String code;
    private String password;
    private String reenterPassword;

    @AssertTrue(message = "Passwords do not match")
    public boolean isMatchPasswords() {
        return password != null && password.equals(reenterPassword);
    }

    @NotBlank
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NotBlank
    @Length(min = 8, max = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank
    public String getReenterPassword() {
        return reenterPassword;
    }

    public void setReenterPassword(String reenterPassword) {
        this.reenterPassword = reenterPassword;
    }

}
