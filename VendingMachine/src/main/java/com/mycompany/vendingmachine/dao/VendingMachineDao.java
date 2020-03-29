/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dao;

import com.mycompany.vendingmachine.dto.VendingMachine;
import java.util.List;

/**
 *
 * @author jenni
 */
public interface VendingMachineDao {
    VendingMachine getItemById (String itemId) throws VendingMachineDaoException;
    List<VendingMachine> getAllItems() throws VendingMachineDaoException;
    VendingMachine updateInventory(VendingMachine updateInventory) throws VendingMachineDaoException;
}
