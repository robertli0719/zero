/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Index;

/**
 * This is a Hibernate entity class which is mapped to a relevant database
 * table.<br>
 *
 * Each line of this table is an permission for an administrator of the system.
 *
 * @version 1.0 2016-09-21
 * @author Robert Li
 */
@Entity
@Table(name = "admins_permission")
@org.hibernate.annotations.Table(appliesTo = "admins_permission", indexes = {
    @Index(name = "combine_index", columnNames = {"admin_username", "name"})
})
public class AdminPermission implements Serializable {

    private int id;
    private Admin admin;
    private String name;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(nullable = false)
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Column(nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
