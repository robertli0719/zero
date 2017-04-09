/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import robertli.zero.core.FileManager;

/**
 * This interface is designed for implementing file storage service.<br>
 * The file storage service can be based on S3, EBS, local file system, or any
 * anther storage system which can storage file data. <br>
 * Each file can be identity by UUID. User always need to use new UUID to write
 * new file. User is not allowed to update a file after write. After
 * implementing delete, the file may not be delete at once.
 *
 * @version 1.0.3 2017-04-07
 * @author Robert Li
 */
public final class FileManagerImpl implements FileManager {

    private final int BUFFER_SIZE = 1 * 1024 * 1024;
    private String basePath;

    @Override
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public InputStream getInputStream(String uuid, long start) {
        final String path = basePath + "/" + uuid;
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
            in.skipBytes((int) start);
            return in;
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            Logger.getLogger(FileManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void write(String uuid, InputStream in, long length) throws IOException {
        final String path = basePath + "/" + uuid;
        final byte[] buffer = new byte[BUFFER_SIZE];
        int len;
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            while ((len = in.read(buffer)) >= 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } finally {
            in.close();
        }
    }

    @Override
    public void delete(String uuid) {
        String path = basePath + "/" + uuid;
        File fe = new File(path);
        if (fe.isFile() == false) {
            return;
        }
        fe.delete();
    }

}
