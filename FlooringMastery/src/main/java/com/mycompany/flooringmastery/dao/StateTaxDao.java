/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.dao;

import com.mycompany.flooringmastery.dto.StateTax;
import java.util.List;

/**
 *
 * @author jenni
 */
public interface StateTaxDao {
    List<StateTax> getallStates() throws DaoPersistenceException;
    StateTax getState(String stateName) throws DaoPersistenceException;
}
