/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * This is a page in the web site, such as about page. The administrators can
 * create,delete, and edit the page.
 *
 * @see PageImage
 * @version 1.0 2016-10-02
 * @author Robert Li
 */
@Entity
@Table(name = "page")
public class Page implements Serializable {

    private int id;
    private PageCategory category;
    private String title;
    private String description;
    private String content;
    private Date createTime;
    private Date lastModifyTime;
    private boolean opened;
    private List<PageImage> pageImageList;

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
    public PageCategory getCategory() {
        return category;
    }

    public void setCategory(PageCategory category) {
        this.category = category;
    }

    @Column(nullable = false, length = 150)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false, length = 190)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false, length = 65535, columnDefinition = "Text")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    @OneToMany(mappedBy = "page")
    public List<PageImage> getPageImageList() {
        return pageImageList;
    }

    public void setPageImageList(List<PageImage> pageImageList) {
        this.pageImageList = pageImageList;
    }

}
