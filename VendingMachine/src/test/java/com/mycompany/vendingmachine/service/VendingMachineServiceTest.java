/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.service;

import com.mycompany.vendingmachine.dao.VendingMachineAuditDao;
import com.mycompany.vendingmachine.dao.VendingMachineAuditDaoMock;
import com.mycompany.vendingmachine.dao.VendingMachineDao;
import com.mycompany.vendingmachine.dao.VendingMachineDaoException;
import com.mycompany.vendingmachine.dao.VendingMachineDaoMock;
import com.mycompany.vendingmachine.dto.Change;
import com.mycompany.vendingmachine.dto.VendingMachine;
import java.math.BigDecimal;
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
public class VendingMachineServiceTest {

    private VendingMachineService service;

    public VendingMachineServiceTest() {

        VendingMachineDao dao = new VendingMachineDaoMock();

        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoMock();

        service = new VendingMachineService(dao, auditDao);

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

    @Test

    public void testInsufficientFunds() throws Exception {

        try {

            BigDecimal moneyBalance = new BigDecimal("0.75");

            VendingMachine testItem = new VendingMachine("A1", "Kit-Kat", new BigDecimal("1.00"), 2);

            service.updateInventory(testItem, moneyBalance);

            fail();

        } catch (InsufficientFundsException ex) {

        } catch (VendingMachineDaoException ex) {

            fail();

        }

    }

    /**
     *
     * Test of getItemById method, of class VendingMachineService.
     *
     */
    @Test

    public void testGetItemById() throws Exception {

        try {

            service.getItemById("null");

            fail();

        } catch (NoItemInventoryException ex) {

        }

        try {

            service.getItemById("A1");

        } catch (NoItemInventoryException ex) {

            fail();

        }

    }

    /**
     *
     * Test of updateInventory method, of class VendingMachineService.
     *
     */
    @Test

    public void testUpdateInventory() throws Exception {

        VendingMachine A1 = new VendingMachine("A1", "Twix", new BigDecimal("1.25"), 2);

        VendingMachine A2 = new VendingMachine("A2", "Hershey", new BigDecimal("1.00"), 0);

        VendingMachine A3 = new VendingMachine("A3", "Reeses", new BigDecimal("1.00"), 1);

        BigDecimal moneyBalance = new BigDecimal("1.00");

        try {

            service.updateInventory(A1, moneyBalance);

            fail();

        } catch (NoItemInventoryException | InsufficientFundsException | VendingMachineDaoException ex) {

        }

        try {

            service.updateInventory(A2, moneyBalance);

            fail();

        } catch (NoItemInventoryException | InsufficientFundsException | VendingMachineDaoException ex) {

        }

        try {

            service.updateInventory(A3, moneyBalance);

        } catch (NoItemInventoryException | InsufficientFundsException | VendingMachineDaoException ex) {

            fail();

        }

    }

    /**
     *
     * Test of GetChange method, of class VendingMachineService.
     *
     */
    @Test

    public void testGetChange() throws Exception {

        try {

            BigDecimal moneyBalance = new BigDecimal("0.55");

            Change changeOwed = new Change(2, 0, 1, 0);

            assertEquals(service.GetChange(moneyBalance), changeOwed);

        } catch (VendingMachineDaoException ex) {

            fail();

        }

        try {

            BigDecimal moneyBalance = BigDecimal.ZERO;

            Change changeOwed = new Change(0, 0, 0, 0);

            assertEquals(service.GetChange(moneyBalance), changeOwed);

        } catch (VendingMachineDaoException ex) {

            fail();

        }

        try {

            BigDecimal moneyBalance = new BigDecimal("2.37");

            Change changeOwed = new Change(9, 1, 0, 2);

            assertEquals(service.GetChange(moneyBalance), changeOwed);

        } catch (VendingMachineDaoException ex) {

            fail();

        }

        try {

            BigDecimal moneyBalance = new BigDecimal("0.09");

            Change changeOwed = new Change(0, 0, 1, 4);

            assertEquals(service.GetChange(moneyBalance), changeOwed);

        } catch (VendingMachineDaoException ex) {

            fail();

        }

    }
}
