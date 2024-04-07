package be.thomasmore.myapp.controllers;

import be.thomasmore.myapp.model.Games;
import be.thomasmore.myapp.model.Ratings;
import be.thomasmore.myapp.repositories.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class homePageController {

    @Autowired
    private GamesRepository gamesRepository;

    @GetMapping("/")
    public String indexListFilter(Model model,
                                  @RequestParam(required = false) Integer minPrice,
                                  @RequestParam(required = false) Integer maxPrice,
                                  @RequestParam(required = false) String category,
                                  @RequestParam(required = false) String console,
                                  @RequestParam(required = false, defaultValue = "price_asc") String orderBy) {

        Iterable<Games> allGames;

        if (minPrice != null || maxPrice != null || category != null || console != null) {
            List<Games> filteredGames;
            if (orderBy != null && !orderBy.isEmpty()) {
                filteredGames = gamesRepository.findByFilter(minPrice, maxPrice, category, console, orderBy);
            } else {
                filteredGames = gamesRepository.findByFilter(minPrice, maxPrice, category, console, "price_asc");
            }
            model.addAttribute("allgames", filteredGames);
            long count = filteredGames.size();
            model.addAttribute("count", count);
        } else {
            allGames = gamesRepository.findAll();
            model.addAttribute("allgames", allGames);
            long count = allGames.spliterator().estimateSize();
            model.addAttribute("count", count);
        }

        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("console", console);
        model.addAttribute("category", category);

        return "index";
    }
    @PostMapping("/products/addratings/{id}")
    public String addRatings(Model model, @PathVariable Integer id, @RequestParam double newRatingValue) {

        Optional<Games> gamesOptional = gamesRepository.findById(id);

        if(gamesOptional.isPresent()){
            Games game = gamesOptional.get();

            Ratings newRating = new Ratings();
            newRating.setRating(newRatingValue);

            Collection<Games> gameCollection = new ArrayList<>();
            gameCollection.add(game);

            newRating.setGames(gameCollection);

            game.getRatings().add(newRating);

            gamesRepository.save(game);

            double averageScore = calculateAverageRating(game);
            model.addAttribute("game",game);
            model.addAttribute("averagescore", averageScore);
        }
        return "products/addratings"+id;
    }
    private double calculateAverageRating(Games game) {
        Collection<Ratings> ratings = game.getRatings();
        double totalScore = 0.0;
        int numberOfRatings = ratings.size();

        for (Ratings rating : ratings) {
            totalScore += rating.getRating();
        }

        return numberOfRatings > 0 ? totalScore / numberOfRatings : 0.0;
    }
}

