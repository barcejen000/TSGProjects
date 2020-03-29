/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.service;

import com.mycompany.flooringmastery.dao.OrdersDao;
import com.mycompany.flooringmastery.dao.OrdersDaoMock;
import com.mycompany.flooringmastery.dao.ProductsDao;
import com.mycompany.flooringmastery.dao.ProductsDaoMock;
import com.mycompany.flooringmastery.dao.StateTaxDao;
import com.mycompany.flooringmastery.dao.StateTaxDaoMock;
import com.mycompany.flooringmastery.dto.Orders;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class FlooringMasteryServiceTest {

    private FlooringMasteryService service;

    public FlooringMasteryServiceTest() {
        OrdersDao ordersDao = new OrdersDaoMock();
        ProductsDao productsDao = new ProductsDaoMock();
        StateTaxDao taxDao = new StateTaxDaoMock();
        service = new FlooringMasteryService(ordersDao, productsDao, taxDao);

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
     * Test of getAllOrdersByDate method, of class FlooringMasteryService.
     */
    @Test
    public void testGetAllOrdersByDate() throws Exception {
       try {
            service.getAllOrdersByDate(LocalDate.of(2020, 03, 01));
        } catch (OrderNumberDoesNotExistException ex) {
            fail();
        }

        try {
            service.getAllOrdersByDate(LocalDate.of(2020, 02, 01));
            fail();
        } catch (OrderNumberDoesNotExistException ex) {
        }

    }

    /**
     * Test of getOrderByOrderNumber method, of class FlooringMasteryService.
     */
    @Test
    public void testGetOrderByOrderNumber() throws Exception {

        try {
            service.getOrderByOrderNumber(1, LocalDate.of(2020, 03, 01));
        } catch (OrderNumberDoesNotExistException ex) {
            fail();
        }

        try {
            service.getOrderByOrderNumber(2, LocalDate.of(2020, 03, 01));
            fail();
        } catch (OrderNumberDoesNotExistException ex) {

        }
         try {
            service.getOrderByOrderNumber(0, LocalDate.of(2020, 03, 01));
            fail();
        } catch (OrderNumberDoesNotExistException ex) {

        }

        try {
            service.getOrderByOrderNumber(1, LocalDate.of(2019, 03, 01));
            fail();
        } catch (OrderNumberDoesNotExistException ex) {

        }

    }

    /**
     * Test of removeOrder method, of class FlooringMasteryService.
     */
    @Test
    public void testRemoveOrder() throws Exception {
        try {
            service.removeOrder(1, LocalDate.of(2020, 03, 01));
        } catch (OrderNumberDoesNotExistException ex) {
            fail();
        }

        try {
            service.removeOrder(1, LocalDate.of(2020, 04, 01));
            fail();
        } catch (OrderNumberDoesNotExistException ex) {
        }

        try {
            service.removeOrder(2, LocalDate.of(2020, 03, 01));
            fail();
        } catch (OrderNumberDoesNotExistException ex) {
        }

    }

    /**
     * Test of getProduct method, of class FlooringMasteryService.
     */
    @Test
    public void testGetProduct() throws Exception {
        try {
            service.getProduct("null");
            fail();
        } catch (ProductNotSoldByCompanyException ex) {

        }

        try {
            service.getProduct("Tile");
        } catch (ProductNotSoldByCompanyException ex) {
            fail();
        }
    }

    /**
     * Test of getState method, of class FlooringMasteryService.
     */
    @Test
    public void testGetState() throws Exception {
        try {
            service.getState("null");
            fail();
        } catch (DoNotServeStateException ex) {

        }

        try {
            service.getState("TX");
        } catch (DoNotServeStateException ex) {
            fail();
        }

    }

    /**
     * Test of newOrderAddOns method, of class FlooringMasteryService.
     */
    @Test
    public void testNewOrderAddOns() throws Exception {
       //NULL/EMPTY FIELDS
       try {
            Orders O1 = new Orders("","TX","Wood",new BigDecimal(200));
            service.newOrderAddOns(O1);
            fail();
        } catch (FlooringMasteryDataValidationException ex) {
            
        }
       
       try {
           Orders O2 = new Orders("Floor Depot","","Carpet",new BigDecimal(250));
            service.newOrderAddOns(O2);
            fail();
        } catch (FlooringMasteryDataValidationException|DoNotServeStateException ex) {
            
        }
       
       try {
            Orders O3 = new Orders("Floors & Beyond","KY","",new BigDecimal(400));
            service.newOrderAddOns(O3);
             fail();
        } catch (FlooringMasteryDataValidationException |ProductNotSoldByCompanyException ex) {

        }
       
       try {
            Orders O4 = new Orders("Tile Co.","TX","Wood",new BigDecimal("0"));
            service.newOrderAddOns(O4);
            fail();
        } catch (FlooringMasteryDataValidationException ex) {
            
        }
       
       //STATE EXCEPTION
        try {
            Orders O5 = new Orders("Tile Co.","null","Wood",new BigDecimal("100"));
            service.newOrderAddOns(O5);
            fail();
        } catch (DoNotServeStateException|FlooringMasteryDataValidationException ex) {
            
        }
        
        //PRODUCTS EXCEPTION
        try {
            Orders O7 = new Orders("Tile Co.","TX","null",new BigDecimal("100"));
            service.newOrderAddOns(O7);
            fail();
        } catch (ProductNotSoldByCompanyException| FlooringMasteryDataValidationException  ex) {
              
        }
        
        try {
            Orders O8 = new Orders("Tile Co.","TX","Wood",new BigDecimal("100"));
            service.newOrderAddOns(O8);
        } catch (ProductNotSoldByCompanyException|FlooringMasteryDataValidationException|DoNotServeStateException ex) {
            fail();  
        }
        
        try {
            Orders O9 = new Orders("Tile Co.","","",new BigDecimal("0"));
            service.newOrderAddOns(O9);
             fail();  
        } catch (ProductNotSoldByCompanyException|FlooringMasteryDataValidationException|DoNotServeStateException ex) { 
        }
    }

    /**
     * Test of editOrderAddOns method, of class FlooringMasteryService.
     */
    @Test
    public void testEditOrderAddOns() throws Exception {
        //STATE EXCEPTION
        try {
            Orders order1 = new Orders("Company Name","null","null",new BigDecimal("100"));
            service.newOrderAddOns(order1);
            fail();
        } catch (DoNotServeStateException|ProductNotSoldByCompanyException ex) {
        }
        
        //PRODUCTS EXCEPTION
        try {
            Orders order2 = new Orders("Tile Co.","TX","null",new BigDecimal("100"));
            service.newOrderAddOns(order2);
            fail();
        } catch (ProductNotSoldByCompanyException ex) {
              
        }
        
        try {
            Orders order3 = new Orders("Tile Company2","TX","Laminate",new BigDecimal("100"));
            service.newOrderAddOns(order3);
        } catch (ProductNotSoldByCompanyException ex) {
            fail();  
        }
        
    }

}
