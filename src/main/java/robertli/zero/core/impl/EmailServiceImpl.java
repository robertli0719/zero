/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import robertli.zero.core.EmailService;

/**
 * Web site can use this service to send email to users.<br>
 *
 * reference:<br>
 * http://www.oracle.com/technetwork/java/javamail/faq-135477.html
 *
 * @version 1.0.1 2016-08-22
 * @author Robert Li
 */
public class EmailServiceImpl implements EmailService {

    private final String HOST;
    private final String FROM;
    private final String USERNAME;
    private final String PASSWORD;
    private final int PORT;
    private final boolean DEBUG;
    private final Session session;

    public EmailServiceImpl(EmailConfiguration cfg) {
        HOST = cfg.getHost();
        FROM = cfg.getFrom();
        USERNAME = cfg.getUsername();
        PASSWORD = cfg.getPassword();
        PORT = cfg.getPort();
        DEBUG = cfg.isDebug();
        session = getSession();
        session.setDebug(DEBUG);
    }

    private Session getSession() {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
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
        boolean fail = false;
        String subject = emailMessage.getSubject();
        String content = emailMessage.getContent();
        MimeMessage msg = new MimeMessage(session);

        Transport transport = null;
        try {
            msg.setFrom(new InternetAddress(FROM));
            InternetAddress recipientList[] = getRecipientList(emailMessage);
            msg.setRecipients(Message.RecipientType.TO, recipientList);
            msg.setSubject(subject);
            msg.setContent(content, "text/html;charset=UTF-8");
            transport = session.getTransport();
            transport.connect(HOST, USERNAME, PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            Logger.getLogger(EmailServiceImpl.class.getName()).log(Level.INFO, "SMTP send out:{0}", emailMessage.getUUID());
        } catch (Exception ex) {
            Logger.getLogger(EmailServiceImpl.class.getName()).log(Level.SEVERE, "SMTP send fail", ex);
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
        new Thread() {
            @Override
            public void run() {
                sendWithBlocking(emailMessage);
            }
        }.start();
    }

    @Override
    public void send(EmailMessage emailMessage, SenderCallbacker callback) {
        new Thread() {
            @Override
            public void run() {
                boolean fail = sendWithBlocking(emailMessage);
                callback.processResult(emailMessage, fail);
            }
        }.start();
    }

}
