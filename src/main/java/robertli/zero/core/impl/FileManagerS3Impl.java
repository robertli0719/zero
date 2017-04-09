/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core.impl;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import java.io.InputStream;
import robertli.zero.core.FileManager;

/**
 * This class is designed for using AWS S3 to be the file storage solution.
 * Before you using this class, you need to set IAM user in AWS console, and
 * give the IAM user s3 permissions. The file manager will read
 * AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY from system environment variable.
 *
 *
 * @version 1.0.2 2017-04-07
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
    public InputStream getInputStream(String uuid, long start) {
        GetObjectRequest gor = new GetObjectRequest(bucketName, uuid);
        gor.setRange(start);
        final S3Object obj = s3.getObject(gor);
        return obj.getObjectContent();
    }

    @Override
    public void write(String uuid, InputStream in, long length) {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(length);
        meta.setContentType("application/x-binary");
        s3.putObject(bucketName, uuid, in, meta);
    }

    @Override
    public void delete(String uuid) {
        s3.deleteObject(bucketName, uuid);
    }

}
