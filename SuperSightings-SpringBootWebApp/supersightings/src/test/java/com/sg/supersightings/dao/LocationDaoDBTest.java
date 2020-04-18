/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.sg.supersightings.entities.Location;
import com.sg.supersightings.entities.Organization;
import com.sg.supersightings.entities.Sighting;
import com.sg.supersightings.entities.Superperson;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author jenni
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationDaoDBTest {
    
    @Autowired
    LocationDao locationDao;
   
    @Autowired 
    SuperpersonDao superDao;
    
    @Autowired 
    OrganizationDao orgDao;
    
    @Autowired
    SightingDao sightingDao;
    
    
    public LocationDaoDBTest() {
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
     * Test of getAllLocations method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("123 Test Address City, State 12345");
        location.setLatitude(new BigDecimal("44.9537"));
        location.setLongitude(new BigDecimal("-93.0922"));
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setName("Test Name 2");
        location2.setDescription("Test Description 2");
        location2.setAddress("123 Test Address 2 City, State 12345");
        location2.setLatitude(new BigDecimal("66.2902000").stripTrailingZeros());
        location2.setLongitude(new BigDecimal("88.0730").stripTrailingZeros());
        location2 = locationDao.addLocation(location2);
        
        List<Location> locations = locationDao.getAllLocations();
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
        
    }

    /**
     * Test of addLocation method, of class LocationDaoDB.
     */
    @Test
    public void testAddAndGetLocation() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("123 Test Address City, State 12345");
        location.setLatitude(new BigDecimal("44.9537"));
        location.setLongitude(new BigDecimal("-93.0922"));
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
    }

    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("123 Test Address City, State 12345");
        location.setLatitude(new BigDecimal("44.9537"));
        location.setLongitude(new BigDecimal("-93.0933"));
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        
        assertEquals(location, fromDao);
        
        location.setName("Different Test Name");
        locationDao.updateLocation(location);
        
        assertNotEquals(location, fromDao);
        
        fromDao = locationDao.getLocationById(location.getId());
        
        assertEquals(location, fromDao);
        
    }

    /**
     * Test of deleteLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocationById() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("123 Test Address City, State 12345");
        location.setLatitude(new BigDecimal("44.9537"));
        location.setLongitude(new BigDecimal("-93.0900"));
        location = locationDao.addLocation(location);
        
        Superperson superperson = new Superperson();
        superperson.setName("Super Test Name");
        superperson.setType(true);
        superperson.setDescription("Super Test Description");
        superperson.setSuperpower("Test Superpower");
        superperson = superDao.addSuperperson(superperson);
        
        List<Superperson> superpersons = new ArrayList<>();
        superpersons.add(superperson);
        
        Sighting sighting = new Sighting();
        sighting.setSightingTime(LocalDateTime.now());
        sighting.setLocation(location);
        sighting.setSuperpersons(superpersons);
        sighting = sightingDao.addSighting(sighting);
        
        locationDao.deleteLocationById(location.getId());
        
        Location fromDao = locationDao.getLocationById(location.getId());
        
        assertNull(fromDao);
        
    }
    
}
