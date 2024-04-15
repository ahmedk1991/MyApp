package be.thomasmore.myapp.controllers;

import be.thomasmore.myapp.model.Games;
import be.thomasmore.myapp.model.GamesDto;
import be.thomasmore.myapp.model.Ratings;
import be.thomasmore.myapp.model.Reviews;
import be.thomasmore.myapp.repositories.GamesRepository;

import be.thomasmore.myapp.repositories.RatingsRepository;
import be.thomasmore.myapp.repositories.ReviewsRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/products")
public class GamesController {
    @Autowired
    private GamesRepository gamesRepository;
    @Autowired
    private ReviewsRepository reviewsRepository;
    @Autowired
    private RatingsRepository ratingsRepository;
    private static final Logger logger = LoggerFactory.getLogger(GamesController.class);
    @GetMapping({"", "/"})


            public String showGamesList(Model model) {

                Iterable<Games> allgames;
                allgames = gamesRepository.findAll();

                model.addAttribute("allgames", allgames);
                return "products/gameslist";
            }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        GamesDto gamesDto = new GamesDto();
        model.addAttribute("gamesDto", gamesDto);

        return "products/createproduct";
    }


    @PostMapping("/create")
    public String createProduct( @Valid @ModelAttribute GamesDto gamesDto, BindingResult result) throws IOException {

        if (gamesDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("gamesDto", "imageFile", "The image file is required"));
        }
        if (result.hasErrors()) {
            return "products/createproduct";
        }

        Games game = new Games();
        game.setName(gamesDto.getName());
        game.setCategory(gamesDto.getCategory());
        game.setPrice(gamesDto.getPrice());
        game.setDescription(gamesDto.getDescription());
        game.setConsole(gamesDto.getDescription());
        game.setVideoUrl(gamesDto.getVideoUrl());
        try {
            game.setImageFileName(uploadImage(gamesDto.getImageFile()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        gamesRepository.save(game);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String displayEditPage(Model model, @PathVariable int id) {
        Optional<Games> gameOptional = gamesRepository.findById(id);
        logger.debug("Displaying edit page for ID: {}", id);
        if (gameOptional.isPresent()) {
            Games game = gameOptional.get();
            GamesDto gamesDto = new GamesDto();
            gamesDto.setName(game.getName());
            gamesDto.setCategory(game.getCategory());
            gamesDto.setPrice(game.getPrice());
            gamesDto.setDescription(game.getDescription());
            gamesDto.setConsole(game.getConsole());
            gamesDto.setVideoUrl(game.getVideoUrl());

            model.addAttribute("game", game);
            model.addAttribute("gamesDto", gamesDto);
            return "products/editproduct";
        } else {
            return "redirect:/products";
        }
    }


    @PostMapping("/edit/{id}")
    public String editGame(@Valid @ModelAttribute GamesDto gamesDto, BindingResult result, @PathVariable int id, Model model) {
        logger.debug("Posting data for ID: {}", id);
        if (result.hasErrors()) {
            logger.error("Validation errors: {}", result.getAllErrors());
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
                game.setConsole(gamesDto.getConsole());
                game.setVideoUrl(gamesDto.getVideoUrl());


                if (!gamesDto.getImageFile().isEmpty()) {
                    String filename = uploadImage(gamesDto.getImageFile());
                    game.setImageFileName(filename);
                }

                gamesRepository.save(game);
                return "products/editproduct";
            } else {
                return "redirect:/products";
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return "redirect:/products";
        }

    }

    @GetMapping("/delete/{id}")

    public String deleteGame(@PathVariable int id) {
        Optional<Games> gameOptional = gamesRepository.findById(id);

        if (!gameOptional.isPresent()) {

            return "redirect:/products/gamedetails";
        }



        gamesRepository.findById(id).ifPresent(game -> {

            game.getReviews().forEach(review -> {
                review.getGames().remove(game);

                reviewsRepository.save(review);
            });

            game.getRatings().forEach(rating -> {
                rating.getGames().remove(game);
                ratingsRepository.save(rating);});


            gamesRepository.delete(game);
        });
        Games spel = gameOptional.get();

        gamesRepository.delete(spel);

        return "redirect:/products";
    }


    private String uploadImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        final String directoryPath = "src/main/resources/static/img/";
        File directory = new File(directoryPath.trim());
        if (!directory.exists()) {
            directory.mkdirs();
        }


        String filename = multipartFile.getOriginalFilename();
        Path path = Paths.get(directoryPath + filename);
        Files.copy(multipartFile.getInputStream(), path);

        return filename;
    }


}