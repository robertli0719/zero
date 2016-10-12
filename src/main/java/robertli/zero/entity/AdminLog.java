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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.Index;

/**
 * This is a Hibernate entity class which is mapped to a relevant database
 * table.<br>
 *
 * Each line of this table is an log record for administrator of the system.
 *
 * @version 1.0 2016-09-21
 * @author Robert Li
 */
@Entity
@Table(name = "admins_log")
public class AdminLog implements Serializable {

    private int id;
    private String type;
    private String username;
    private String comment;
    private Date dateTime;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false, length = 10)
    @Index(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(nullable = false, length = 50)
    @Index(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false, length = 150)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Index(name = "dateTime")
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

}
