package authentication;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MFAAuthenticationV2 {
    public static void sendEmail(String toEmail, String code) {
        final String fromEmail = "donotreply@omgame.club";
        final String password = "";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

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
            System.err.println("Failed to send email");
        }

    }
}
