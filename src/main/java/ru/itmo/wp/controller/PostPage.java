package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post/{id}")
    public String post(Model model, @PathVariable("id") String id) {
        long idLong = -1;
        try {
            idLong = Long.parseLong(id);
        } catch (NumberFormatException e) {
            System.out.println("sudo -rm- rf");
        }
        model.addAttribute("post", postService.findById(idLong));
        model.addAttribute("comment", new Comment());
        return "PostPage";
    }

    @PostMapping("/post/{id}")
    public String writeCommentPost(@Valid @ModelAttribute("comment") Comment comment,
                                   @PathVariable("id") String id,
                                BindingResult bindingResult,
                                HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "PostPage";
        }

        long idLong = -1;
        try {
            idLong = Long.parseLong(id);
        } catch (NumberFormatException e) {
            System.out.println("sudo -rm- rf");
        }
        postService.writeComment(postService.findById(idLong), comment, getUser(httpSession));
        putMessage(httpSession, "You published new comment");
        return "redirect:/posts";
    }

}
