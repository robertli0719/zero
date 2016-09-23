/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * This is a Hibernate entity class which is mapped to a relevant database
 * table.<br>
 *
 * Before we write the file to other file system, the file content should be
 * temporarily stored in this table.<br>
 *
 * The content size is 16777215, so that MySQL database will use medium blob for
 * the field type. The max file size should be 16M - 1 byte.
 *
 * @see robertli.zero.service.StorageService
 * @version 1.0 2016-9-22
 * @author Robert Li
 */
@Entity
@Table(name = "file_record_cache")
public class FileRecordCache implements Serializable {

    private FileRecord file;
    private byte[] content;

    @Id
    @OneToOne
    public FileRecord getFile() {
        return file;
    }

    public void setFile(FileRecord file) {
        this.file = file;
    }

    @Column(unique = false, nullable = false, length = 16777215)
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

}
