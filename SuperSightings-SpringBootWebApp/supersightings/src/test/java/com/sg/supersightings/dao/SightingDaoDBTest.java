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
import java.time.format.DateTimeFormatter;
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
public class SightingDaoDBTest {
    
    @Autowired
    LocationDao locationDao;
   
    @Autowired 
    SuperpersonDao superDao;
    
    @Autowired 
    OrganizationDao orgDao;
    
    @Autowired
    SightingDao sightingDao;
    
    public SightingDaoDBTest() {
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
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("281 Test Ave");
        location.setLatitude(new BigDecimal("-89.29938"));
        location.setLongitude(new BigDecimal("23.292"));
        location = locationDao.addLocation(location);
        
        Superperson superperson = new Superperson();
        superperson.setName("Test Superhero Name");
        superperson.setType(true);
        superperson.setDescription("Test description for superhero");
        superperson.setSuperpower("Test Superhero power");
        superperson = superDao.addSuperperson(superperson);
        
        List<Superperson> supers = new ArrayList<>();
        supers.add(superperson);
                
        Sighting sighting1 = new Sighting();
        sighting1.setSightingTime(LocalDateTime.now().withNano(0));
        sighting1.setLocation(location);
        sighting1.setSuperpersons(supers);
        sighting1 = sightingDao.addSighting(sighting1);
        
        Sighting sighting2 = new Sighting();
        sighting2.setSightingTime(LocalDateTime.now().withNano(0));
        sighting2.setLocation(location);
        sighting2.setSuperpersons(supers);
        sighting2 = sightingDao.addSighting(sighting2);
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting1));
        assertTrue(sightings.contains(sighting2));
        
    }

    /**
     * Test of addSighting method, of class SightingDaoDB.
     */
    @Test
    public void testAddAndGetSighting() {
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("8292 Test Dr.");
        location.setLatitude(new BigDecimal("75.9392022"));
        location.setLongitude(new BigDecimal("96.292339"));
        location = locationDao.addLocation(location);
        
        Superperson superperson = new Superperson();
        superperson.setName("Test Supervillain Name");
        superperson.setType(false);
        superperson.setDescription("Test description for supervillain");
        superperson.setSuperpower("Test Supervillain power");
        superperson = superDao.addSuperperson(superperson);
        
        List<Superperson> supers = new ArrayList<>();
        supers.add(superperson);
                
        Sighting sighting = new Sighting();
        sighting.setSightingTime(LocalDateTime.now().withNano(0));
        sighting.setLocation(location);
        sighting.setSuperpersons(supers);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        
        assertEquals(fromDao, sighting);
        
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("8292 Test Dr.");
        location.setLatitude(new BigDecimal("75.9392022"));
        location.setLongitude(new BigDecimal("96.292339"));
        location = locationDao.addLocation(location);
        
        Superperson superperson = new Superperson();
        superperson.setName("Test Supervillain Name");
        superperson.setType(false);
        superperson.setDescription("Test description for supervillain");
        superperson.setSuperpower("Test Supervillain power");
        superperson = superDao.addSuperperson(superperson);
        
        List<Superperson> supers = new ArrayList<>();
        supers.add(superperson);
                
        Sighting sighting = new Sighting();
        sighting.setSightingTime(LocalDateTime.now().withNano(0));
        sighting.setLocation(location);
        sighting.setSuperpersons(supers);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        
        assertEquals(fromDao, sighting);
        
        Location location2 = new Location();
        location2.setName("Test Location Name 2");
        location2.setDescription("Test Location Description 2");
        location2.setAddress("83739 Test Lane Minneapolis, MN");
        location2.setLatitude(new BigDecimal("-34.28"));
        location2.setLongitude(new BigDecimal("-45.291"));
        location2 = locationDao.addLocation(location2);
        
        //updating location
        sighting.setLocation(location2);
        sightingDao.updateSighting(sighting);
        
        assertNotEquals(fromDao, sighting);
        
        fromDao = sightingDao.getSightingById(sighting.getId());
        
        assertEquals(fromDao, sighting);
        
        //added new test for updating sightees
        
        Superperson superperson2 = new Superperson();
        superperson2.setName("Test Supervillain Name");
        superperson2.setType(false);
        superperson2.setDescription("Test description for supervillain");
        superperson2.setSuperpower("Test Supervillain power");
        superperson2 = superDao.addSuperperson(superperson2);
        
        Superperson superperson3 = new Superperson();
        superperson3.setName("Test Superhero Name");
        superperson3.setType(true);
        superperson3.setDescription("Test description for superhero");
        superperson3.setSuperpower("Test superhero power");
        superperson3 = superDao.addSuperperson(superperson3);
        
        // addding new supers to list of supers
        supers.add(superperson2);
        supers.add(superperson3);
        
        sighting.setSuperpersons(supers);
        sightingDao.updateSighting(sighting);
        
        assertNotEquals(fromDao, sighting);
        
        fromDao = sightingDao.getSightingById(sighting.getId());
        
        assertEquals(fromDao,sighting);
        
    }

    /**
     * Test of deleteSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSightingById() {
        Location location = new Location();
        location.setName("Test Location Name");
        location.setDescription("Test Location Description");
        location.setAddress("8292 Test St. Test,Test 12345");
        location.setLatitude(new BigDecimal("929.4673"));
        location.setLongitude(new BigDecimal("-242.2839"));
        location = locationDao.addLocation(location);
        
        Superperson superperson = new Superperson();
        superperson.setName("Test Superhero Name");
        superperson.setType(true);
        superperson.setDescription("Test description for superhero");
        superperson.setSuperpower("Test superpower");
        superperson = superDao.addSuperperson(superperson);
        
        List<Superperson> supers = new ArrayList<>();
        supers.add(superperson);
                
        Sighting sighting = new Sighting();
        sighting.setSightingTime(LocalDateTime.now().withNano(0));
        sighting.setLocation(location);
        sighting.setSuperpersons(supers);
        sighting = sightingDao.addSighting(sighting);
        
        sightingDao.deleteSightingById(sighting.getId());
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        
        assertNull(fromDao);
        
    }

    /**
     * Test of getSightingsForLocation method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsForLocation() {
        Superperson superperson1 = new Superperson();
        superperson1.setName("Batman");
        superperson1.setType(true);
        superperson1.setDescription("Protector of Gotham");
        superperson1.setSuperpower("no superpowers, relies on gear");
        superperson1 = superDao.addSuperperson(superperson1);
        
        Superperson superperson2 = new Superperson();
        superperson2.setName("Joker");
        superperson2.setType(false);
        superperson2.setDescription("Supervillain with a clown-like appearance, criminal in Gotham");
        superperson2.setSuperpower("No powers, insanity");
        superperson2 = superDao.addSuperperson(superperson2);
        
        Superperson superperson3 = new Superperson();
        superperson3.setName("Spiderman");
        superperson3.setType(true);
        superperson3.setDescription("Has spider-like abilities");
        superperson3.setSuperpower("Spider-senses, great reflexes, can wall-crawl");
        superperson3 = superDao.addSuperperson(superperson3);
        
        Location location1 = new Location();
        location1.setName("Test Location Name");
        location1.setDescription("Test Location Description");
        location1.setAddress("8292 Test St. Test,Test 12345");
        location1.setLatitude(new BigDecimal("929.4673"));
        location1.setLongitude(new BigDecimal("-242.2839"));
        location1 = locationDao.addLocation(location1);
        
        Location location2 = new Location();
        location2.setName("Test Location Name 2");
        location2.setDescription("Test Location Description 2");
        location2.setAddress("83739 Test Lane Minneapolis, MN");
        location2.setLatitude(new BigDecimal("-34.28"));
        location2.setLongitude(new BigDecimal("-45.291"));
        location2 = locationDao.addLocation(location2);
        
        List<Superperson> supersSeenAtSight1 = new ArrayList<>();
        supersSeenAtSight1.add(superperson1);
        supersSeenAtSight1.add(superperson2);
        
        Sighting sighting1 = new Sighting();
        sighting1.setSightingTime(LocalDateTime.now().withNano(0));
        sighting1.setLocation(location1);
        sighting1.setSuperpersons(supersSeenAtSight1);
        sighting1 = sightingDao.addSighting(sighting1);
        
        List<Superperson> supersSeenAtSight2 = new ArrayList<>();
        supersSeenAtSight2.add(superperson1);
        supersSeenAtSight2.add(superperson3);
        
        Sighting sighting2 = new Sighting();
        sighting2.setSightingTime(LocalDateTime.now().withNano(0));
        sighting2.setLocation(location1);
        sighting2.setSuperpersons(supersSeenAtSight2);
        sighting2 = sightingDao.addSighting(sighting2);
        
        List<Superperson> supersSeenAtSight3 = new ArrayList<>();
        supersSeenAtSight3.add(superperson2);
        
        Sighting sighting3 = new Sighting();
        sighting3.setSightingTime(LocalDateTime.now().withNano(0));
        sighting3.setLocation(location2);
        sighting3.setSuperpersons(supersSeenAtSight3);
        sighting3 = sightingDao.addSighting(sighting3);
        
        List<Sighting> sightingsForLocation1 = sightingDao.getSightingsForLocation(location1);
        assertEquals(2,sightingsForLocation1.size());
        assertTrue(sightingsForLocation1.contains(sighting1));
        assertTrue(sightingsForLocation1.contains(sighting2));
        assertFalse(sightingsForLocation1.contains(sighting3));
        
        List<Sighting> sightingsForLocation2 = sightingDao.getSightingsForLocation(location2);
        assertEquals(1,sightingsForLocation2.size());
        assertFalse(sightingsForLocation2.contains(sighting1));
        assertFalse(sightingsForLocation2.contains(sighting2));
        assertTrue(sightingsForLocation2.contains(sighting3));
               
    }

    /**
     * Test of getSightingsForSuperperson method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsForSuperperson() {
        Superperson superperson1 = new Superperson();
        superperson1.setName("Batman");
        superperson1.setType(true);
        superperson1.setDescription("Protector of Gotham");
        superperson1.setSuperpower("no superpowers, relies on gear");
        superperson1 = superDao.addSuperperson(superperson1);
        
        Superperson superperson2 = new Superperson();
        superperson2.setName("Joker");
        superperson2.setType(false);
        superperson2.setDescription("Supervillain with a clown-like appearance, criminal in Gotham");
        superperson2.setSuperpower("No powers, insanity");
        superperson2 = superDao.addSuperperson(superperson2);
        
        Superperson superperson3 = new Superperson();
        superperson3.setName("Spiderman");
        superperson3.setType(true);
        superperson3.setDescription("Has spider-like abilities");
        superperson3.setSuperpower("Spider-senses, great reflexes, can wall-crawl");
        superperson3 = superDao.addSuperperson(superperson3);
        
        Location location1 = new Location();
        location1.setName("Test Location Name");
        location1.setDescription("Test Location Description");
        location1.setAddress("8292 Test St. Test,Test 12345");
        location1.setLatitude(new BigDecimal("35.3921"));
        location1.setLongitude(new BigDecimal("-12.3864"));
        location1 = locationDao.addLocation(location1);
        
        Location location2 = new Location();
        location2.setName("Test Location Name 2");
        location2.setDescription("Test Location Description 2");
        location2.setAddress("83739 Test Lane Minneapolis, MN");
        location2.setLatitude(new BigDecimal("-56.3922"));
        location2.setLongitude(new BigDecimal("-23.1437"));
        location2 = locationDao.addLocation(location2);
        
        List<Superperson> supersSeenAtSight1 = new ArrayList<>();
        supersSeenAtSight1.add(superperson1);
        supersSeenAtSight1.add(superperson2);
        
        Sighting sighting1 = new Sighting();
        sighting1.setSightingTime(LocalDateTime.now().withNano(0));
        sighting1.setLocation(location1);
        sighting1.setSuperpersons(supersSeenAtSight1);
        sighting1 = sightingDao.addSighting(sighting1);
        
        List<Superperson> supersSeenAtSight2 = new ArrayList<>();
        supersSeenAtSight2.add(superperson3);
        
        Sighting sighting2 = new Sighting();
        sighting2.setSightingTime(LocalDateTime.now().withNano(0));
        sighting2.setLocation(location2);
        sighting2.setSuperpersons(supersSeenAtSight2);
        sighting2 = sightingDao.addSighting(sighting2);
        
        List<Superperson> supersSeenAtSight3 = new ArrayList<>();
        supersSeenAtSight3.add(superperson2);
        
        Sighting sighting3 = new Sighting();
        sighting3.setSightingTime(LocalDateTime.now().withNano(0));
        sighting3.setLocation(location1);
        sighting3.setSuperpersons(supersSeenAtSight3);
        sighting3 = sightingDao.addSighting(sighting3);
        
        List<Sighting> sightingsForSP1 = sightingDao.getSightingsForSuperperson(superperson1);
        assertEquals(1, sightingsForSP1.size());
        assertTrue(sightingsForSP1.contains(sighting1));
        assertFalse(sightingsForSP1.contains(sighting2));
        assertFalse(sightingsForSP1.contains(sighting3));
        
        List<Sighting> sightingsForSP2 = sightingDao.getSightingsForSuperperson(superperson2);
        assertEquals(2, sightingsForSP2.size());
        assertTrue(sightingsForSP2.contains(sighting1));
        assertFalse(sightingsForSP2.contains(sighting2));
        assertTrue(sightingsForSP2.contains(sighting3));
        
        List<Sighting> sightingsForSP3 = sightingDao.getSightingsForSuperperson(superperson3);
        assertEquals(1, sightingsForSP3.size());
        assertFalse(sightingsForSP3.contains(sighting1));
        assertTrue(sightingsForSP3.contains(sighting2));
        assertFalse(sightingsForSP3.contains(sighting3));

    }
    
    /**
     * Test of getLatest10Sightings method, of class SightingDaoDB.
     */
    @Test
     public void getLatest10Sightings(){
        Superperson superperson1 = new Superperson();
        superperson1.setName("Batman");
        superperson1.setType(true);
        superperson1.setDescription("Protector of Gotham");
        superperson1.setSuperpower("no superpowers, relies on gear");
        superperson1 = superDao.addSuperperson(superperson1);
        
        Location location1 = new Location();
        location1.setName("Test Location Name");
        location1.setDescription("Test Location Description");
        location1.setAddress("8292 Test St. Test,Test 12345");
        location1.setLatitude(new BigDecimal("35.3921"));
        location1.setLongitude(new BigDecimal("-12.3864"));
        location1 = locationDao.addLocation(location1);
        
        List<Superperson> supers= new ArrayList<>();
        supers.add(superperson1);
        
        
        Sighting sighting1 = new Sighting();
        String sightTiming1 = "2020-03-01T04:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime date1 = LocalDateTime.parse(sightTiming1, formatter);
        sighting1.setSightingTime(date1);
        sighting1.setLocation(location1);
        sighting1.setSuperpersons(supers);
        sightingDao.addSighting(sighting1);
        
        Sighting sighting2 = new Sighting();
        String sightTiming2 = "2020-03-02T04:00:00";
        LocalDateTime date2 = LocalDateTime.parse(sightTiming2, formatter);
        sighting2.setSightingTime(date2);
        sighting2.setLocation(location1);
        sighting2.setSuperpersons(supers);
        sightingDao.addSighting(sighting2);
        
        Sighting sighting3 = new Sighting();
        String sightTiming3 = "2020-03-03T04:00:00";
        LocalDateTime date3 = LocalDateTime.parse(sightTiming3, formatter);
        sighting3.setSightingTime(date3);
        sighting3.setLocation(location1);
        sighting3.setSuperpersons(supers);
        sightingDao.addSighting(sighting3);
        
        Sighting sighting4 = new Sighting();
        String sightTiming4 = "2020-03-04T04:00:00";
        LocalDateTime date4 = LocalDateTime.parse(sightTiming4, formatter);
        sighting4.setSightingTime(date4);
        sighting4.setLocation(location1);
        sighting4.setSuperpersons(supers);
        sightingDao.addSighting(sighting4);
        
        Sighting sighting5 = new Sighting();
        String sightTiming5 = "2020-03-05T04:00:00";
        LocalDateTime date5 = LocalDateTime.parse(sightTiming5, formatter);
        sighting5.setSightingTime(date5);
        sighting5.setLocation(location1);
        sighting5.setSuperpersons(supers);
        sightingDao.addSighting(sighting5);
        
        Sighting sighting6 = new Sighting();
        String sightTiming6 = "2020-03-06T04:00:00";
        LocalDateTime date6 = LocalDateTime.parse(sightTiming6, formatter);
        sighting6.setSightingTime(date6);
        sighting6.setLocation(location1);
        sighting6.setSuperpersons(supers);
        sightingDao.addSighting(sighting6);
        
        Sighting sighting7 = new Sighting();
        String sightTiming7 = "2020-03-07T04:00:00";
        LocalDateTime date7 = LocalDateTime.parse(sightTiming7, formatter);
        sighting7.setSightingTime(date7);
        sighting7.setLocation(location1);
        sighting7.setSuperpersons(supers);
        sightingDao.addSighting(sighting7);
        
        Sighting sighting8 = new Sighting();
        String sightTiming8 = "2020-03-08T04:00:00";
        LocalDateTime date8 = LocalDateTime.parse(sightTiming8, formatter);
        sighting8.setSightingTime(date3);
        sighting8.setLocation(location1);
        sighting8.setSuperpersons(supers);
        sightingDao.addSighting(sighting8);
        
        Sighting sighting9 = new Sighting();
        String sightTiming9 = "2020-03-09T04:00:00";
        LocalDateTime date9 = LocalDateTime.parse(sightTiming9, formatter);
        sighting9.setSightingTime(date9);
        sighting9.setLocation(location1);
        sighting9.setSuperpersons(supers);
        sightingDao.addSighting(sighting9);
        
        Sighting sighting10= new Sighting();
        String sightTiming10 = "2020-03-10T04:00:00";
        LocalDateTime date10 = LocalDateTime.parse(sightTiming10, formatter);
        sighting10.setSightingTime(date10);        
        sighting10.setLocation(location1);
        sighting10.setSuperpersons(supers);
        sightingDao.addSighting(sighting10);
        
        Sighting sighting11= new Sighting();
        String sightTiming11 = "2020-01-01T04:00:00";
        LocalDateTime date11 = LocalDateTime.parse(sightTiming11, formatter);
        sighting11.setSightingTime(date11); 
        sighting11.setLocation(location1);
        sighting11.setSuperpersons(supers);
        sighting11 = sightingDao.addSighting(sighting11);
         
        
        List<Sighting> sightings = sightingDao.getLatest10Sightings();
        assertEquals(10, sightings.size());
        assertFalse(sightings.contains(sighting11));
        
     }
}
