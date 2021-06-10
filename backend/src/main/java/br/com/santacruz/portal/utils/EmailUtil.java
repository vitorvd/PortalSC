package br.com.santacruz.portal.utils;

import br.com.santacruz.portal.modelo.User;
import br.com.santacruz.portal.security.EmailSecurity;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {

    public static void sendMail(User user) {
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("vitordversiani@gmail.com", EmailSecurity.EMAIL_PASSWORD);
            }
        });

        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress("GrupoSC"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

            message.setSubject("[GrupoSC] Dados para autenticação.");

            message.setContent(
                    "<h1>GrupoSC - Login</h1>" +
                            "<span>Seu Login: </span>" + user.getLogin() +
                            "<br/><span>Sua Senha: teste</span>",
                    "text/html");

            Transport.send(message);
            System.out.println("Mail sent.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
