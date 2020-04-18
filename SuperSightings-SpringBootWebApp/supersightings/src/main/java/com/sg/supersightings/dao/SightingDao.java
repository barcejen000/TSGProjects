/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.entities.Location;
import com.sg.supersightings.entities.Sighting;
import com.sg.supersightings.entities.Superperson;
import java.util.List;

/**
 *
 * @author jenni
 */
public interface SightingDao {
    Sighting getSightingById(int id);
    List<Sighting> getLatest10Sightings();
    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
    
    List<Sighting> getSightingsForLocation(Location location);
    List<Sighting> getSightingsForSuperperson(Superperson superperson);
    
}
