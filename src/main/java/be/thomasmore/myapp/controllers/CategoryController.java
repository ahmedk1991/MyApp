package be.thomasmore.myapp.controllers;

import be.thomasmore.myapp.model.Games;
import be.thomasmore.myapp.model.Reviews;
import be.thomasmore.myapp.repositories.GamesRepository;
import be.thomasmore.myapp.repositories.ReviewsRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Controller
@RequestMapping("/products")
public class CategoryController {
    @Autowired
    private GamesRepository gamesRepository;
    @Autowired
    private ReviewsRepository reviewsRepository;

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
    @GetMapping("/gamedetails/{id}")
    public String getDetailsGame(@PathVariable(required = false)Integer id,Model model){
        if(id==null) return "products/gamedetails";
        Optional<Games> game=gamesRepository.findById(id);




        long count = game.get().getReviews().spliterator().estimateSize();

        Reviews reviews = new Reviews();
        model.addAttribute("count",count);
        model.addAttribute("game", game);
        model.addAttribute("reviews",reviews);

        return "products/gamedetails";
    }
    @PostMapping("/gamedetails/{id}")
    public String addComment(@Valid @ModelAttribute("reviews") Reviews reviews, BindingResult result, @PathVariable(required = false) Integer id, Model model) {
        if (result.hasErrors()) {

            Optional<Games> gameOptional = gamesRepository.findById(id);
            gameOptional.ifPresent(game -> model.addAttribute("game", game));
            return "products/gamedetails";
        }

        Optional<Games> gameOptional = gamesRepository.findById(id);
        if (!gameOptional.isPresent()) {

            return "redirect:/products/category";
        }

      
        reviews.setName(reviews.getName());
        reviews.setDate(new Date());
        reviews.setReview(reviews.getReview());
        reviewsRepository.save(reviews);



        return "redirect:/products/gamedetails/" + id;
    }
}
