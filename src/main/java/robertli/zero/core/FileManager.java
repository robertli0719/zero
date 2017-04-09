/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

import java.io.IOException;
import java.io.InputStream;

/**
 * This interface is designed for implementing file storage service.<br>
 * The file storage service can be based on S3, EBS, local file system, or any
 * anther storage system which can storage file data. <br>
 * Each file can be identity by UUID. User always need to use new UUID to write
 * new file. User is not allowed to update a file after write. After
 * implementing delete, the file may not be delete at once.
 *
 *
 * @version 1.0.2 2017-04-07
 * @author Robert Li
 */
public interface FileManager {

    /**
     * set the location for storage,such as file path or bucket name
     *
     * @param basePath
     */
    public void setBasePath(String basePath);

    /**
     * get a input stream for the file. If the file is not found, return null.
     *
     * @param uuid the identification of file
     * @param start the range offset
     * @return InputStream for the file
     */
    public InputStream getInputStream(String uuid, long start);

    /**
     * Write a new file to the file system.<br>
     * Do NOT use this function to update an existing file.
     *
     * @param uuid the identification of file
     * @param in the input stream for the data of file
     * @param length the size of the file
     * @throws java.io.IOException when writing failly
     */
    public void write(String uuid, InputStream in, long length) throws IOException;

    /**
     * Delete an existing file.<br>
     *
     * @param uuid the identification of file
     */
    public void delete(String uuid);
}
