package be.thomasmore.myapp.controllers;

import be.thomasmore.myapp.repositories.GamesRepository;
import be.thomasmore.myapp.repositories.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class CommunityController {
    @Autowired
    private GamesRepository gamesRepository;
    @Autowired
    private ReviewsRepository reviewsRepository;

    @GetMapping({"/community"})
    public String showCommunity(Model model){


        return"products/community";
    }
}
