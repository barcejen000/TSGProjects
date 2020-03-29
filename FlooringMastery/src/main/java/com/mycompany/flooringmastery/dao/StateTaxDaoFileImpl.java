/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.dao;

import com.mycompany.flooringmastery.dto.StateTax;
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
public class StateTaxDaoFileImpl implements StateTaxDao{
    
    private final String FILE;
    final String DELIMITER =",";
    
    List<StateTax> taxinfo = new ArrayList<>();
    
    public StateTaxDaoFileImpl(){
        FILE = "Data\\Taxes.txt";
    }
    
    public StateTaxDaoFileImpl(String filename){
        FILE = filename;
    } 
    
    @Override
    public List<StateTax> getallStates() throws DaoPersistenceException {
        loadFile();
        return taxinfo;
    }
     
    @Override
    public StateTax getState(String stateName) throws DaoPersistenceException{
        loadFile();
        for(StateTax t:taxinfo){
            if(t.getStateAbbreviation().equalsIgnoreCase(stateName)){
                return t;
            }
        }
        return null;
    }
    
    
    private void loadFile() throws DaoPersistenceException{
        Scanner sc = null;
        
        try{
            sc = new Scanner(new FileReader(FILE));
        } catch (FileNotFoundException ex){
            throw new DaoPersistenceException("Tax file not found");
        }
    
        taxinfo = new ArrayList<>();
        sc.nextLine();
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] part = line.split(DELIMITER);
            
            taxinfo.add(new StateTax(part[0],part[1],new BigDecimal(part[2])));
        }
        sc.close();
    }
    
        
    }
