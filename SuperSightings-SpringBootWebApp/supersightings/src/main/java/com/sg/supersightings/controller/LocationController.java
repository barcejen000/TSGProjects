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
public class LocationController {
    @Autowired
    LocationDao locationDao;
   
    @Autowired 
    SuperpersonDao superDao;
    
    @Autowired 
    OrganizationDao orgDao;
    
    @Autowired
    SightingDao sightingDao;
    
    
@GetMapping("locations")
public String displayLocations(Model model){
    List<Location> locations = locationDao.getAllLocations();
    
    model.addAttribute("locations", locations);
    model.addAttribute("location", new Location());
    
    return "locations";
}
    
@GetMapping("viewLocation")
public String displayLocation(Model model, HttpServletRequest request){
    int locationId = Integer.parseInt(request.getParameter("id"));
    Location location = locationDao.getLocationById(locationId);
    model.addAttribute("location", location);
    return "viewLocation";
}

@PostMapping("addLocation")
public String addLocation(@Valid Location location, BindingResult result, Model model){
   if(result.hasErrors()){
      model.addAttribute("locations", locationDao.getAllLocations());
      return "locations";
   }
   locationDao.addLocation(location);
   return "redirect:/locations";
}

@GetMapping("deleteLocation")
public String deleteLocation(Integer id){
   locationDao.deleteLocationById(id);
   return "redirect:/locations";
}

@GetMapping("editLocation")
public String getEditLocationInfo(HttpServletRequest request, Model model){
    int locationId = Integer.parseInt(request.getParameter("id"));
    Location location = locationDao.getLocationById(locationId);    
    model.addAttribute("location", location);
    
    return "editLocation";
}

@PostMapping("editLocation")
public String editLocation(@Valid Location location, BindingResult result){
    if(result.hasErrors()){
        return "editLocation";
    }
    locationDao.updateLocation(location);
    return "redirect:/locations";
}
}
