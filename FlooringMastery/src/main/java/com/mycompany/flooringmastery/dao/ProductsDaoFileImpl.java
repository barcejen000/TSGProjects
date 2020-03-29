/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.dao;


import com.mycompany.flooringmastery.dto.Products;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jenni
 */
public class ProductsDaoFileImpl implements ProductsDao{
    private final String FILE;
    final String DELIMITER =",";
    
    List<Products> productinfo = new ArrayList<>();
    
    public ProductsDaoFileImpl(){
        FILE = "Data\\Products.txt";
    }
    
    public ProductsDaoFileImpl(String filename){
        FILE = filename;
    } 
    
    @Override
    public List<Products> getallProducts() throws DaoPersistenceException {
        loadFile();
        return productinfo;
    }
    
    @Override
    public Products getProduct(String productName) throws DaoPersistenceException{
        loadFile();
        for(Products p : productinfo){
            if(p.getProductType().equalsIgnoreCase(productName)){
                return p;
            }
        }
        return null;
    }
    
    private void loadFile() throws DaoPersistenceException{
        Scanner sc = null;
        
        try{
            sc = new Scanner(new FileReader(FILE));
        } catch (FileNotFoundException ex){
            throw new DaoPersistenceException("Products File not found");
        }
    
        productinfo = new ArrayList<>();
        sc.nextLine();
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] part = line.split(DELIMITER);
            
            productinfo.add(new Products(part[0],new BigDecimal(part[1]), new BigDecimal(part[2])));
        }
        sc.close();
    }
    
    
    }
