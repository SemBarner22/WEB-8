package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.form.validator.PostTagCredentialsValidator;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class WritePostPage extends Page {
    private final UserService userService;
    private final PostService postService;
    private final PostTagCredentialsValidator postTagCredentialsValidator;

    public WritePostPage(UserService userService, PostService postService, PostTagCredentialsValidator postTagCredentialsValidator) {
        this.userService = userService;
        this.postService = postService;
        this.postTagCredentialsValidator = postTagCredentialsValidator;
    }

    @InitBinder("post")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(postTagCredentialsValidator);
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/writePost")
    public String writePostGet(Model model) {
                                        // was new Post() here
        model.addAttribute("post", new PostCredentials());
        return "WritePostPage";
    }

                                    // Post post was here


//    public String writePostPost(@Valid @ModelAttribute("post") Post post,
//                                BindingResult bindingResult,
//                                HttpSession httpSession) {
//        if (bindingResult.hasErrors()) {
//            return "WritePostPage";
//        }
//
//        //Post post = postService.makePost(postForm);
//        // ????
//        //Set<Tag> tags = new HashSet<>();
//        // post was here
//        //, tags
//        userService.writePost(getUser(httpSession), post);
//        putMessage(httpSession, "You published new post");
//
//        return "redirect:/posts";
//    }

    // PostCredentials postForm
    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/writePost")
    public String writePostPost(@Valid @ModelAttribute("post") PostCredentials postForm,
                                BindingResult bindingResult,
                                HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "WritePostPage";
        }

        Post post = postService.makePost(postForm);
        post.setUser(getUser(httpSession));
        userService.writePost(getUser(httpSession), post);
        putMessage(httpSession, "You published new post");

        return "redirect:/posts";
    }

}
