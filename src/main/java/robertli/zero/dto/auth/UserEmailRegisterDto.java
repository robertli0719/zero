/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dto.auth;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @version 1.0 2016-11-23
 * @author Robert Li
 */
public class UserEmailRegisterDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @NotNull
    @Length(min = 8, max = 20)
    private String password;

    @NotBlank
    private String passwordAgain;

    @NotBlank
    private String name;

    @AssertTrue(message = "Two password entries are inconsistent")
    public boolean isPasswordAgainValid() {
        return this.password.equals(this.passwordAgain);
    }

    /**
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return the real password which inputs by user
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return user should type the password twice
     */
    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    /**
     *
     * @return the name which will show on pages after user login
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
