/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.dao;

import com.mycompany.flooringmastery.dto.Products;
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
public class ProductsDaoFileImplTest {
    
        ProductsDao productsDao = new ProductsDaoFileImpl("Data\\testproductsfile.txt");
    
    public ProductsDaoFileImplTest() {
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
     * Test of getallProducts method, of class ProductsDaoFileImpl.
     */
    @Test
    public void testGetallProducts() throws Exception {
        List<Products> products = productsDao.getallProducts();
        assertEquals(4, products.size());
        
    }

    /**
     * Test of getProduct method, of class ProductsDaoFileImpl.
     */
    @Test
    public void testGetProduct() throws Exception {
        Products p = new Products("Carpet",new BigDecimal("2.25"), new BigDecimal("2.10"));
        Products fromDao = productsDao.getProduct(p.getProductType());
        assertEquals(p, fromDao);
        assertEquals(productsDao.getProduct("Carpet"), p);
    }
    
}
