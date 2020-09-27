package ca.ulaval.glo4003.spamdul.usecases.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GmailEmailService implements EmailService {

  public void send(String to, String subject, String text) {
    try {
      Properties properties = getProperties();
      Session session = getSession(properties);

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(properties.get("mail.from").toString()));
      message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(to));
      message.setSubject(subject);
      message.setText(text);

      Transport.send(message);
    } catch (MessagingException e) {
      throw new EmailException();
    }
  }

  private static Properties getProperties() {
    try (InputStream input = GmailEmailService.class.getClassLoader()
                                                    .getResourceAsStream("mail.properties")) {
      Properties props = new Properties();
      props.load(input);
      return props;
    } catch (IOException e) {
      throw new EmailException();
    }
  }

  private static Session getSession(Properties properties) {
      return Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(properties.getProperty("mail.username"),
                                            properties.getProperty("mail.password"));
        }
      });
  }
}
