/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import robertli.zero.core.FileManager;

/**
 * This class is designed for using AWS S3 to be the file storage solution.
 * Before you using this class, you need to set IAM user in AWS console, and
 * give the IAM user s3 permissions. The file manager will read
 * AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY from system environment variable.
 *
 *
 * @version 1.0.1 2017-02-28
 * @author Robert Li
 */
public class FileManagerS3Impl implements FileManager {

    private final AmazonS3 s3;
    private String bucketName;

    public FileManagerS3Impl() {
        s3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .build();
    }

    @Override
    public void setBasePath(String basePath) {
        this.bucketName = basePath;
    }

    @Override
    public byte[] read(String uuid) {
        byte[] readBuf = null;
        try {
            S3Object o = s3.getObject(bucketName, uuid);
            readBuf = IOUtils.toByteArray(o.getObjectContent());
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return readBuf;
    }

    @Override
    public void write(String uuid, byte[] data) throws IOException {
        InputStream stream = new ByteArrayInputStream(data);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(data.length);
        meta.setContentType("application/x-binary");
        s3.putObject(bucketName, uuid, stream, meta);
    }

    @Override
    public void delete(String uuid) {
        s3.deleteObject(bucketName, uuid);
    }

}
