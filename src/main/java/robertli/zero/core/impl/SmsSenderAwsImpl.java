/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core.impl;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import java.util.HashMap;
import java.util.Map;
import robertli.zero.core.SmsSender;

/**
 * This class is designed for using AWS SNS to be the SMS solution. Before you
 * using this class, you need to set IAM user in AWS console, and give the IAM
 * user SNS permissions. The file manager will read AWS_ACCESS_KEY_ID and
 * AWS_SECRET_ACCESS_KEY from system environment variable.
 *
 *
 * @version 1.0.0 2017-06-01
 * @author Robert Li
 */
public class SmsSenderAwsImpl implements SmsSender {

    private final AmazonSNSClient snsClient;
    private final Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();

    public SmsSenderAwsImpl() {
        snsClient = (AmazonSNSClient) AmazonSNSClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .build();
    }

    @Override
    public void send(String phoneNumber, String message) {
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
        System.out.println(result); // Prints the message ID.
    }

}
