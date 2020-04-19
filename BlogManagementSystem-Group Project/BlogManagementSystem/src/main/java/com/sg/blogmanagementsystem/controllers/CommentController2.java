/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogmanagementsystem.controllers;

import com.sg.blogmanagementsystem.entities.Article;
import com.sg.blogmanagementsystem.entities.Comment;
import com.sg.blogmanagementsystem.repositories.ArticleRepository;
import com.sg.blogmanagementsystem.repositories.CommentRepository;
import com.sg.blogmanagementsystem.repositories.HashtagRepository;
import com.sg.blogmanagementsystem.repositories.RoleRepository;
import com.sg.blogmanagementsystem.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author jenni
 */
@Controller
public class CommentController2 {

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

    @PostMapping("/addComment")
    public String addComment(HttpServletRequest request) {
        String title = request.getParameter("title");
        String body = request.getParameter("body");
        String articleId = request.getParameter("articleId");
        Integer id = Integer.parseInt(articleId);
        Article article = articleRepo.findById(id).orElse(null);

        Comment comment = new Comment();
        comment.setTitle(title);
        comment.setBody(body);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        comment.setArticle(article);
        article.setComments(comments);
        articleRepo.save(article);

        commentRepo.save(comment);

        return "redirect:/viewBlog?id=" + id;

    }

    @GetMapping("deleteComment")
    public String deleteComment(Integer id) {
    Comment comment =  commentRepo.findById(id).orElse(null);
    Article article = comment.getArticle();
     List<Comment> comments = article.getComments();
    comments.remove(comment);
    articleRepo.save(article);
    
//    Article article2 = new Article();
//    comment.setArticle(article2);
//    commentRepo.save(comment);
    
    commentRepo.delete(comment);
    return "redirect:/viewBlog?id=" + article.getId();
    }
}
