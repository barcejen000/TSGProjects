/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.dao;

import com.mycompany.flooringmastery.dto.StateTax;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jenni
 */
public class StateTaxDaoMock implements StateTaxDao{
    List<StateTax> taxinfo = new ArrayList<>();

    @Override
    public List<StateTax> getallStates() throws DaoPersistenceException {
        return new ArrayList<>();
    }

    @Override
    public StateTax getState(String stateName) throws DaoPersistenceException {
        if (stateName.equals("null")) {
            return null;
        }
        return new StateTax(stateName, "Texas", new BigDecimal("4.45"));

    }
    
}
