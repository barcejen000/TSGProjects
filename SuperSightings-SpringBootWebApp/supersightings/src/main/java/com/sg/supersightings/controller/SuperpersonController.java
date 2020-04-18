/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.controller;

import com.sg.supersightings.dao.LocationDao;
import com.sg.supersightings.dao.OrganizationDao;
import com.sg.supersightings.dao.SightingDao;
import com.sg.supersightings.dao.SuperpersonDao;
import com.sg.supersightings.entities.Superperson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author jenni
 */
@Controller
public class SuperpersonController {
    
    @Autowired
    LocationDao locationDao;
   
    @Autowired 
    SuperpersonDao superDao;
    
    @Autowired 
    OrganizationDao orgDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @GetMapping("supers")
    public String displaySuperpersons(Model model){
        List<Superperson> superpersons = superDao.getAllSuperpersons();
        model.addAttribute("superpersons",superpersons);
        model.addAttribute("superperson", new Superperson());
        return "supers";
    }   
    
    
    @PostMapping("addSuper")
    public String addSuperperson(@Valid Superperson superperson, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("superpersons",superDao.getAllSuperpersons());
            return "supers";
        }
        superDao.addSuperperson(superperson);
        return "redirect:/supers";
    }
    
    @GetMapping("viewSuper")
    public String displaySuperpersonInfo(HttpServletRequest request, Model model){
        int superId = Integer.parseInt(request.getParameter("id"));
        Superperson superperson = superDao.getSuperpersonById(superId);
        model.addAttribute("superperson", superperson);
        
        return "viewSuper";
    }
    
    @GetMapping("deleteSuper")
    public String deleteSuper(HttpServletRequest request){
        int superId = Integer.parseInt(request.getParameter("id"));
        superDao.deleteSuperpersonById(superId);
        
        return "redirect:/supers";
    }
    
    @GetMapping("editSuper")
    public String getEditSuper(Model model, HttpServletRequest request){
        int superId = Integer.parseInt(request.getParameter("id"));
        Superperson superperson = superDao.getSuperpersonById(superId);
        model.addAttribute("superperson", superperson);
        return "editSuper";
    }
    
    @PostMapping("editSuper")
    public String editSuper(@Valid Superperson superperson, BindingResult result){
        if(result.hasErrors()){
            return "editSuper";
        }
        superDao.updateSuperperson(superperson);
        return "redirect:/supers";
    }
    
    @PostMapping("editImage")
    public String editImage(@Valid Superperson superperson, BindingResult result){
        if(result.hasErrors()){
//            model.addAttribute("superperson",superDao.getSuperpersonById(superperson.getId()));
            return "viewSuper";
        }
        
        superDao.updateSuperperson(superperson);
        return "viewSuper";
    }
}
