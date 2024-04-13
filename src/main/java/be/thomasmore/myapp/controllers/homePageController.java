package be.thomasmore.myapp.controllers;

import be.thomasmore.myapp.model.Games;
import be.thomasmore.myapp.model.Ratings;
import be.thomasmore.myapp.repositories.GamesRepository;
import be.thomasmore.myapp.repositories.RatingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class homePageController {

    @Autowired
    private GamesRepository gamesRepository;
    @Autowired
    private RatingsRepository ratingsRepository;
    @GetMapping("/")
    public String indexListFilter(Model model,
                                  @RequestParam(required = false) Integer minPrice,
                                  @RequestParam(required = false) Integer maxPrice,
                                  @RequestParam(required = false)  List<String> category,
                                  @RequestParam(required = false)  List<String>console,
                                  @RequestParam(required = false, defaultValue = "price_asc") String orderBy){

        Iterable<Games> allGames;

        if (minPrice != null || maxPrice != null || category != null || console != null) {
            List<Games> filteredGames;
            if (orderBy != null ) {
                filteredGames = gamesRepository.findByFilter(minPrice, maxPrice, category, console, orderBy);
            } else {
                filteredGames = gamesRepository.findByFilter(minPrice, maxPrice, category, console, "price_asc");
            }
            model.addAttribute("allespellen", filteredGames);
            long count = filteredGames.size();
            model.addAttribute("count", count);
        } else {
            allGames = gamesRepository.findAll();
            model.addAttribute("allespellen", allGames);
            long count = allGames.spliterator().estimateSize();
            model.addAttribute("count", count);
        }
        allGames = gamesRepository.findAll();



    Map<Integer, Double> avgRatingsMap = new HashMap<>();
    for (Games game : allGames) {
        double avgRating = calculateAverageRating(game);
        avgRatingsMap.put(game.getId(), avgRating);
    }


    model.addAttribute("avgRatingsMap", avgRatingsMap);

        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("console", console);
        model.addAttribute("category", category);

        return "index";
    }

    @GetMapping("/products/addratings/{id}")
    public String addRatingsForm(Model model, @PathVariable(required = false) int id) {
        Optional<Games>optionalGames=gamesRepository.findById(id);

        if(optionalGames.isPresent()) {
            Ratings ratings = new Ratings();
            model.addAttribute("ratings", ratings);
        }
        return "products/addratings";
    }

    @PostMapping("/products/addratings/{id}")
    public String addRatings(@PathVariable int id, @ModelAttribute("ratings") Ratings formratings) {
        Optional<Games> gamesOptional = gamesRepository.findById(id);

        if (gamesOptional.isPresent()) {
            Games game = gamesOptional.get();

            Ratings ratings= new Ratings();

            ratings.setRating(formratings.getRating());


            game.getRatings().add(ratings);
            gamesRepository.save(game);


            ratings.getGames().add(game);
            ratingsRepository.save(ratings);
        }
        return "products/addratings";
    }
       private double calculateAverageRating(Games game) {
           return calculate(game);
       }

    static double calculate(Games game) {
        Collection<Ratings> ratings = game.getRatings();
        double totalScore = 0.0;
        int numberOfRatings = ratings.size();

        for (Ratings rating : ratings) {
            totalScore += rating.getRating();
        }

        double averageRating = numberOfRatings > 0 ? totalScore / numberOfRatings : 0.0;

        return Math.round(averageRating * 10.0) / 10.0;
    }



}

