package be.thomasmore.myapp.controllers;

import be.thomasmore.myapp.model.Games;
import be.thomasmore.myapp.repositories.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class CategoryController {
    @Autowired
    private GamesRepository gamesRepository;

    @GetMapping({"/category"})
    public String showCategoryGames(Model model, @RequestParam(required = false) String search){

        List<Games> allgames;

        allgames=gamesRepository.findByCategoryIsContainingIgnoreCase("Horror");
        model.addAttribute("horrorgames",allgames);

        allgames=gamesRepository.findByCategoryIsContainingIgnoreCase("Adventure");
        model.addAttribute("adventuregames",allgames);

        allgames=gamesRepository.findByCategoryIsContainingIgnoreCase("Survival");
        model.addAttribute("survivalgames",allgames);

        allgames=gamesRepository.findByCategoryIsContainingIgnoreCase("Action");
        model.addAttribute("actiongames",allgames);

        allgames=gamesRepository.findByCategoryIsContainingIgnoreCase("Shooter");
        model.addAttribute("shootergames",allgames);

        return "products/category";
    }
}
