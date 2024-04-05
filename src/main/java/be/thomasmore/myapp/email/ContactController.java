package be.thomasmore.myapp.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam("fullname") String fullname,
                            @RequestParam("email") String email,
                            @RequestParam("phone") String phone,
                            @RequestParam("message") String message) {
        String subject = "Contact Form Submission";
        String body = "Full Name: " + fullname + "\n"
                + "Email: " + email + "\n"
                + "Phone: " + phone + "\n\n"
                + "Message:\n" + message;

        emailService.sendEmail("akriekske1@gmail.com", subject, body);

        return "redirect:/products/contact";
    }
}