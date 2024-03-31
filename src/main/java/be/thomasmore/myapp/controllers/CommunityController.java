package be.thomasmore.myapp.controllers;

import be.thomasmore.myapp.model.Members;
import be.thomasmore.myapp.repositories.GamesRepository;
import be.thomasmore.myapp.repositories.MemberRepository;
import be.thomasmore.myapp.repositories.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class CommunityController {
    @Autowired
    private GamesRepository gamesRepository;
    @Autowired
    private ReviewsRepository reviewsRepository;
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping({"/community"})
    public String showCommunity(Model model){

       Iterable<Members> allMembers=memberRepository.findAll();
       long count=allMembers.spliterator().estimateSize();

        model.addAttribute("allMembers",allMembers);
        model.addAttribute("count",count);
        return"products/community";
    }
}
