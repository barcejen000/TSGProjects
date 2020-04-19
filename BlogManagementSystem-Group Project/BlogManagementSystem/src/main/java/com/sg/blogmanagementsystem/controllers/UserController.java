/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogmanagementsystem.controllers;

import com.sg.blogmanagementsystem.entities.Article;
import com.sg.blogmanagementsystem.entities.Hashtag;
import com.sg.blogmanagementsystem.entities.User;
import com.sg.blogmanagementsystem.repositories.ArticleRepository;
import com.sg.blogmanagementsystem.repositories.HashtagRepository;
import com.sg.blogmanagementsystem.repositories.RoleRepository;
import com.sg.blogmanagementsystem.repositories.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author pfcar
 */
@Controller
public class UserController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    ArticleRepository articleRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    HashtagRepository hashtagRepo;

    @GetMapping("/users")
    public String displayAdminPage(Model model) {
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("articles", articleRepo.findAllAndOrderByPostDate());
        return "users";
    }

    @GetMapping("editUserBlog")
    public String displayEditBlog(Integer id, Model model) {
        Article article = articleRepo.findById(id).orElse(null);
        model.addAttribute("article", article);
        model.addAttribute("hashtags", hashtagRepo.findAll());
        model.addAttribute("hashtag", new Hashtag());

        return "editUserBlog";
    }

    @PostMapping("editUserBlog")
    public String editBlog(@Valid Article article, BindingResult result, @Valid Hashtag hashtag, BindingResult result2, Principal principal, HttpServletRequest request, Model model) {
        String[] hashIds = request.getParameterValues("hashtagId");
        String approval = request.getParameter("approved");
        String[] hashtagIds = request.getParameterValues("hashtagIds");
        int approved;
        if (approval == null) {
            approved = 0;
        } else {
            approved = Integer.parseInt(approval);
        }

        User user = userRepo.findById(principal.getName()).orElse(null);
        article.setUser(user);
        
        List<Hashtag> hashtags = new ArrayList<>();
        if (hashIds != null) {
            for (String hashId : hashIds) {
                Hashtag hash = hashtagRepo.findById(hashId).orElse(null);
                hashtags.add(hash);
            }
        } else  {
            if(hashtagIds.length == 1){
                for (String hashtagId : hashtagIds) {
                    if (hashtagId.isBlank() && article.isStaticPage() == false) {
                       FieldError fieldError = new FieldError("article", "hashtags", "You must select atleast one hashtag for the blog");
                       result.addError(fieldError);
                    }
                }
            }
        }

        if (hashtagIds != null) {
            for (String hashtagId : hashtagIds) {
                if (!hashtagId.isBlank()) {
                    if (hashtagId.length() > 45) {
                        FieldError fieldError2 = new FieldError("hashtag", "name", "Hashtag name must be less than 45 characters");
                        result2.addError(fieldError2);
                    } else if (hashtagRepo.findAll().contains(hashtagRepo.findById(hashtagId).orElse(null))) {
                        FieldError fieldError3 = new FieldError("hashtag", "name", "#" + hashtagId + " already exists, please select from existing hashtags.");
                        result2.addError(fieldError3);
                    } else {
                        Hashtag hashtag2 = new Hashtag();
                        hashtag2.setName(hashtagId);
                        hashtagRepo.save(hashtag2);
                        hashtags.add(hashtag2);
                    }
                }
            }
        }
       
        article.setHashtags(hashtags);
        
        if (article.getApproved() == 4) {
            List<Article> featuredArticles = articleRepo.findAllByApproved(4);
            for (Article featuredArticle : featuredArticles) {
                featuredArticle.setApproved(2);
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("article", article);
            model.addAttribute("hashtag", hashtag);
            model.addAttribute("hashtags", hashtagRepo.findAll());
            return "editUserBlog";
        }

        if (result2.hasErrors()) {
            model.addAttribute("article", article);
            model.addAttribute("hashtag", hashtag);
            model.addAttribute("hashtags", hashtagRepo.findAll());
            return "editUserBlog";
        }

        articleRepo.save(article);
        return "redirect:/users";
    }

}
