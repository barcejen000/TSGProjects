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
public class OrganizationDaoDBTest {
    
    @Autowired
    LocationDao locationDao;
   
    @Autowired 
    SuperpersonDao superDao;
    
    @Autowired 
    OrganizationDao orgDao;
    
    @Autowired
    SightingDao sightingDao;
    
    public OrganizationDaoDBTest() {
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
     * Test of getAllOrganizations method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrganizations() {
        Superperson superperson = new Superperson();
        superperson.setName("Super Test Name");
        superperson.setType(true);
        superperson.setDescription("Super Test Description");
        superperson.setSuperpower("Test Superpower");
        superperson = superDao.addSuperperson(superperson);  
        
        List<Superperson> members = new ArrayList<>();
        members.add(superperson);
        
        Organization organization = new Organization();
        organization.setName("Organization Test Name");
        organization.setType(true);
        organization.setDescription("Organization Test Description");
        organization.setAddress("902 Test Organization Address City, State 12345");
        organization.setContact("(111)222-3333");
        organization.setMembers(members);
        organization = orgDao.addOrganization(organization);
        
        Organization organization2 = new Organization();
        organization2.setName("Organization Test Name 2");
        organization2.setType(true);
        organization2.setDescription("Organization Test Description 2");
        organization2.setAddress("833 Test Organization Address 2 City, State 55075");
        organization2.setContact("1-800-123-1234");
        organization2.setMembers(members);
        organization2 = orgDao.addOrganization(organization2);
        
        List<Organization> orgs = orgDao.getAllOrganizations();
        
        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(organization));
        assertTrue(orgs.contains(organization2));
    }

    /**
     * Test of addOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testAddAndGetOrganization() {
        Superperson superperson = new Superperson();
        superperson.setName("Super Test Name");
        superperson.setType(true);
        superperson.setDescription("Super Test Description");
        superperson.setSuperpower("Test Superpower");
        superperson = superDao.addSuperperson(superperson); 
        
        List<Superperson> members = new ArrayList<>();
        members.add(superperson);
        
        Organization organization = new Organization();
        organization.setName("Organization Test Name");
        organization.setType(true);
        organization.setDescription("Organization Test Description");
        organization.setAddress("902 Test Organization Address City, State 12345");
        organization.setContact("(111)2222-3333");
        organization.setMembers(members);
        organization = orgDao.addOrganization(organization);
        
        Organization fromDao = orgDao.getOrganizationById(organization.getId());
        
        assertEquals(fromDao, organization);
    }

    /**
     * Test of updateOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testUpdateOrganization() {
        Superperson superperson = new Superperson();
        superperson.setName("Super Test Name");
        superperson.setType(false);
        superperson.setDescription("Super Test Description");
        superperson.setSuperpower("Test Superpower");
        superperson = superDao.addSuperperson(superperson);
        
        List<Superperson> members = new ArrayList<>();
        members.add(superperson);
   
        Organization organization = new Organization();
        organization.setName("Organization Test Name");
        organization.setType(false);
        organization.setDescription("Organization Test Description");
        organization.setAddress("123 Test Organization Address City, State 77088");
        organization.setContact("832-292-1829");
        organization.setMembers(members);
        organization = orgDao.addOrganization(organization);
        
        Organization fromDao = orgDao.getOrganizationById(organization.getId());
        assertEquals(fromDao, organization);
        
        organization.setAddress("12345 Update Organization Address ChangeCity, State 55075");
        orgDao.updateOrganization(organization);
        
        assertNotEquals(fromDao, organization);
        
        fromDao =orgDao.getOrganizationById(organization.getId());
        assertEquals(fromDao, organization);
        
        Superperson superperson2 = new Superperson();
        superperson2.setName("Super Test Name 2");
        superperson2.setType(false);
        superperson2.setDescription("Super Test Description 2");
        superperson2.setSuperpower("Test Superpower 2");
        superperson2 = superDao.addSuperperson(superperson2);
        
        Superperson superperson3 = new Superperson();
        superperson3.setName("Super Test Name 3");
        superperson3.setType(true);
        superperson3.setDescription("Super Test Description 3");
        superperson3.setSuperpower("Test Superpower 3");
        superperson3 = superDao.addSuperperson(superperson3);
        
        //added test- update org members 
        List<Superperson> newMembers = new ArrayList<>();
        newMembers.add(superperson2);
        newMembers.add(superperson3);
        
        organization.setMembers(newMembers);
        orgDao.updateOrganization(organization);
        
        assertNotEquals(fromDao, organization);
        
        fromDao = orgDao.getOrganizationById(organization.getId());
        
        assertEquals(fromDao, organization);
        
    }

    /**
     * Test of deleteOrganizationById method, of class OrganizationDaoDB.
     */
    @Test
    public void testDeleteOrganizationById() { 
        Superperson superperson = new Superperson();
        superperson.setName("Super Test Name");
        superperson.setType(true);
        superperson.setDescription("Super Test Description");
        superperson.setSuperpower("Test Superpower");
        superperson = superDao.addSuperperson(superperson);
        
        List<Superperson> members = new ArrayList<>();
        members.add(superperson);
   
        Organization organization = new Organization();
        organization.setName("Organization Test Name");
        organization.setType(true);
        organization.setDescription("Organization Test Description");
        organization.setAddress("123 Test Organization Address City, State 12345");
        organization.setContact("666-9999");
        organization.setMembers(members);
        organization = orgDao.addOrganization(organization);
        
        orgDao.deleteOrganizationById(organization.getId());
        
        Organization fromDao = orgDao.getOrganizationById(organization.getId());
        
        assertNull(fromDao);
     
    }

