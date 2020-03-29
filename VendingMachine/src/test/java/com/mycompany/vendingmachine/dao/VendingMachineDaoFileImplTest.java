/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dao;

import com.mycompany.vendingmachine.dto.VendingMachine;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
public class VendingMachineDaoFileImplTest {

    VendingMachineDao dao;

    public VendingMachineDaoFileImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws IOException {
        Path test = Path.of("testinventory.txt");
        Path seed = Path.of("seedinventory.txt");

        if (Files.exists(test, LinkOption.NOFOLLOW_LINKS)) {
            Files.delete(test);
        }
        Files.copy(seed, test, StandardCopyOption.REPLACE_EXISTING);

    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getItemById method, of class VendingMachineDaoFileImpl.
     */
    @Test
    public void testGetItemById() throws VendingMachineDaoException {
        VendingMachineDao testDao = new VendingMachineDaoFileImpl("testinventory.txt");
         VendingMachine B3 = new VendingMachine("B3", "Butterfinger Bar", new BigDecimal("1.75"), 4);
        assertEquals(testDao.getItemById("B3"), B3);
        
    }

    /**
     * Test of getAllItems method, of class VendingMachineDaoFileImpl.
     *
     * @throws com.mycompany.vendingmachine.dao.VendingMachineDaoException
     */

    @Test
    public void testGetAllItems() throws VendingMachineDaoException {
        VendingMachineDao testDao = new VendingMachineDaoFileImpl("testinventory.txt");
        List<VendingMachine> items = testDao.getAllItems();
        assertEquals(10, items.size());
    }

    /**
     * Test of updateInventory method, of class VendingMachineDaoFileImpl.
     */
    @Test
    public void testUpdateInventory() throws Exception {
        VendingMachineDao testDao = new VendingMachineDaoFileImpl("testinventory.txt");
        VendingMachine A1 = testDao.getItemById("A1");
        testDao.updateInventory(A1);
        assertEquals(1, A1.getItemCount());
        
        VendingMachine A2 = testDao.getItemById("A2");
        testDao.updateInventory(A2);
        assertEquals(6, A2.getItemCount());
    }

}
