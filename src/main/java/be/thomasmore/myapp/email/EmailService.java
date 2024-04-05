package be.thomasmore.myapp.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class EmailService {


    @Autowired
    private EmailService emailService;

    void sendEmail(String to, String subject, String body) {

    }
}