    /**
     * Test of getOrganizationsForSuperperson method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetOrganizationsForSuperperson() {
        Superperson superperson1 = new Superperson();
        superperson1.setName("Batman");
        superperson1.setType(true);
        superperson1.setDescription("Protector of Gotham");
        superperson1.setSuperpower("no superpowers, relies on gear");
        superperson1 = superDao.addSuperperson(superperson1);
        
        Superperson superperson2 = new Superperson();
        superperson2.setName("Spiderman");
        superperson2.setType(true);
        superperson2.setDescription("Has spider-like abilities");
        superperson2.setSuperpower("Spider-senses, great reflexes, can wall-crawl");
        superperson2 = superDao.addSuperperson(superperson2);
        
        Superperson superperson3 = new Superperson();
        superperson3.setName("Joker");
        superperson3.setType(false);
        superperson3.setDescription("Supervillain with a clown-like appearance, criminal in Gotham");
        superperson3.setSuperpower("No powers, insanity");
        superperson3 = superDao.addSuperperson(superperson3);
        
        List<Superperson> membersForGoodOrg = new ArrayList<>();
        membersForGoodOrg.add(superperson1);
        membersForGoodOrg.add(superperson2);
        
        Organization organization1 = new Organization();
        organization1.setName("Superheroes United Org");
        organization1.setType(true);
        organization1.setDescription("Only Superheroes allowed");
        organization1.setAddress("123 Test Organization Address City, State 12345");
        organization1.setContact("299-2828");
        organization1.setMembers(membersForGoodOrg);
        organization1 = orgDao.addOrganization(organization1);
        
        List<Superperson> membersForEvilOrg = new ArrayList<>();
        membersForEvilOrg.add(superperson3);
        
        Organization organization2 = new Organization();
        organization2.setName("Supervillains Org");
        organization2.setType(false);
        organization2.setDescription("Only Supervillains allowed");
        organization2.setAddress("2211 Test Organization Address City, State 28292");
        organization2.setContact("232-EVIL");
        organization2.setMembers(membersForEvilOrg);
        organization2 = orgDao.addOrganization(organization2);
        
        List<Superperson> membersForLearningOrg = new ArrayList<>();
        membersForLearningOrg.add(superperson1);
        
        Organization organization3 = new Organization();
        organization3.setName("Gain Superpowers Org for Superheros");
        organization3.setType(true);
        organization3.setDescription("Learn new superpowers");
        organization3.setAddress("123 Test Organization Address City, State 12345");
        organization3.setContact("255-930-9920");
        organization3.setMembers(membersForLearningOrg);
        organization3 = orgDao.addOrganization(organization3);
        
        List<Organization> organizationsForSP1 = orgDao.getOrganizationsForSuperperson(superperson1);
        assertEquals(2,organizationsForSP1.size());
        assertTrue(organizationsForSP1.contains(organization1));
        assertFalse(organizationsForSP1.contains(organization2));
        assertTrue(organizationsForSP1.contains(organization3));
        
        List<Organization> organizationsForSP2 = orgDao.getOrganizationsForSuperperson(superperson2);
        assertEquals(1,organizationsForSP2.size());
        assertTrue(organizationsForSP2.contains(organization1));
        assertFalse(organizationsForSP2.contains(organization2));
        assertFalse(organizationsForSP2.contains(organization3));
        
        
        List<Organization> organizationsForSP3 = orgDao.getOrganizationsForSuperperson(superperson3);
        assertEquals(1,organizationsForSP3.size());
        assertFalse(organizationsForSP3.contains(organization1));
        assertTrue(organizationsForSP3.contains(organization2));
        assertFalse(organizationsForSP3.contains(organization3));
   
    }    
}
