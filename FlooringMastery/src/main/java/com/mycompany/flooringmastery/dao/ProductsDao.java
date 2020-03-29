/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.dao;

import com.mycompany.flooringmastery.dto.Products;
import java.util.List;

/**
 *
 * @author jenni
 */
public interface ProductsDao {
    List<Products> getallProducts() throws DaoPersistenceException;
    Products getProduct(String productName) throws DaoPersistenceException;
}
