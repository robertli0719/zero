/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero;

/**
 *
 * @author Robert Li
 */
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.util.List;
import java.util.UUID;

public class S3Test {

    private final static String BUCKET_NAME = "sandbox.checkshop";
    private final static String FILE_PATH = "C:\\Users\\liliu\\Desktop\\img\\b.jpg";

    public static void main(String args[]) {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .build();

        try {
            String key = UUID.randomUUID().toString();
            s3.putObject(BUCKET_NAME, key, FILE_PATH);
            PutObjectRequest request;
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }

        ObjectListing ol = s3.listObjects(BUCKET_NAME);
        List<S3ObjectSummary> objects = ol.getObjectSummaries();
        for (S3ObjectSummary os : objects) {
            System.out.println("* " + os.getKey());
        }
    }
}
