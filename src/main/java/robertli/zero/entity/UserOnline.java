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
 * Each line of this table is an online user. Because one user can use different
 * sessionId (different device or browser) to be the login status at the same
 * time, it is possible for one user in more than one line.
 *
 * @version 1.02 2016-09-19
 * @author Robert Li
 */
@Entity
@Table(name = "users_online")
public class UserOnline implements Serializable {

    private String sessionId;
    private User user;
    private Date lastActiveDate;

    @Id
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @ManyToOne
    @JoinColumn(nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
