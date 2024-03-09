package be.thomasmore.myapp.controllers;

import be.thomasmore.myapp.model.Games;
import be.thomasmore.myapp.repositories.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GamesController {
    @Autowired
    private GamesRepository gamesRepository;
@GetMapping("/products")
    public String showGamesList(Model model) {

        Iterable<Games> allgames = gamesRepository.findAll();
        model.addAttribute("allgames", allgames);

        return "products/index";
    }

}
