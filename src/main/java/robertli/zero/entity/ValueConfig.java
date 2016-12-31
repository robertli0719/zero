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
import javax.persistence.Index;
import javax.persistence.Table;

/**
 *
 * @version 1.0.2 2016-12-30
 * @author Robert Li
 */
@Entity
@Table(name = "value_config", indexes = {
    @Index(name = "combined_index", columnList = "namespace,pageName,name")
})
public class ValueConfig implements Serializable {

    private int id;
    private String namespace;
    private String pageName;
    private String name;
    private String val;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false, length = 50)
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Column(nullable = false, length = 50)
    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    @Column(nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false, length = 190)
    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

}
