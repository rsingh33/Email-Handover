package com.citi.isg.omc.handover;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


public class SendEmail {

    void emailSend(String content) {

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        Date date = new Date();

        Properties props = new Properties();

        props.put("mail.smtp.host", Constants.smtpHost);
        props.put("mail.smtp.port", Constants.smtpPort);
        props.put("mail.smtp.auth", Constants.smtpAuth);
        props.put("mail.smtp.starttls.enable", Constants.smtpTLS);

        Session session = Session.getInstance(props, new Authenticator() {
            // Set the account information session，transport will send mail
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constants.setFrom, Constants.setPassword);
            }
        });
        session.setDebug(true);

        try {

            String[] tolist = Constants.emailTO.split(";");
            InternetAddress[] address;
            address = new InternetAddress[tolist.length];
            for (int i = 0; i < tolist.length; i++) {
                InternetAddress addresses = new InternetAddress(tolist[i]);
                address[i] = addresses;
            }

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Constants.setFrom));
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(Constants.emailSubject + "    "+date);
            message.setContent(content, "text/html; charset=utf-8");
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}