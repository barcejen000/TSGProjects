/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogmanagementsystem.controllers;

import com.sg.blogmanagementsystem.entities.Article;
import com.sg.blogmanagementsystem.entities.Comment;
import com.sg.blogmanagementsystem.entities.Hashtag;
import com.sg.blogmanagementsystem.entities.User;
import com.sg.blogmanagementsystem.repositories.ArticleRepository;
import com.sg.blogmanagementsystem.repositories.CommentRepository;
import com.sg.blogmanagementsystem.repositories.HashtagRepository;
import com.sg.blogmanagementsystem.repositories.RoleRepository;
import com.sg.blogmanagementsystem.repositories.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class ArticleController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    ArticleRepository articleRepo;

    @Autowired
    HashtagRepository hashtagRepo;
    
    @Autowired
    CommentRepository commentRepo;

    @GetMapping("blogs")
    public String displayBlogs(Model model) {
        model.addAttribute("articles", articleRepo.findAllAndOrderByPostDate());
        model.addAttribute("hashtags", hashtagRepo.findAll());
        return "blogs";
    }

    @GetMapping("viewBlog")
    public String displayIndividualBlog(Integer id, Model model) {
        Article article = articleRepo.findById(id).orElse(null);
        model.addAttribute("article", article);
        model.addAttribute("hashtags", hashtagRepo.findAll());
        return "viewBlog";
    }

    @GetMapping("addBlog")
    public String displayAddBlog(Model model) {
        model.addAttribute("articles", articleRepo.findAllAndOrderByPostDate());
        model.addAttribute("hashtags", hashtagRepo.findAll());
        model.addAttribute("article", new Article());
        model.addAttribute("hashtag", new Hashtag());

        return "addBlog";
    }

    @PostMapping("addBlog")
    public String addArticle(@Valid Article article, BindingResult result, @Valid Hashtag hashtag, BindingResult result2, Principal principal, HttpServletRequest request, Model model) {
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
            return "addBlog";
        }
        if (result2.hasErrors()) {
            model.addAttribute("article", article);
            model.addAttribute("hashtag", hashtag);
            model.addAttribute("hashtags", hashtagRepo.findAll());
            return "addBlog";
        }

        articleRepo.save(article);
        return "redirect:/blogs";
    }

    @GetMapping("searches")
    public String searches(Model model) {
        List<Hashtag> hashtags = hashtagRepo.findAll();
        List<User> users = userRepo.findAll();
        model.addAttribute("hashtags", hashtags);
        model.addAttribute("users", users);
        return "searches";
    }

    @GetMapping("searchBlogsByHashtag")
    public String viewHashtagResults(HttpServletRequest request, Model model) {
        String[] hashtagIds = request.getParameterValues("hashtagIds");
        List<Hashtag> hashtags = new ArrayList<>();
        for (String name : hashtagIds) {
            hashtags.add(hashtagRepo.findById(name).orElse(null));
        }
        List<Article> articleList = new ArrayList<>();
        Set<Article> articles = new HashSet<>();
        for (Hashtag hashtag : hashtags) {
            articleList = articleRepo.findAllByHashtagsContaining(hashtag);
            articleList = filteroutUnpublishedAndStatic(articleList);
            for (Article article : articleList) {
                articles.add(article);
            }
        }
        model.addAttribute("articles", articles);
        return "searchResults";
    }

    @GetMapping("searchBlogsByAuthor")
    public String viewAuthorResults(HttpServletRequest request, Model model) {
        String usernameId = request.getParameter("usernameId");
        User user = userRepo.findById(usernameId).orElse(null);
        List<Article> articles = articleRepo.findAllByUser(user);
        articles = filteroutUnpublishedAndStatic(articles);
        model.addAttribute("articles", articles);
        return "searchResults";
    }

    @GetMapping("searchByTitle")
    public String viewTitleResult(String title, Model model) {
        List<Article> articles = articleRepo.findAllByTitle(title);
        articles = filteroutUnpublishedAndStatic(articles);
        model.addAttribute("articles", articles);
        return "searchResults";
    }

    private List<Article> filteroutUnpublishedAndStatic(List<Article> articles) {
        List<Article> newList = new ArrayList<>();
        for(Article article : articles) {
            if (!article.isStaticPage() && article.getApproved() == 2 || !article.isStaticPage() && article.getApproved() == 4) {
                newList.add(article);
            }
        }
        return newList;
    }

    @GetMapping("editBlog")
    public String displayEditBlog(Integer id, Model model) {
        Article article = articleRepo.findById(id).orElse(null);
        model.addAttribute("article", article);
        model.addAttribute("hashtags", hashtagRepo.findAll());
        model.addAttribute("hashtag", new Hashtag());

        return "editBlog";
    }

    @PostMapping("editBlog")
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
            return "editBlog";
        }

        if (result2.hasErrors()) {
            model.addAttribute("article", article);
            model.addAttribute("hashtag", hashtag);
            model.addAttribute("hashtags", hashtagRepo.findAll());
            return "editBlog";
        }

        articleRepo.save(article);
        return "redirect:/blogs";
    }

    @GetMapping("deleteBlog")
    public String deleteBlog(Integer id) {
        Article article = articleRepo.findById(id).orElse(null);
        
        List<Comment> originalcomments = article.getComments();
        for(Comment comment: originalcomments){
            commentRepo.delete(comment);
        }
//        List<Comment> comments = new ArrayList<>();
//        article.setComments(comments);
//        articleRepo.save(article);
        articleRepo.delete(article);
//        commentRepo.delete(comment);

        return "redirect:/blogs";
    }
}
