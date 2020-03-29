/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.dao;

import com.mycompany.flooringmastery.dto.StateTax;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jenni
 */
public class StateTaxDaoFileImplTest {
    
       StateTaxDao taxDao = new StateTaxDaoFileImpl("Data\\teststatesfile.txt");
    
    public StateTaxDaoFileImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getallStates method, of class StateTaxDaoFileImpl.
     */
    @Test
    public void testGetallStates() throws Exception {
        List<StateTax> taxinfo = taxDao.getallStates();
        assertEquals(4, taxinfo.size());
    }

    /**
     * Test of getState method, of class StateTaxDaoFileImpl.
     */
    @Test
    public void testGetState() throws Exception {
        StateTax s = new StateTax("TX","Texas",new BigDecimal("4.45"));
        StateTax fromDao = taxDao.getState(s.getStateAbbreviation());
        assertEquals(s,fromDao);
        assertEquals(taxDao.getState("TX"), s);
        
        
    }
    
}
