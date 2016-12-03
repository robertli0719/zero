/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.io.IOException;
import robertli.zero.entity.FileRecord;

/**
 * This interface is designed for implementing object storage service in
 * transactional way.<br>
 *
 * The object storage service can be based on S3, EBS, local file system, or any
 * other storage system which can storage binary data. <br>
 *
 * Based on CAP theorem, consistency, availability, and partition tolerance can
 * not be implemented within one system. We have to just keep two of them and
 * give up one of them. In this design, we have to keep partition tolerance
 * because we want to use S3, EBS, or local file system with our transactional
 * database together. Also, in my opinion, availability is more important than
 * consistency for most of our web application such as image storage for
 * articles. In conclusion, this service will guarantee availability and
 * partition tolerance, but just guarantee eventual consistency rather than
 * consistency.<br>
 *
 * In other words, all of the public function in this service can join a
 * database transaction, and this service can rollback correctly when the
 * transaction rollback. However,users may not feel the execution result at once
 * after database transaction commit. There could be a delay, and we should make
 * the delay as short as possible<br>
 *
 * Each object can be identity by UUID. Because of CDN and web cache, our system
 * always need to use new UUID to write write new object. User is not allowed to
 * update a object after write. After implementing delete, the object may not be
 * delete at once.<br>
 *
 * The file uploading process should be: 1.register 2.store 3.fix
 *
 * @version 1.02 2016-09-29
 * @author Robert Li
 */
public interface StorageService {

    /**
     * get FileRecord from the storage by id<br>
     *
     * If the record is not found, return null.<br>
     *
     * @param uuid the identifier of the object or file
     * @return FileRecord entity of the ID or null if not found
     */
    public FileRecord getFileRecord(String uuid);

    /**
     * get object from the storage by id<br>
     *
     * If the object is not found, return null.<br>
     *
     * This function can only get the object which is added within the
     * transaction committed before current transaction start.
     *
     * @param uuid the identifier of the object or file
     * @return the binary data of the object or file
     */
    public byte[] get(String uuid);

    /**
     * When a user upload a file to this system, the system should register this
     * file in the database before we store it to the file system. This function
     * will create an UUID to be the identifier of the file.<br>
     *
     * Do NOT executes register and store in same transaction.<br>
     *
     * @see robertli.zero.entity.FileRecord
     * @param name the name of the file or object or file
     * @param type the type of the file or object or file
     * @return UUID, which is the identifier of the object or file
     */
    public String register(String name, String type);

    /**
     * store a new object to file system. we should register the file in
     * database before we store the file to file system.<br>
     *
     * Do NOT executes register and store in same transaction.<br>
     *
     * After store successful, the file will be in temp status. In temp status,
     * users can access it within a period of validity, but the system could
     * clean it if the file is out of date. We can use 'fix' to fix the file
     * from temp status to fixed status.<br>
     *
     * guarantee eventual consistency rather than consistency within a database
     * transaction.
     *
     * @param uuid which is the identifier of the object or file
     * @param data the binary data of the object or file
     * @throws java.io.IOException when store failly
     */
    public void store(String uuid, byte[] data) throws IOException;

    /**
     * After store successful, the file will be in temp status. In temp status,
     * users can access it within a period of validity, but the system could
     * clean it if the file is out of date. We can use 'fix' to fix the file
     * from temp status to fixed status.<br>
     *
     * @param uuid which is the identifier of the object or file
     */
    public void fix(String uuid);

    /**
     * delete the object from the system<br>
     * guarantee eventual consistency rather than consistency within a database
     * transaction.
     *
     * @param uuid the identifier of the object or file
     */
    public void delete(String uuid);

    /**
     * remove the files which should be delete from file system.
     */
    public void clean();
}
