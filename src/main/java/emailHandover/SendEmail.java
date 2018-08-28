package emailHandover;


import java.sql.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendEmail {

    void emailSend(String content) {


        Properties props = new Properties();
        props.put("mail.smtp.auth", Constants.smtpAuth);
        props.put("mail.smtp.starttls.enable", Constants.smtpTLS);
        props.put("mail.smtp.host", Constants.smtpHost);
        props.put("mail.smtp.port", Constants.smtpPort);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Constants.setFrom, Constants.setPassword);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Constants.setFrom));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(Constants.emailTO));
            message.setSubject(Constants.emailSubject);
            message.setContent(content, "text/html; charset=utf-8");
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}