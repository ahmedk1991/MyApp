package be.thomasmore.myapp.controllers;


import be.thomasmore.myapp.model.PostsDto;
import be.thomasmore.myapp.model.Posts;
import be.thomasmore.myapp.repositories.GamesRepository;
import be.thomasmore.myapp.repositories.PostsRepository;
import be.thomasmore.myapp.repositories.ReviewsRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Controller
@RequestMapping("/products")
public class CommunityController {
    @Autowired
    private GamesRepository gamesRepository;
    @Autowired
    private ReviewsRepository reviewsRepository;
    @Autowired
    private PostsRepository postsRepository;

    @GetMapping({"/community"})
    public String showCommunity(Model model){

       Iterable<Posts> allPosts= postsRepository.findAll();
       long count=allPosts.spliterator().estimateSize();

        model.addAttribute("allPosts",allPosts);
        model.addAttribute("count",count);
        return"products/community";
    }
    @GetMapping({"/contact"})
    public String showContact(Model model){


        return"products/contact";
    }
    @GetMapping("/createpost")
    public String showNewPost(Model model){
        PostsDto postSDto= new PostsDto();
        model.addAttribute("postsDto",postSDto);

        return "products/createpost";
    }
    @PostMapping("/createpost")
    public String createNewPost (Model model, @Valid @ModelAttribute PostsDto postSDto, BindingResult result) throws IOException{
        if (postSDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("postsDto", "imageFile", "The image file is required"));
        }
        if (result.hasErrors()) {
            return "products/community";
        }

       Posts posts=new Posts();
        posts.setName(postSDto.getName());
        posts.setDate(new Date());
        posts.setPost(postSDto.getPost());
        try {
            posts.setImageFileName(uploadImage(postSDto.getImageFile()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        postsRepository.save(posts);
        return "redirect:/products/community";
    }
    private String uploadImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        final String directoryPath = "src/main/resources/static/img/";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }


        String filename = multipartFile.getOriginalFilename();
        Path path = Paths.get(directoryPath + filename);
        Files.copy(multipartFile.getInputStream(), path);

        return filename;
    }



}
