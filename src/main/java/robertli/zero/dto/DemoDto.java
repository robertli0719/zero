/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dto;

import java.util.Date;
import javax.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 *
 * @author Robert Li
 */
public class DemoDto {

    private int id;
    private String name;
    private Date dateTime;

    @Range(min = 0, max = 30)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Length(min = 3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Past
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

}
