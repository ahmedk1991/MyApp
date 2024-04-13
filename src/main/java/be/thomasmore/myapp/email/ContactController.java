package be.thomasmore.myapp.email;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public ModelAndView sendEmail(@RequestParam("fullname") String fullname,
                                  @RequestParam("email") String email,
                                  @RequestParam("phone") String phone,
                                  @RequestParam("message") String message) {
        String subject = "Contact Form Submission";
        String body = "Full Name: " + fullname + "\n"
                + "Email: " + email + "\n"
                + "Phone: " + phone + "\n\n"
                + "Message:\n" + message;


        emailService.sendMessage("akriekske1@gmail.com", subject, body);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("products/contact");
        return modelAndView;
    }
}
