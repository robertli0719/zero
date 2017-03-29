/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is a Hibernate entity class which is mapped to a relevant database
 * table.<br>
 *
 * Each line of this table is a file for StorageService
 *
 * You can only use FileRecordDao to create it, but you can delete fileRecord
 * directly. Deleting fileRecord will set fileRecordToken's reference to null
 * automatically.
 *
 * @see robertli.zero.service.StorageService
 * @see robertli.zero.entity.FileRecordToken
 * @version 1.0.2 2017-03-15
 * @author Robert Li
 */
@Entity
@Table(name = "file_record")
public class FileRecord implements Serializable {

    private String uuid;
    private String url;

    @Id
    @Column(length = 36)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Column(nullable = false, length = 150)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
