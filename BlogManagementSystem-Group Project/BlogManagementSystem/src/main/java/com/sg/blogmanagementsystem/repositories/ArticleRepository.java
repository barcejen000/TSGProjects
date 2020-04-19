/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.blogmanagementsystem.repositories;

import com.sg.blogmanagementsystem.entities.Article;
import com.sg.blogmanagementsystem.entities.Hashtag;
import com.sg.blogmanagementsystem.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jenni
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>{
    List<Article> findAllByHashtagsContaining(Hashtag hashtag);
    List<Article> findAllByApproved(Integer approved);
    @Query("SELECT a FROM Article a where a.approved = ?1 AND a.staticPage =FALSE")
    Article findArticleByApprovedAndStaticPage(Integer approved, Boolean staticPage);
    @Query("SELECT a FROM Article a WHERE a.postDate =(SELECT MAX(a.postDate) FROM Article a WHERE a.staticPage =FALSE AND a.approved = ?1)")
    Article findArticleByPostDateAndStaticPageAndApproved(Integer approved, Boolean staticPage);
    List<Article> findAllByUser(User user);
    List<Article> findAllByTitle(String title);
    @Query("select a from Article a order by a.postDate DESC")
    List<Article> findAllAndOrderByPostDate();
    
}
