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
import com.sg.supersightings.entities.Organization;
import com.sg.supersightings.entities.Superperson;
import java.util.ArrayList;
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
public class OrganizationController {
    
    @Autowired
    LocationDao locationDao;
   
    @Autowired 
    SuperpersonDao superDao;
    
    @Autowired 
    OrganizationDao orgDao;
    
    @Autowired
    SightingDao sightingDao;
    
    
 @GetMapping("organizations")
 public String displayAllOrgs(Model model) {
     List<Organization> organizations = orgDao.getAllOrganizations();
     List<Superperson> members = superDao.getAllSuperpersons();
     model.addAttribute("organizations", organizations);
     model.addAttribute("members",members);
     model.addAttribute("organization", new Organization());
     
     return "organizations";
 }
 
 @GetMapping("viewOrganization")
 public String displayOrgInfo(Integer id, Model model){
     Organization organization = orgDao.getOrganizationById(id);
     model.addAttribute("organization", organization);
     return "viewOrganization";
 }
 
 @GetMapping("deleteOrganization")
 public String deleteOrg(Integer id){
     orgDao.deleteOrganizationById(id);
     return "redirect:/organizations"; 
 }
 
 @PostMapping("addOrganization")
 public String addOrg(@Valid Organization organization, BindingResult result, HttpServletRequest request,Model model){
     String[] memberIds = request.getParameterValues("superId");
     
     List<Superperson> members = new ArrayList<>();
     if(memberIds != null){
     for(String memberId: memberIds){
         members.add(superDao.getSuperpersonById(Integer.parseInt(memberId)));
     }
     }else{
         FieldError fieldError = new FieldError("organization", "members", "You must select one member");
         result.addError(fieldError);
     }
      organization.setMembers(members);
      if(result.hasErrors()){
         model.addAttribute("members", superDao.getAllSuperpersons());
         model.addAttribute("organizations", orgDao.getAllOrganizations());
         model.addAttribute("organization", organization);
         return "organizations";
     }
      
      orgDao.addOrganization(organization);
      return "redirect:/organizations";
 }
 
 
 @GetMapping("editOrganization")
 public String getEditOrgInfo(HttpServletRequest request, Model model){
     int orgId = Integer.parseInt(request.getParameter("id"));
     Organization organization = orgDao.getOrganizationById(orgId);
     List<Superperson> members = superDao.getAllSuperpersons();
     model.addAttribute("organization", organization);
     model.addAttribute("members", members);
     return "editOrganization";
 }
  
 
 @PostMapping("editOrganization")
 public String editOrg(@Valid Organization organization,BindingResult result,Model model, HttpServletRequest request){
     String[] memberIds = request.getParameterValues("superId");
     
     List<Superperson> members = new ArrayList<>();
     if(memberIds != null){
     for(String memberId: memberIds){
         members.add(superDao.getSuperpersonById(Integer.parseInt(memberId)));
     }
     }else{
         FieldError fieldError = new FieldError("organization", "members", "You must select one member");
         result.addError(fieldError);
     }
     
     organization.setMembers(members);
     if(result.hasErrors()){
         model.addAttribute("organization", organization);
         model.addAttribute("members", superDao.getAllSuperpersons());
         return "editOrganization";
     }
     orgDao.updateOrganization(organization);
     return "redirect:/organizations";
 }
 
    
}
