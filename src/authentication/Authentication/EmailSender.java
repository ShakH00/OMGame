package authentication.Authentication;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    public static void sendEmail(String toEmail, String code) {
        final String fromEmail = "donotreply@omgame.club";
        final String password = "ellendusk"; // I used app password here for security purposes

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465"); // Use SSL port
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Enable SSL
        props.put("mail.smtp.host", "smtp.migadu.com");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("OMGame: Your Verification Code");
            message.setText("Your verification code is: " + code);
            Transport.send(message);

            System.out.println("Verification code sent to: " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Email failed to send.");
        }
    }
}
