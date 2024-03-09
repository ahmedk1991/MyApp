package be.thomasmore.myapp.controllers;

import be.thomasmore.myapp.model.Games;
import be.thomasmore.myapp.model.GamesDto;
import be.thomasmore.myapp.repositories.GamesRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/products")
public class GamesController {
    @Autowired
    private GamesRepository gamesRepository;
@GetMapping({"","/"})
    public String showGamesList(Model model) {

        Iterable<Games> allgames = gamesRepository.findAll();
        model.addAttribute("allgames", allgames);

        return "products/index";
    }
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        GamesDto gamesDto = new GamesDto();
        model.addAttribute("gamesDto", gamesDto);

        return "products/createproduct";
    }
    @PostMapping("/create")
    public String createProduct(
         @Valid @ModelAttribute GamesDto gamesDto,
         BindingResult result
    ) {
    if(gamesDto.getImageFile().isEmpty()){
        result.addError(new FieldError("gamesDto","imageFile","The image file is required"));
    }
    if(result.hasErrors()){
        return "products/createproduct";
    }
    return "redirect:/products";
    }
}
