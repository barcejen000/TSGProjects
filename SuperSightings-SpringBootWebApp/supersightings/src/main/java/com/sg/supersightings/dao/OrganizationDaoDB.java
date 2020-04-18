/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.dao.SuperpersonDaoDB.SuperpersonMapper;
import com.sg.supersightings.entities.Organization;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) {
        try {
          final String SELECT_ORG_BY_ID ="SELECT * FROM organization WHERE id = ?";
          Organization org = jdbc.queryForObject(SELECT_ORG_BY_ID, new OrganizationMapper(), id);
          org.setMembers(getSuperpersonsForOrganization(org));
          return org;
        }catch(DataAccessException ex){
            return null;
        }

    }

    private List<Superperson> getSuperpersonsForOrganization(Organization organization) {
        final String SELECT_SUPERPERSONS_FOR_ORG = "SELECT s.* FROM superperson s "
                + "JOIN super_organization so ON s.id = so.superId WHERE so.organizationId = ?";
        return jdbc.query(SELECT_SUPERPERSONS_FOR_ORG, new SuperpersonMapper(), organization.getId());
    }

    
    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGS = "SELECT * FROM organization";
        List<Organization> orgs = jdbc.query(SELECT_ALL_ORGS, new OrganizationMapper());
        
        associateSuperpersonsWithOrganization(orgs);
        
        return orgs;
    }
    
    private void associateSuperpersonsWithOrganization(List<Organization> organizations) {
        for (Organization org : organizations) {
            org.setMembers(getSuperpersonsForOrganization(org));
        }
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String INSERT_NEW_ORG = "INSERT INTO organization(name, type, description, address, contact) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_NEW_ORG,
                organization.getName(),
                organization.isType(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContact());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);
        insertIntoSuperOrganization(organization);
        
        return organization;
        
    }
    
    
    //helper method to create the bridge table for super_organization
    private void insertIntoSuperOrganization(Organization organization){
        final String INSERT_INTO_SUPER_ORGANIZATION = "INSERT INTO super_organization"
                + "(organizationId, superId) VALUES (?,?)";
        for(Superperson superperson : organization.getMembers()){
            jdbc.update(INSERT_INTO_SUPER_ORGANIZATION,
                    organization.getId(),
                    superperson.getId());
        }
                
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORG = "UPDATE organization SET name = ?, type = ?, description = ?, "
                + "address = ?, contact = ? WHERE id = ?";
        
        jdbc.update(UPDATE_ORG,
                organization.getName(),
                organization.isType(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContact(),
                organization.getId());
        
        //delete existing bridge table entries
        final String DELETE_FROM_SUPER_ORG = "DELETE FROM super_organization WHERE organizationId = ?";
        jdbc.update(DELETE_FROM_SUPER_ORG, organization.getId());
        //reinsert supers now associated with this org
        insertIntoSuperOrganization(organization);
    }

    @Override
    public void deleteOrganizationById(int id) {
        final String DELETE_FROM_SUPER_ORG = "DELETE FROM super_organization WHERE organizationId = ?";
        jdbc.update(DELETE_FROM_SUPER_ORG, id);
        
        final String DELETE_FROM_ORG = "DELETE FROM organization WHERE id = ?";
        jdbc.update(DELETE_FROM_ORG, id);
        
    }

    @Override
    public List<Organization> getOrganizationsForSuperperson(Superperson superperson) {
        final String SELECT_ORGS_FOR_SUPERPERSON = "SELECT o.* FROM organization o "
                + "JOIN super_organization so ON o.id = so.organizationId WHERE so.superId = ?";
        List<Organization> orgs = jdbc.query(SELECT_ORGS_FOR_SUPERPERSON, new OrganizationMapper(), superperson.getId());
        
        associateSuperpersonsWithOrganization(orgs); 
        return orgs; 
        
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization org = new Organization();
            org.setId(rs.getInt("id"));
            org.setName(rs.getString("name"));
            org.setType(rs.getBoolean("type"));
            org.setDescription(rs.getString("description"));
            org.setAddress(rs.getString("address"));
            org.setContact(rs.getString("contact"));

            return org;
        }

    }

}
