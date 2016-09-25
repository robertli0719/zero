/*
 * The MIT License
 *
 * Copyright 2016 Robert Li.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package robertli.zero.core.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import robertli.zero.core.FileManager;

/**
 * This interface is designed for implementing file storage service.<br>
 * The file storage service can be based on S3, EBS, local file system, or any
 * anther storage system which can storage file data. <br>
 * Each file can be identity by UUID. User always need to use new UUID to write
 * new file. User is not allowed to update a file after write. After
 * implementing delete, the file may not be delete at once.
 *
 * @version 1.0 2016-09-17
 * @author Robert Li
 */
public final class FileManagerImpl implements FileManager {

    private String basePath;

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
    
    @Override
    public byte[] read(String uuid) {
        String path = basePath + "/" + uuid;
        byte[] result = null;
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
            int size = in.available();
            result = new byte[size];
            in.read(result);
            in.close();
        } catch (IOException ex) {
            result = null;
            System.out.println("Can't found file uuid:" + uuid);
        }
        return result;
    }

    @Override
    public void write(String uuid, byte[] data) throws IOException {
        String path = basePath + "/" + uuid;
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
        out.write(data);
        out.flush();
        out.close();
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
