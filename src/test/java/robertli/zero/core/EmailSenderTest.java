/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Robert Li
 */
public class EmailSenderTest {

    private static void test() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        EmailSender emailSender = (EmailSender) context.getBean("emailSender");

        emailSender.send(new EmailMessage() {
            @Override
            public String getUUID() {
                return UUID.randomUUID().toString();
            }

            @Override
            public List<String> getEmailList() {
                List<String> emailList = new ArrayList<>();
                emailList.add("382332726@qq.com");
                emailList.add("li.liufv@gmail.com");
                return emailList;
            }

            @Override
            public String getSubject() {
                return "Zero Project EmailSender Test";
            }

            @Override
            public String getContent() {
                return "Zero Project EmailSender is testing.";
            }
        });

    }

    public static void main(String args[]) throws Exception {
        test();
    }
}
