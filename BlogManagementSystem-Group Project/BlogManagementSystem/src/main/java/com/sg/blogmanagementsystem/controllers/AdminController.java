/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogmanagementsystem.controllers;

import com.sg.blogmanagementsystem.entities.Article;
import com.sg.blogmanagementsystem.entities.Hashtag;
import com.sg.blogmanagementsystem.entities.Role;
import com.sg.blogmanagementsystem.entities.User;
import com.sg.blogmanagementsystem.repositories.ArticleRepository;
import com.sg.blogmanagementsystem.repositories.HashtagRepository;
import com.sg.blogmanagementsystem.repositories.RoleRepository;
import com.sg.blogmanagementsystem.repositories.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
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
public class AdminController {

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
    
    Set<ConstraintViolation<User>> violations = new HashSet<>();

    @GetMapping("/admin")
    public String displayAdminPage(Model model) {
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("articles", articleRepo.findAllAndOrderByPostDate());
        model.addAttribute("user", new User());
        return "admin";
    }

    @PostMapping("/addUser")
    public String addUser(@Valid User user, BindingResult result, Model model, HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(!username.isBlank()){
            if(userRepo.findAll().contains(userRepo.findById(username).orElse(null))){
            FieldError fieldError = new FieldError("user","username", "Username '" +username+ "' already exists, please choose another one.");
            result.addError(fieldError);
            }
        }
        
        if(password.length() <8){
           FieldError fieldError2 = new FieldError("user", "password", "Password must be a minimum of 8 characters");
            result.addError(fieldError2); 
        }
        
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEnabled(true);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleRepo.findByRole("ROLE_USER"));
        user.setRoles(userRoles);

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("users", userRepo.findAll());
            model.addAttribute("articles", articleRepo.findAll());
            return "admin";
        }

        userRepo.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(String username) {
        User user = userRepo.findById(username).orElse(null);
        User admin = userRepo.findById("admin").orElse(null);
        List<Article> articles = articleRepo.findAllByUser(user);
        if(!articles.isEmpty()){
            for(Article article : articles){
                article.setUser(admin);
                articleRepo.save(article);
            }
        }
        userRepo.deleteById(username);
        return "redirect:/admin";
    }

    @GetMapping("/editUser")
    public String editUser(Model model, String username) {
        User user = userRepo.findById(username).orElse(null);
        List<Role> rolesList = roleRepo.findAll();

        model.addAttribute(user);
        model.addAttribute(rolesList);
        return "editUser";
    }

    @PostMapping("/editUser")
    public String performEditUser(@Valid User user, BindingResult result, HttpServletRequest request, Model model, Boolean enabled) {
        String[] roleIdList = request.getParameterValues("roleIdList");
        String username = request.getParameter("username");
        User user2 = userRepo.findById(username).orElse(null);
        
        if (enabled != null) {
            user.setEnabled(enabled);
        } else {
            user.setEnabled(false);
        }

        Set<Role> roleList = new HashSet<>();
        if (roleIdList != null) {
            for (String roleId : roleIdList) {
                Role role = roleRepo.findById(Integer.parseInt(roleId)).orElse(null);
                roleList.add(role);
            }
        }else {
             FieldError fieldError = new FieldError("user", "roles", "User must be assigned a role");
            result.addError(fieldError);
        }
        user.setRoles(roleList);
        user.setPassword(user2.getPassword());
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            List<Role> rolesList = roleRepo.findAll();
            model.addAttribute(rolesList);
            return "/editUser";
        }
        
        userRepo.save(user);
        return "redirect:/admin";
    }


    
    @GetMapping("editPassword")
    public String editPasswordDisplay(Model model, String username, Integer error){
        User user = userRepo.findById(username).orElse(null);
        
//            if (error != null) {
//            if (error == 1) {
//                model.addAttribute("error", "Passwords did not match, password was not updated.");
//            }
//        }
        model.addAttribute("user", user);
//         model.addAttribute("errors", violations);
        return "editPassword";
    }
    
    
//    @PostMapping("editPassword")
//    public String editPassword(String username, String password, String confirmPassword) {
//        User user = userRepo.findById(username).orElse(null);
//
//        if (password.equals(confirmPassword)) {
//            user.setPassword(encoder.encode(password));
//            userRepo.save(user);
//            return "redirect:/admin";
//        } else {
////            return "/editPassword?id=" + username + "&error=1";
//            return "editPassword";
//        }
//    }
    
       @PostMapping("editPassword")
    public String editPassword(@Valid User user, BindingResult result, HttpServletRequest request, Model model) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword =request.getParameter("confirmPassword");
        User user2 = userRepo.findById(username).orElse(null);
        if(!password.isBlank() && !confirmPassword.isBlank()){
        if(password.length() <8){
           FieldError fieldError = new FieldError("user", "password", "Passwords must be a minimum of 8 characters");
            result.addError(fieldError); 
        }else if (!password.equals(confirmPassword)) {
            FieldError fieldError2 = new FieldError("user", "password", "Passwords did not match, password was not updated.");
            result.addError(fieldError2);
        }
        }
        
        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "editPassword";
        }
        user.setRoles(user2.getRoles());
        user.setEnabled(user2.isEnabled());
        user.setPassword(encoder.encode(password));
        userRepo.save(user);
        return "redirect:/admin";
    }

    @GetMapping("blogApproval")
    public String displayBlogApproval(Integer id, Model model) {
        Article article = articleRepo.findById(id).orElse(null);
        model.addAttribute("article", article);
        model.addAttribute("hashtags", hashtagRepo.findAll());

        return "blogApproval";
    }

    @PostMapping("blogApproval")
    public String editBlog(Article article, Principal principal, HttpServletRequest request) {
        String[] hashtagIds = request.getParameterValues("hashtagId");
        String username = request.getParameter("username");
        User user = userRepo.findById(username).orElse(null);
        article.setUser(user);

        List<Hashtag> hashtags = new ArrayList<>();
        for (String hashId : hashtagIds) {
            Hashtag hash = hashtagRepo.findById(hashId).orElse(null);
            hashtags.add(hash);
        }

        article.setHashtags(hashtags);
        if (article.getApproved() == 4) {
            List<Article> featuredArticles = articleRepo.findAllByApproved(4);
            for (Article featuredArticle : featuredArticles) {
                featuredArticle.setApproved(2);
            }
        }
        articleRepo.save(article);
        return "redirect:/admin";
    }
}
