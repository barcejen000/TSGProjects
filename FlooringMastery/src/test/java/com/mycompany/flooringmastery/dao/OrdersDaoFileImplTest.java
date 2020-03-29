/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.dao;

import com.mycompany.flooringmastery.dto.Orders;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class OrdersDaoFileImplTest {
    
    OrdersDao ordersDao;
    
    public OrdersDaoFileImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws IOException {
        Path test = Path.of("Orders\\Orders_09091999.txt");
        Path seed = Path.of("SeedFiles\\seedorders.txt");
        
        if(Files.exists(test, LinkOption.NOFOLLOW_LINKS)){
            Files.delete(test);
        }
        
        Files.copy(seed, test, StandardCopyOption.REPLACE_EXISTING);
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllOrdersByDate method, of class OrdersDaoFileImpl.
     */
    @Test
    public void testGetAllOrdersByDate() throws Exception {
        OrdersDao testDao = new OrdersDaoFileImpl("Orders\\Orders_09091999.txt");
        LocalDate ld = LocalDate.parse("09091999", DateTimeFormatter.ofPattern("MMddyyyy"));
        List<Orders> orders = testDao.getAllOrdersByDate(ld);
        assertEquals(2, orders.size());
    }

    /**
     * Test of addOrder method, of class OrdersDaoFileImpl.
     */
    @Test
    public void testAddOrder() throws Exception {
        OrdersDao testDao = new OrdersDaoFileImpl("Orders\\Orders_09091999.txt");
          Orders o = new Orders(7, "Flooring Co", "WA", new BigDecimal("9.25"), "Wood", new BigDecimal("243.00"),
          new BigDecimal("5.15"),new BigDecimal("4.75"), new BigDecimal("1251.45"), new BigDecimal("1154.25"), new BigDecimal("216.51"), new BigDecimal("2622.21"), LocalDate.of(1999, 9, 9));
          o = testDao.addNewOrder(o);
          Orders fromTestDao = testDao.getOrderByOrderNumber(o.getOrderNumber(), o.getOrderDate());
          assertEquals(o, fromTestDao);
    }

    /**
     * Test of editOrder method, of class OrdersDaoFileImpl.
     */
    @Test
    public void testEditOrder() throws Exception {
      OrdersDao testDao = new OrdersDaoFileImpl("Orders\\Orders_09091999.txt");
      Orders o = new Orders(10, "Lowes", "WA", new BigDecimal("9.25"), "Wood", new BigDecimal("243.00"),
      new BigDecimal("5.15"),new BigDecimal("4.75"), new BigDecimal("1251.45"), new BigDecimal("1154.25"), new BigDecimal("216.51"), new BigDecimal("2622.21"), LocalDate.of(1999, 9, 9));
      o = testDao.addNewOrder(o);
      Orders fromTestDao = testDao.getOrderByOrderNumber(o.getOrderNumber(), o.getOrderDate());
      assertEquals(o, fromTestDao);  
      
      o.setCustomerName("Another Lowes Name");
      o.setState("TX");
      o.setProductType("Tile");
      o.setArea(new BigDecimal("100"));
      
      assertNotEquals(o, fromTestDao);
      
      testDao.editOrder(o, o.getOrderDate()); 
      fromTestDao = testDao.getOrderByOrderNumber(o.getOrderNumber(), o.getOrderDate());
      assertEquals(o, fromTestDao);
       
    }

    /**
     * Test of removeOrder method, of class OrdersDaoFileImpl.
     */
    @Test
    public void testRemoveOrder() throws Exception {
          OrdersDao testDao = new OrdersDaoFileImpl("Orders\\Orders_09091999.txt");
          Orders o = new Orders(5, "Home Depot", "WA", new BigDecimal("9.25"), "Wood", new BigDecimal("243.00"),
          new BigDecimal("5.15"),new BigDecimal("4.75"), new BigDecimal("1251.45"), new BigDecimal("1154.25"), new BigDecimal("216.51"), new BigDecimal("2622.21"), LocalDate.of(1999, 9, 9));
          o = testDao.addNewOrder(o);
          //int orderNumber = 2;
          //LocalDate ld = LocalDate.parse("09091999", DateTimeFormatter.ofPattern("MMddyyyy"));
          Orders fromTestDao = testDao.getOrderByOrderNumber(o.getOrderNumber(), o.getOrderDate());
          assertEquals(o, fromTestDao);
          testDao.removeOrder(o.getOrderNumber(), o.getOrderDate());
          fromTestDao = testDao.getOrderByOrderNumber(o.getOrderNumber(), o.getOrderDate());
          assertNull(fromTestDao);
    }
    
  
    @Test
    public void setOrderNumber() throws Exception {
        OrdersDao testDao = new OrdersDaoFileImpl("Orders\\Orders_09091999.txt");
        Orders o1 = new Orders("Home Depot", "WA", new BigDecimal("9.25"), "Wood", new BigDecimal("243.00"),
        new BigDecimal("5.15"),new BigDecimal("4.75"), new BigDecimal("1251.45"), new BigDecimal("1154.25"), new BigDecimal("216.51"), new BigDecimal("2622.21"), LocalDate.of(1999, 9, 9));
        testDao.setOrderNumber(o1);
        testDao.addNewOrder(o1);
        
       assertEquals(o1.getOrderNumber(), 3);
       
    }
    
    @Test
    public void setOrderNumberWithAddAndRemove() throws Exception {
        OrdersDao testDao = new OrdersDaoFileImpl("Orders\\Orders_09091999.txt");
        Orders o2 = new Orders("Lowes", "WA", new BigDecimal("9.25"), "Wood", new BigDecimal("243.00"),
        new BigDecimal("5.15"),new BigDecimal("4.75"), new BigDecimal("1251.45"), new BigDecimal("1154.25"), new BigDecimal("216.51"), new BigDecimal("2622.21"), LocalDate.of(1999, 9, 9));
        testDao.setOrderNumber(o2);
        testDao.addNewOrder(o2);
        assertEquals(o2.getOrderNumber(), 3);
        
        Orders o3 = new Orders("Washington Flooring Co.", "WA", new BigDecimal("9.25"), "Wood", new BigDecimal("243.00"),
        new BigDecimal("5.15"),new BigDecimal("4.75"), new BigDecimal("1251.45"), new BigDecimal("1154.25"), new BigDecimal("216.51"), new BigDecimal("2622.21"), LocalDate.of(1999, 9, 9));
        testDao.setOrderNumber(o3);
        testDao.addNewOrder(o3);
        
        assertEquals(o3.getOrderNumber(), 4);
        
        //remove last one/max order number
        testDao.removeOrder(o3.getOrderNumber(), o3.getOrderDate());
        //add new order which is assign max number
        Orders o4 = new Orders("Flooring Depot", "TX", new BigDecimal("9.25"), "Wood", new BigDecimal("243.00"),
        new BigDecimal("5.15"),new BigDecimal("4.75"), new BigDecimal("1251.45"), new BigDecimal("1154.25"), new BigDecimal("216.51"), new BigDecimal("2622.21"), LocalDate.of(1999, 9, 9));
        testDao.setOrderNumber(o4);
        testDao.addNewOrder(o4);
        
        assertEquals(o4.getOrderNumber(), 4);
        
        
        //add two more orders and remove one in between and will use max number not how many orders are in the file
        Orders o5 = new Orders("Lowes Flooring Company", "KY", new BigDecimal("9.25"), "Wood", new BigDecimal("243.00"),
        new BigDecimal("5.15"),new BigDecimal("4.75"), new BigDecimal("1251.45"), new BigDecimal("1154.25"), new BigDecimal("216.51"), new BigDecimal("2622.21"), LocalDate.of(1999, 9, 9));
        testDao.setOrderNumber(o2);
        testDao.addNewOrder(o2);
        assertEquals(o2.getOrderNumber(), 5);
        
        Orders o6 = new Orders("Floors and beyond", "CA", new BigDecimal("9.25"), "Wood", new BigDecimal("243.00"),
        new BigDecimal("5.15"),new BigDecimal("4.75"), new BigDecimal("1251.45"), new BigDecimal("1154.25"), new BigDecimal("216.51"), new BigDecimal("2622.21"), LocalDate.of(1999, 9, 9));
        testDao.setOrderNumber(o6);
        testDao.addNewOrder(o6);
        
        assertEquals(o6.getOrderNumber(), 6);
        //removing order 5
        testDao.removeOrder(o5.getOrderNumber(), o5.getOrderDate());
        //adding a new one
        Orders o7 = new Orders("Floors LTD.", "CA", new BigDecimal("9.25"), "Wood", new BigDecimal("243.00"),
        new BigDecimal("5.15"),new BigDecimal("4.75"), new BigDecimal("1251.45"), new BigDecimal("1154.25"), new BigDecimal("216.51"), new BigDecimal("2622.21"), LocalDate.of(1999, 9, 9));
        testDao.setOrderNumber(o7);
        testDao.addNewOrder(o7);
        
        assertEquals(o7.getOrderNumber(), 7);
    }
    
}
