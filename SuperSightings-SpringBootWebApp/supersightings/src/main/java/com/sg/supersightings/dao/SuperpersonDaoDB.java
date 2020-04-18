/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.entities.Superperson;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class SuperpersonDaoDB implements SuperpersonDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superperson getSuperpersonById(int id) {
        try{ 
            final String SELECT_SUPER_BY_ID = "SELECT * FROM superperson WHERE id = ?";
            return jdbc.queryForObject(SELECT_SUPER_BY_ID, new SuperpersonMapper(), id);
            
        }catch(DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Superperson> getAllSuperpersons() {
        final String SELECT_ALL_SUPERS = "SELECT * FROM superperson";
        return jdbc.query(SELECT_ALL_SUPERS, new SuperpersonMapper());
    }

    @Override
    @Transactional
    public Superperson addSuperperson(Superperson superperson) {
        final String INSERT_NEW_SUPER = "INSERT INTO superperson(name, type, description, superpower) "
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_NEW_SUPER,
                superperson.getName(),
                superperson.isType(),
                superperson.getDescription(),
                superperson.getSuperpower());
//                superperson.getPicLink());
            
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superperson.setId(newId);
        return superperson;
        
    }

    @Override
    public void updateSuperperson(Superperson superperson) {
        final String UPDATE_SUPER = "UPDATE superperson SET name = ?, type = ?, description = ?, superpower = ?, picLink = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_SUPER,
                superperson.getName(),
                superperson.isType(),
                superperson.getDescription(),
                superperson.getSuperpower(),
                superperson.getPicLink(),
                superperson.getId());
    }

    @Override
    @Transactional
    public void deleteSuperpersonById(int id) {
       final String DELETE_FROM_SUPER_ORG = "DELETE FROM super_organization WHERE superId = ?";
       jdbc.update(DELETE_FROM_SUPER_ORG, id);
       final String DELETE_FROM_SUPER_SIGHTING = "DELETE FROM super_sighting WHERE superId = ?";
       jdbc.update(DELETE_FROM_SUPER_SIGHTING, id);
       final String DELETE_FROM_SUPER ="DELETE FROM superperson WHERE id = ?";
       jdbc.update(DELETE_FROM_SUPER, id);
    }
    
    public static final class SuperpersonMapper implements RowMapper<Superperson>{

        @Override
        public Superperson mapRow(ResultSet rs, int index) throws SQLException {
            
            Superperson superperson = new Superperson();
            superperson.setId(rs.getInt("id"));
            superperson.setName(rs.getString("name"));
            superperson.setType(rs.getBoolean("type"));
            superperson.setDescription(rs.getString("description"));
            superperson.setSuperpower(rs.getString("superpower"));
            superperson.setPicLink(rs.getString("picLink"));
            return superperson;
        }
        
    }
}
