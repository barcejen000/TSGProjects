/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.entities.Location;
import com.sg.supersightings.entities.Organization;
import com.sg.supersightings.entities.Sighting;
import com.sg.supersightings.entities.Superperson;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author jenni
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuperpersonDaoDBTest {
    @Autowired
    LocationDao locationDao;
   
    @Autowired 
    SuperpersonDao superDao;
    
    @Autowired 
    OrganizationDao orgDao;
    
    @Autowired
    SightingDao sightingDao;
    
    public SuperpersonDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Location> locations = locationDao.getAllLocations();
        for(Location location: locations){
            locationDao.deleteLocationById(location.getId());
        }
        
        List<Superperson> superpersons = superDao.getAllSuperpersons();
        for(Superperson superperson:superpersons){
            superDao.deleteSuperpersonById(superperson.getId());
        }
        
        List<Organization> orgs = orgDao.getAllOrganizations();
        for(Organization org: orgs){
            orgDao.deleteOrganizationById(org.getId());
        }
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting: sightings){
            sightingDao.deleteSightingById(sighting.getId());
        }
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of getAllSuperpersons method, of class SuperpersonDaoDB.
     */
    @Test
    public void testGetAllSuperpersons() {
        Superperson superperson = new Superperson();
        superperson.setName("Super Test Name");
        superperson.setType(true);
        superperson.setDescription("Super Test Description");
        superperson.setSuperpower("Test Superpower");
        superperson = superDao.addSuperperson(superperson);
        
        Superperson superperson2 = new Superperson();
        superperson2.setName("Super Test Name 2");
        superperson2.setType(false);
        superperson2.setDescription("Super Test Description 2");
        superperson2.setSuperpower("Test Superpower 2");
        superperson2 = superDao.addSuperperson(superperson2);
        
        List<Superperson> superpersons = superDao.getAllSuperpersons();
        assertEquals(2, superpersons.size());
        assertTrue(superpersons.contains(superperson));
        assertTrue(superpersons.contains(superperson2));
    }

    /**
     * Test of addSuperperson method, of class SuperpersonDaoDB.
     */
    @Test
    public void testAddAndGetSuperperson() {
        Superperson superperson = new Superperson();
        superperson.setName("Super Test Name");
        superperson.setType(true);
        superperson.setDescription("Super Test Description");
        superperson.setSuperpower("Test Superpower");
        superperson = superDao.addSuperperson(superperson);
        
        Superperson fromDao = superDao.getSuperpersonById(superperson.getId());
        assertEquals(fromDao, superperson);
        
    }

    /**
     * Test of updateSuperperson method, of class SuperpersonDaoDB.
     */
    @Test
    public void testUpdateSuperperson() {
        Superperson superperson = new Superperson();
        superperson.setName("Super Test Name");
        superperson.setType(true);
        superperson.setDescription("Super Test Description");
        superperson.setSuperpower("Test Superpower");
        superperson = superDao.addSuperperson(superperson);
        
        Superperson fromDao = superDao.getSuperpersonById(superperson.getId());
        assertEquals(fromDao, superperson);
        
        superperson.setSuperpower("Different Superpower");
        superDao.updateSuperperson(superperson);
        
        assertNotEquals(fromDao, superperson);
        
        fromDao = superDao.getSuperpersonById(superperson.getId());
        
        assertEquals(fromDao, superperson);
    
    }

    /**
     * Test of deleteSuperpersonById method, of class SuperpersonDaoDB.
     */
    @Test
    public void testDeleteSuperpersonById() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("123 Test Address City, State 12345");
        location.setLatitude(new BigDecimal("44.953700000"));
        location.setLongitude(new BigDecimal("-93.090000000"));
        location = locationDao.addLocation(location);
        
        Superperson superperson = new Superperson();
        superperson.setName("Super Test Name");
        superperson.setType(false);
        superperson.setDescription("Super Test Description");
        superperson.setSuperpower("Test Superpower");
        superperson = superDao.addSuperperson(superperson);
        
        List<Superperson> superpersons = new ArrayList<>();
        superpersons.add(superperson);
        
        Sighting sighting = new Sighting();
        sighting.setSightingTime(LocalDateTime.now().withNano(0));
        sighting.setLocation(location);
        sighting.setSuperpersons(superpersons);
        sighting = sightingDao.addSighting(sighting);
        
        List<Superperson> members = new ArrayList<>();
        members.add(superperson);
        
        Organization organization = new Organization();
        organization.setName("Organization Test Name");
        organization.setType(false);
        organization.setDescription("Organization Test Description");
        organization.setAddress("123 Test Organization Address City, State 12345");
        organization.setContact("999-999-9999");
        organization.setMembers(members);
        organization = orgDao.addOrganization(organization);
        
        superDao.deleteSuperpersonById(superperson.getId());
        
        Superperson fromDao = superDao.getSuperpersonById(superperson.getId());
        
        assertNull(fromDao);
        
    }
    
}
