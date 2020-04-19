/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogmanagementsystem.controllers;

import com.sg.blogmanagementsystem.entities.Article;
import com.sg.blogmanagementsystem.repositories.ArticleRepository;
import com.sg.blogmanagementsystem.repositories.HashtagRepository;
import com.sg.blogmanagementsystem.repositories.RoleRepository;
import com.sg.blogmanagementsystem.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author jenni
 */
@Controller
public class HomeController {
    @Autowired
    UserRepository userRepo;
    
    @Autowired
    RoleRepository roleRepo;
    
    @Autowired
    ArticleRepository articleRepo;
    
    @Autowired
    HashtagRepository hashtagRepo;

 @GetMapping({"/", "/home"})
    public String displayHomePage(Model model) {
        //This one does not work if there are no blogs.
//        Article article = articleRepo.findArticleByApprovedAndStaticPage(4,false);
//        if(article != null){
//          model.addAttribute("article", article);
//        }else{
//         Article article2 = articleRepo.findArticleByPostDateAndStaticPageAndApproved(2,false);
//          model.addAttribute("article", article2);
//        }
//        
//        List<Article> articles = articleRepo.findAll();
//        model.addAttribute("articles", articles);
//        
//        return "home";
    
        //sends up empty list and article if no blogs exist in database
        Article article = articleRepo.findArticleByApprovedAndStaticPage(4, false);
        List<Article> articles = new ArrayList<>();
        if (article != null) {
            model.addAttribute("article", article);
            articles = articleRepo.findAll();
            model.addAttribute("articles", articles);
            return "home";
        } else {
            article = articleRepo.findArticleByPostDateAndStaticPageAndApproved(2, false);
        }

        if (article != null) {
            model.addAttribute("article", article);
            articles = articleRepo.findAll();
            model.addAttribute("articles", articles);
            return "home";
        } else {
            article = new Article();
            model.addAttribute("article", article);
            articles = articleRepo.findAll();
            model.addAttribute("articles", articles);
        }

        return "home";
    }
    
 @GetMapping("viewStatic")
    public String displayStaticPage(Integer id, Model model){
        Article article = articleRepo.findById(id).orElse(null);
        model.addAttribute("article", article);
        
        return "viewStatic";
        
    }
    
}
