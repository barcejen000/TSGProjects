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
import com.sg.supersightings.entities.Location;
import com.sg.supersightings.entities.Sighting;
import com.sg.supersightings.entities.Superperson;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class SightingController {
    
    @Autowired
    LocationDao locationDao;
   
    @Autowired 
    SuperpersonDao superDao;
    
    @Autowired 
    OrganizationDao orgDao;
    
    @Autowired
    SightingDao sightingDao;
    
@GetMapping({"/","home"})
public String display10LatestSightings(Model model){
    List<Sighting> sightings = sightingDao.getLatest10Sightings();
    List<Superperson> superpersons = superDao.getAllSuperpersons();
    model.addAttribute("sightings", sightings);
    model.addAttribute("superpersons", superpersons);
    return "home";
}
    
@GetMapping("sightings")
public String displaySightings(Model model){
    List<Superperson> superpersons = superDao.getAllSuperpersons();
    List<Location> locations = locationDao.getAllLocations();
    List<Sighting> sightings = sightingDao.getAllSightings();
    
    model.addAttribute("superpersons", superpersons);
    model.addAttribute("locations", locations);
    model.addAttribute("sightings", sightings);
    model.addAttribute("sighting", new Sighting());
    
    return "sightings";  
}

@GetMapping("viewSighting")
public String displaySighting(Integer id, Model model){
    Sighting sighting = sightingDao.getSightingById(id);
    model.addAttribute("sighting", sighting);
    
    return "viewSighting";
}

@PostMapping("addSighting")
public String addSighting(@Valid Sighting sighting, BindingResult result, Model model, HttpServletRequest request){
    int locationId = Integer.parseInt(request.getParameter("locationId"));
    String[] superIds = request.getParameterValues("superId");
    
    Location location = locationDao.getLocationById(locationId);
    sighting.setLocation(location);
    
    List<Superperson> superpersons = new ArrayList<>();
    if(superIds != null){
    for(String superId:superIds){
     Superperson superperson = superDao.getSuperpersonById(Integer.parseInt(superId));
     superpersons.add(superperson);
    }
    } else{
        FieldError fieldError = new FieldError("sighting","superpersons","You must select one Super");
        result.addError(fieldError);
    }
    
    sighting.setSuperpersons(superpersons);
    
    if(result.hasErrors()){
        model.addAttribute("superpersons", superDao.getAllSuperpersons());
        model.addAttribute("locations", locationDao.getAllLocations());
        model.addAttribute("sightings", sightingDao.getAllSightings());
        model.addAttribute("sighting", sighting);
        return "sightings";
    }
    
    sightingDao.addSighting(sighting);
    return "redirect:/sightings";
    
}


@GetMapping("deleteSighting")
public String deleteSighting(int id){
    sightingDao.deleteSightingById(id);
    return "redirect:/sightings";
}

@GetMapping("editSighting")
public String getEditSightingInfo(HttpServletRequest request, Model model){
    int sightingId = Integer.parseInt(request.getParameter("id"));
    Sighting sighting = sightingDao.getSightingById(sightingId);
    List<Location> locations = locationDao.getAllLocations();
    List<Superperson> superpersons= superDao.getAllSuperpersons();
    
    model.addAttribute("sighting", sighting);
    model.addAttribute("locations", locations);
    model.addAttribute("superpersons", superpersons);
    
    return "editSighting";
}

@PostMapping("editSighting")
public String editSighting(@Valid Sighting sighting, BindingResult result, Model model, HttpServletRequest request){
    int locationId = Integer.parseInt(request.getParameter("locationId"));
    String[] superIds = request.getParameterValues("superId");
    
    Location location = locationDao.getLocationById(locationId);
    sighting.setLocation(location);
    
    List<Superperson> superpersons = new ArrayList<>();
    if(superIds != null){
    for(String superId:superIds){
        Superperson superperson = superDao.getSuperpersonById(Integer.parseInt(superId));
        superpersons.add(superperson);
    }
    }else{
        FieldError fieldError = new FieldError("sighting", "superpersons", "You must select one Super");
        result.addError(fieldError);
    }
    
    sighting.setSuperpersons(superpersons);
    
    if(result.hasErrors()){
        model.addAttribute("sighting", sighting);
        model.addAttribute("superpersons", superDao.getAllSuperpersons());
        model.addAttribute("locations", locationDao.getAllLocations());
        return "editSighting";
    }
    
    sightingDao.updateSighting(sighting);
    return "redirect:/sightings";
}

}
