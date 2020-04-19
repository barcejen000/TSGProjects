/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogmanagementsystem.controllers;

import com.sg.blogmanagementsystem.entities.Article;
import com.sg.blogmanagementsystem.entities.Hashtag;
import com.sg.blogmanagementsystem.repositories.ArticleRepository;
import com.sg.blogmanagementsystem.repositories.HashtagRepository;
import com.sg.blogmanagementsystem.repositories.RoleRepository;
import com.sg.blogmanagementsystem.repositories.UserRepository;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author jenni
 */
@Controller
public class HashtagController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    ArticleRepository articleRepo;

    @Autowired
    HashtagRepository hashtagRepo;

    @GetMapping("hashtags")
    public String displayHashtags(Model model) {
        model.addAttribute("hashtags", hashtagRepo.findAll());
        model.addAttribute("articles", articleRepo.findAll());
        return "hashtags";
    }

    @GetMapping("deleteHashtag")
    public String deleteHashtag(String name) {
        Hashtag hashtag = hashtagRepo.findById(name).orElse(null);
        List<Article> articles = articleRepo.findAllByHashtagsContaining(hashtag);
        for(Article article: articles){
            article.getHashtags().remove(hashtag);
            articleRepo.save(article);
        }
        
        hashtagRepo.delete(hashtag);
        return "redirect:/hashtags";

    }

    @GetMapping("editHashtag")
    public String displayEditHashtag(String name, Model model) {
        Hashtag hashtag = hashtagRepo.findById(name).orElse(null);
        model.addAttribute("hashtag", hashtag);
        Hashtag oldHashtag = new Hashtag();
        model.addAttribute("oldHashtag", oldHashtag);
        return "editHashtag";
    }

    @PostMapping("editHashtag")
    public String editHashtag(@Valid Hashtag hashtag, BindingResult result, Model model, HttpServletRequest request) {
        String editName = request.getParameter("name");
        String originalName = request.getParameter("originalname");
        Hashtag oldHashtag = hashtagRepo.findById(originalName).orElse(null);

        if (!editName.isBlank()) {
            if(editName.length()>45){
                    FieldError fieldError2 = new FieldError("hashtag","name", "Hashtag name must be less than 45 characters");
                    result.addError(fieldError2);
            }else if(editName.equals(originalName)){
                FieldError fieldError = new FieldError("hashtag", "name", "no changes hashtag name is the same, click cancel or make a change");
                result.addError(fieldError);
            }else if(hashtagRepo.findAll().contains(hashtagRepo.findById(editName).orElse(null))) {
                FieldError fieldError = new FieldError("hashtag", "name", "#" + editName + " already exists");
                result.addError(fieldError);
            }else{
                List<Article> articles = articleRepo.findAllByHashtagsContaining(hashtagRepo.findById(originalName).orElse(null));
                hashtag.setArticles(articles);
                for(Article article: articles){
                article.getHashtags().remove(hashtagRepo.findById(originalName).orElse(null));
                articleRepo.save(article);
                }
                hashtagRepo.deleteById(originalName);
                hashtag.setName(editName);
                hashtagRepo.save(hashtag);
                for(Article article: hashtag.getArticles()){
                List<Hashtag> hashtags = article.getHashtags();
                hashtags.add(hashtag);
                article.setHashtags(hashtags);
                articleRepo.save(article);
                }
            }
        }else {
            FieldError fieldError2 = new FieldError("hashtag", "name", "Hashtag name cannot be left blank");
            result.addError(fieldError2);
        }

        if (result.hasErrors()) {
            model.addAttribute("hashtag", hashtag);
            model.addAttribute("oldHashtag", oldHashtag);
            return "editHashtag";
        }

        return "redirect:/hashtags";
    }
}
