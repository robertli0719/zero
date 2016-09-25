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
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This is a Hibernate entity class which is mapped to a relevant database
 * table.<br>
 *
 * Each line of this table is an administrator of the system.
 *
 * @version 1.0 2016-09-21
 * @author Robert Li
 */
@Entity
@Table(name = "admins")
public class Admin implements Serializable {

    private String username;
    private String password;
    private String passwordSalt;
    private boolean suspended;
    private List<AdminPermission> adminPermissionList;

    @Id
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    @OneToMany(mappedBy = "admin")
    public List<AdminPermission> getAdminPermissionList() {
        return adminPermissionList;
    }

    public void setAdminPermissionList(List<AdminPermission> adminPermissionList) {
        this.adminPermissionList = adminPermissionList;
    }

}
