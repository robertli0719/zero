/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package temp;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Robert Li
 */
public class MyTest {

    public static void main(String[] args) {
        AmazonSNSClient snsClient = (AmazonSNSClient) AmazonSNSClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .build();

        String message = "My SMS message2";
        String phoneNumber = "+16043000134";

        Map<String, MessageAttributeValue> smsAttributes = new HashMap<>();
//        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
//                .withStringValue("mySenderID") //The sender ID shown on the device.
//                .withDataType("String"));
//        smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
//                .withStringValue("0.50") //Sets the max price to 0.50 USD.
//                .withDataType("Number"));
//        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
//                .withStringValue("Promotional") //Sets the type to promotional.
//                .withDataType("String"));

        //<set SMS attributes>
        sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);
    }

    public static void sendSMSMessage(AmazonSNSClient snsClient, String message, String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
        System.out.println(result); // Prints the message ID.

    }
}
