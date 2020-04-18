/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.dao.LocationDaoDB.LocationMapper;
import com.sg.supersightings.dao.SuperpersonDaoDB.SuperpersonMapper;
import com.sg.supersightings.entities.Location;
import com.sg.supersightings.entities.Sighting;
import com.sg.supersightings.entities.Superperson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jenni
 */
@Repository
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) {
        try{ 
            final String SELECT_SIGHTING_BY_ID ="SELECT * FROM sighting WHERE id = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setLocation(getLocationForSighting(sighting));
            sighting.setSuperpersons(getSupersForSighting(sighting));
            return sighting;
            
        }catch(DataAccessException ex){
            return null;
        }
    }
    
    private Location getLocationForSighting(Sighting sighting){
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l "
                + "JOIN sighting s ON l.id = s.locationId WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), sighting.getId());
        
    }
    
    private List<Superperson> getSupersForSighting(Sighting sighting){
        final String SELECT_SUPERS_FOR_SIGHTING = "SELECT sp.* FROM superperson sp "
                + "JOIN super_sighting ss ON sp.id = ss.superId WHERE ss.sightingId = ?";
        
        return jdbc.query(SELECT_SUPERS_FOR_SIGHTING, new SuperpersonMapper(), sighting.getId());
        
    }
    
    @Override
    public List<Sighting> getLatest10Sightings(){
        final String SELECT_LAST_10_SIGHTINGS ="SELECT * FROM sighting ORDER BY sightingTime DESC LIMIT 10";
        
        List<Sighting> sightings = jdbc.query(SELECT_LAST_10_SIGHTINGS, new SightingMapper());
        
        associateLocationAndSupersToSightings(sightings);
        return sightings;
    }
    
    
    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        
        associateLocationAndSupersToSightings(sightings);
        return sightings;
    }
    
    private void associateLocationAndSupersToSightings(List<Sighting> sightings){
        for(Sighting sighting: sightings){
            sighting.setLocation(getLocationForSighting(sighting));
            sighting.setSuperpersons(getSupersForSighting(sighting));
        }
    }      
    
    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_NEW_SIGHTING = "INSERT INTO sighting(sightingTime, locationId) "
                + "VALUES(?,?)";
        jdbc.update(INSERT_NEW_SIGHTING,
                Timestamp.valueOf(sighting.getSightingTime()),
                sighting.getLocation().getId());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        
        insertIntoSuperSighting(sighting);
        
        return sighting;
    }
    
    //helper method to create the bridge table for super_sighting
    private void insertIntoSuperSighting(Sighting sighting){
        final String INSERT_INTO_SUPER_SIGHTING ="INSERT INTO super_sighting(superId, sightingId) "
                + "VALUES(?,?)";
        
       for(Superperson superperson: sighting.getSuperpersons()){
           jdbc.update(INSERT_INTO_SUPER_SIGHTING, superperson.getId(), sighting.getId());
       }
    }
    
    

    @Override
    @Transactional
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET sightingTime = ?, locationId = ? "
                + "WHERE id = ?";
        
        jdbc.update(UPDATE_SIGHTING, 
                Timestamp.valueOf(sighting.getSightingTime()),
                sighting.getLocation().getId(),
                sighting.getId());
        
        
        //delete existing bridge table entries
        jdbc.update("DELETE FROM super_sighting WHERE sightingId = ?", sighting.getId());
        //reinsert supers now associated with this sighting
        insertIntoSuperSighting(sighting);
    }

    @Override
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING_FROM_SUPER_SIGHTING = "DELETE FROM super_sighting "
                + "WHERE sightingId = ?";
        jdbc.update(DELETE_SIGHTING_FROM_SUPER_SIGHTING, id);
        
        final String DELETE_FROM_SIGHTING = "DELETE FROM sighting WHERE id = ?";
        jdbc.update(DELETE_FROM_SIGHTING, id);
    }

    @Override
    public List<Sighting> getSightingsForLocation(Location location) {
        final String SELECT_SIGHTINGS_FOR_LOCATION = "SELECT * FROM sighting "
                + "WHERE locationId = ? ";
        
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_LOCATION, new SightingMapper(), location.getId());
        
        associateLocationAndSupersToSightings(sightings);
        
        return sightings;
        
    }

    @Override
    public List<Sighting> getSightingsForSuperperson(Superperson superperson) {
        final String SELECT_SIGHTINGS_FOR_SUPER = "SELECT s.* from sighting s "
                + "JOIN super_sighting ss ON s.id = ss.sightingId WHERE ss.superId = ?";
        
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_SUPER, new SightingMapper(), 
                superperson.getId());
        
        associateLocationAndSupersToSightings(sightings);
        
        return sightings;
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("id"));
            sighting.setSightingTime(rs.getTimestamp("sightingTime").toLocalDateTime().withNano(0));
            return sighting;

        }

    }

}
