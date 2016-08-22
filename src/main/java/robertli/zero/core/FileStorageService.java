package robertli.zero.core;

/**
 * This interface is designed for implementing file storage service.<br>
 * The file storage service can be based on S3, EBS, local file system, or any
 * anther storage system which can storage file data. <br>
 * Each file can be identity by UUID. User always need to use new UUID to write
 * new file. User is not allowed to update a file after write. After
 * implementing delete, the file may not be delete at once.
 *
 *
 * @version 1.0 2016-08-16
 * @author Robert Li
 */
public interface FileStorageService {

    /**
     * Read a file from the file system. If the file is not found, return null.
     *
     * @param uuid the identification of file
     * @return the file if found. null if not found
     */
    public byte[] read(String uuid);

    /**
     * Write a new file to the file system.<br>
     * Do NOT use this function to update an existing file.
     *
     * @param uuid the identification of file
     * @param data the data of file
     */
    public void write(String uuid, byte[] data);

    /**
     * Delete an existing file.<br>
     *
     * @param uuid the identification of file
     */
    public void delete(String uuid);
}
