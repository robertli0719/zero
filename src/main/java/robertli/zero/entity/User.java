/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This is a Hibernate entity class which is mapped to a relevant database
 * table.<br>
 *
 * Each line of this table is a signed user.
 *
 * @version 1.0.4 2016-12-29
 * @author Robert Li
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    private int id;
    private String uid;
    private UserPlatform userPlatform;
    private String password;
    private String passwordSalt;
    private String name;
    private boolean locked;
    private List<UserAuth> userAuthList;
    private List<UserRoleItem> userRoleItemList;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false, length = 50, unique = true)
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @ManyToOne
    @JoinColumn(nullable = false)
    public UserPlatform getUserPlatform() {
        return userPlatform;
    }

    public void setUserPlatform(UserPlatform userPlatform) {
        this.userPlatform = userPlatform;
    }

    @Column(nullable = false, length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false, length = 50)
    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @Column(nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @OneToMany(mappedBy = "user")
    public List<UserAuth> getUserAuthList() {
        return userAuthList;
    }

    public void setUserAuthList(List<UserAuth> userAuthList) {
        this.userAuthList = userAuthList;
    }

    @OneToMany(mappedBy = "user")
    public List<UserRoleItem> getUserRoleItemList() {
        return userRoleItemList;
    }

    public void setUserRoleItemList(List<UserRoleItem> userRoleItemList) {
        this.userRoleItemList = userRoleItemList;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userPlatform=" + userPlatform + ", password=" + password + ", passwordSalt=" + passwordSalt + ", name=" + name + '}';
    }

}
