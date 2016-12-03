/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This is a Hibernate entity class which is mapped to a relevant database
 * table.<br>
 *
 * Each user can use more than one way to login the system, such as email,
 * telephone, WeChat，GoogleAuth and so on. No matter the way users using, users
 * always type two fields to login. One is authId, and the other is password.
 * When the user use email to login, the userAuthId is the email address. When
 * the user use telephone to login, the userAuthId is the phone number.<br>
 *
 * authId can be email, phone, and any account name for login<br>
 * label is for showing. ex: email address can include point<br>
 *
 * @version 1.02 2016-09-19
 * @author Robert Li
 */
@Entity
@Table(name = "users_auth")
public class UserAuth implements Serializable {

    private String authId;
    private String label;
    private String type;
    private User user;

    @Id
    @Column(length = 50)
    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    @Column(nullable = false, length = 50)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Column(nullable = false, length = 50)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
