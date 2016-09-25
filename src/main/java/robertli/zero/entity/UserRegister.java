/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.Index;

/**
 * This is a Hibernate entity class which is mapped to a relevant database
 * table<br>
 *
 * Each line is a user who has submit sign up application but has not be
 * validated.
 *
 * @comment authLabel is for showing authId. ex: email address can include point
 * @version 1.02 2016-09-19
 * @author Robert Li
 */
@Entity
@Table(name = "users_register")
public class UserRegister implements Serializable {

    private String authId;
    private String authLabel;
    private String authType;
    private String name;
    private String password;
    private String passwordSalt;
    private String verifiedCode;
    private Date signDate;

    @Id
    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    @Column(nullable = false)
    public String getAuthLabel() {
        return authLabel;
    }

    public void setAuthLabel(String authLabel) {
        this.authLabel = authLabel;
    }

    @Column(nullable = false)
    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false)
    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Index(name = "sign_date")
    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    @Column(nullable = false, unique = true)
    @Index(name = "verified_code")
    public String getVerifiedCode() {
        return verifiedCode;
    }

    public void setVerifiedCode(String verifiedCode) {
        this.verifiedCode = verifiedCode;
    }

}
