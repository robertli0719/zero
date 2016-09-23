package robertli.zero.service;

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
 * delete at once.
 *
 *
 * @version 1.0 2016-09-22
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
     * store a new object to the system. The system will create an UUID to be
     * the identifier for the object<br>
     * guarantee eventual consistency rather than consistency within a database
     * transaction.
     *
     * @param name the name of the file or object or file
     * @param type the type of the file or object or file
     * @param data the binary data of the object or file
     * @return UUID, which is the identifier of the object or file
     */
    public String add(String name, String type, byte[] data);

    /**
     * delete the object from the system<br>
     * guarantee eventual consistency rather than consistency within a database
     * transaction.
     *
     * @param uuid the identifier of the object or file
     */
    public void delete(String uuid);
}
