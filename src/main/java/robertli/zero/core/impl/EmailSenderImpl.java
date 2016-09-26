/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core.impl;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import robertli.zero.core.EmailMessage;
import robertli.zero.core.EmailSender;

/**
 * Web site can use this service to send email to users.<br>
 *
 * reference:<br>
 * http://www.oracle.com/technetwork/java/javamail/faq-135477.html
 *
 * @version 1.0.3 2016-09-25
 * @author Robert Li
 */
public final class EmailSenderImpl implements EmailSender {

    private String host;
    private String from;
    private String username;
    private String password;
    private int port;
    private boolean test;

    public void setHost(String host) {
        this.host = host;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    private Session getSession() {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return Session.getInstance(props);
    }

    private InternetAddress[] getRecipientList(EmailMessage emailMessage) throws AddressException {
        List<String> emailList = emailMessage.getEmailList();
        InternetAddress arr[] = new InternetAddress[emailList.size()];
        for (int i = 0; i < emailList.size(); i++) {
            arr[i] = new InternetAddress(emailList.get(i));
        }
        return arr;
    }

    @Override
    public boolean sendWithBlocking(EmailMessage emailMessage) {
        if (test) {
            System.out.println("EmailSenderImpl is in test model.");
            return false;
        }
        boolean fail = false;
        String subject = emailMessage.getSubject();
        String content = emailMessage.getContent();
        MimeMessage msg = new MimeMessage(getSession());

        Transport transport = null;
        try {
            msg.setFrom(new InternetAddress(from));
            InternetAddress recipientList[] = getRecipientList(emailMessage);
            msg.setRecipients(Message.RecipientType.TO, recipientList);
            msg.setSubject(subject);
            msg.setContent(content, "text/html;charset=UTF-8");
            transport = getSession().getTransport();
            transport.connect(host, username, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            Logger.getLogger(EmailSenderImpl.class.getName()).log(Level.INFO, "SMTP send out:{0}", emailMessage.getUUID());
        } catch (Exception ex) {
            Logger.getLogger(EmailSenderImpl.class.getName()).log(Level.SEVERE, "SMTP send fail", ex);
            fail = true;
        } finally {
            try {
                if (transport != null) {
                    transport.close();
                }
            } catch (MessagingException ex) {/*ignore*/
            }
        }
        return fail;
    }

    @Override
    public void send(EmailMessage emailMessage) {
        if (test) {
            System.out.println("EmailSenderImpl is in test model.");
            return;
        }
        new Thread() {
            @Override
            public void run() {
                sendWithBlocking(emailMessage);
            }
        }.start();
    }

    @Override
    public void send(EmailMessage emailMessage, SenderCallbacker callback) {
        if (test) {
            System.out.println("EmailSenderImpl is in test model.");
            return;
        }
        new Thread() {
            @Override
            public void run() {
                boolean fail = sendWithBlocking(emailMessage);
                callback.processResult(emailMessage, fail);
            }
        }.start();
    }

}
