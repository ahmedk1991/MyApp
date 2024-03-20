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
import org.springframework.web.bind.annotation.*;


import java.util.Optional;


@Controller
@RequestMapping("/products")
public class GamesController {
    @Autowired
    private GamesRepository gamesRepository;

    @GetMapping({"", "/"})
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
        if (gamesDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("gamesDto", "imageFile", "The image file is required"));
        }
        if (result.hasErrors()) {
            return "products/createproduct";
        }
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String displayEditPage(Model model, @PathVariable int id) {
        try {
            Optional<Games> gameOptional = gamesRepository.findById(id);
            if (gameOptional.isPresent()) {
                Games game = gameOptional.get();
                model.addAttribute("game", game);

                GamesDto gamesDto = new GamesDto();
                gamesDto.setName(game.getName());
                gamesDto.setCategory(game.getCategory());
                gamesDto.setPrice(game.getPrice());
                gamesDto.setDescription(game.getDescription());
                gamesDto.setImageFile(gamesDto.getImageFile());

                model.addAttribute("gamesDto", gamesDto);
                return "products/editproduct";
            } else {
                return "redirect:/products";
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return "redirect:/products";
        }
    }

    @PostMapping("/edit/{id}")
    public String editGame(@Valid @ModelAttribute GamesDto gamesDto, BindingResult result, @PathVariable int id) {
        if (result.hasErrors()) {

            return "products/editproduct";
        }

        try {
            Optional<Games> gameOptional = gamesRepository.findById(id);
            if (gameOptional.isPresent()) {
                Games game = gameOptional.get();

                game.setName(gamesDto.getName());
                game.setCategory(gamesDto.getCategory());
                game.setPrice(gamesDto.getPrice());
                game.setDescription(gamesDto.getDescription());

                gamesRepository.save(game);


                return "redirect:/products";
            } else {

                return "redirect:/products";
            }
        } catch (Exception ex) {

            System.out.println("Error: " + ex.getMessage());
            return "redirect:/products";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {

        Optional<Games> gameOptional = gamesRepository.findById(id);

        if (gameOptional.isPresent()) {

            Games game = gameOptional.get();
            model.addAttribute("game", game);
            gamesRepository.delete(game);

            return "redirect:/products";
        }

        return "products";
    }

}

