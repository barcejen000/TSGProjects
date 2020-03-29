/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.dao;

import com.mycompany.flooringmastery.dto.Products;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jenni
 */
public class ProductsDaoMock implements ProductsDao{
     List<Products> products = new ArrayList<>();

     @Override
    public List<Products> getallProducts() throws DaoPersistenceException {
        return new ArrayList<>();
    }

    @Override
    public Products getProduct(String productName) throws DaoPersistenceException {
        if (productName.equals("null")) {
            return null;
        }
        return new Products(productName, new BigDecimal("2.25"), new BigDecimal("2.10"));
    }
    
}
