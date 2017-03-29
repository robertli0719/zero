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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @see Page
 * @version 1.0.1 2017-03-15
 * @author Robert Li
 */
@Entity
@Table(name = "page_image")
public class PageImage implements Serializable {

    private int id;
    private Page page;
    private FileRecord img;

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
    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @OneToOne(orphanRemoval = true)
    public FileRecord getImg() {
        return img;
    }

    public void setImg(FileRecord img) {
        this.img = img;
    }

}
