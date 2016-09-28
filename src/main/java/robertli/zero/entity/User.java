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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This is a Hibernate entity class which is mapped to a relevant database
 * table.<br>
 *
 * Each line of this table is a signed user.
 *
 * @version 1.02 2016-09-19
 * @author Robert Li
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    private int id;
    private String password;
    private String passwordSalt;
    private String name;
    private String telephone;
    private List<UserAuth> userAuthList;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @OneToMany(mappedBy = "user")
    public List<UserAuth> getUserAuthList() {
        return userAuthList;
    }

    public void setUserAuthList(List<UserAuth> userAuthList) {
        this.userAuthList = userAuthList;
    }

}
