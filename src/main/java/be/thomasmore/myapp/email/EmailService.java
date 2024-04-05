package be.thomasmore.myapp.email;

import jakarta.mail.MessagingException;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;



@Service
public class EmailService {



        @Autowired
        private JavaMailSender emailSender;

        public void sendEmail(String to, String subject, String body) {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            try {
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(body);
                emailSender.send(message);
            } catch (MessagingException e) {

            }
        }
}
