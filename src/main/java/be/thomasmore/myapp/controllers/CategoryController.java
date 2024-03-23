package be.thomasmore.myapp.controllers;

import be.thomasmore.myapp.model.Games;
import be.thomasmore.myapp.repositories.GamesRepository;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class CategoryController {
    @Autowired
    private GamesRepository gamesRepository;

    @GetMapping({"/category"})
    public String showCategoryGames(Model model){

        return "products/category";
    }
    @GetMapping("/listcategory/{categoryName}")
    public String getGamesByCategory(@PathVariable(required = false) String categoryName, Model model) {
        List<Games> games = gamesRepository.findByCategoryIsContainingIgnoreCase(categoryName);
        model.addAttribute("games", games);

        return "/products/listcategory";
    }

}
