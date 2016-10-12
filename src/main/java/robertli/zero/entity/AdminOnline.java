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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.Index;

/**
 * This is a Hibernate entity class which is mapped to a relevant database
 * table<br>
 *
 * Each line of this table is an online administrator. Because one administrator
 * can use different sessionId (different device or browser) to be the login
 * status at the same time, it is possible for one administrator in more than
 * one line.
 *
 * @see Admin
 * @version 1.0 2016-09-21
 * @author Robert Li
 */
@Entity
@Table(name = "admins_online")
public class AdminOnline implements Serializable {

    private String sessionId;
    private Admin admin;
    private Date lastActiveDate;

    @Id
    @Column(length = 32)
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @ManyToOne
    @JoinColumn(nullable = false)
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Index(name = "last_active_date")
    public Date getLastActiveDate() {
        return lastActiveDate;
    }

    public void setLastActiveDate(Date lastActiveDate) {
        this.lastActiveDate = lastActiveDate;
    }

}
