package be.thomasmore.myapp.controllers;

import be.thomasmore.myapp.model.Games;
import be.thomasmore.myapp.repositories.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class homePageController  {

    @Autowired
    private GamesRepository gamesRepository;
    @GetMapping({"/"})
  public String indexListFilter(Model model,
                                 @RequestParam(required = false) Integer minPrice,
                                 @RequestParam(required = false) Integer maxPrice,
                                 @RequestParam(required = false) String category,
                                  @RequestParam(required = false) String console) {


        Iterable<Games> allGames;


       if (minPrice != null || maxPrice != null || category != null || console != null) {
           List<Games> filteredGames = gamesRepository.findByFilter(minPrice, maxPrice, category, console);
          model.addAttribute("allgames", filteredGames);
          long count=filteredGames.size();
          model.addAttribute("count",count);
       } else {
            allGames=gamesRepository.findAll();
       model.addAttribute("allgames", allGames);
           long count=allGames.spliterator().estimateSize();
           model.addAttribute("count",count);
        }

       model.addAttribute("minPrice", minPrice);
       model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("console", console);
       model.addAttribute("category", category);

        return "index";
    }
}
