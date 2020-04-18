/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author jenni
 */
public class Sighting {
    private int id;
    @PastOrPresent(message ="Date/Time must be in the present or in the past")
    @DateTimeFormat(pattern= "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime sightingTime;
    private Location location;
    private List<Superperson> superpersons;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getSightingTime() {
        return sightingTime;
    }

    public void setSightingTime(LocalDateTime sightingTime) {
        this.sightingTime = sightingTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Superperson> getSuperpersons() {
        return superpersons;
    }

    public void setSuperpersons(List<Superperson> superpersons) {
        this.superpersons = superpersons;
    }
    
    public String getSightingCoords(){
        return "https://www.bing.com/maps/embed?h=400&w=425&cp="+ location.getLatitude() +"~"+location.getLongitude()+"&lvl=11&typ=d&sty=r&src=SHELL&FORM=MBEDV8";
    }
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.sightingTime);
        hash = 97 * hash + Objects.hashCode(this.location);
        hash = 97 * hash + Objects.hashCode(this.superpersons);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.sightingTime, other.sightingTime)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.superpersons, other.superpersons)) {
            return false;
        }
        return true;
    }
    
}
