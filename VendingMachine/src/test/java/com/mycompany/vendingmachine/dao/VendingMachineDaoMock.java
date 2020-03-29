/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dao;


import com.mycompany.vendingmachine.dto.VendingMachine;
import com.mycompany.vendingmachine.dao.VendingMachineDao;
import com.mycompany.vendingmachine.dto.VendingMachine;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jenni
 */
public class VendingMachineDaoMock implements VendingMachineDao {
     List<VendingMachine> items = new ArrayList<>();

    @Override
    public VendingMachine getItemById(String itemId) throws VendingMachineDaoException {
            if (itemId.equals("null")) {
                return null;
            }
            return new VendingMachine(itemId, "Item Name", new BigDecimal("1.00"), 2);
        }
   

    @Override
    public List<VendingMachine> getAllItems() throws VendingMachineDaoException {
        return new ArrayList<>();
    }

    @Override
    public VendingMachine updateInventory(VendingMachine updateInventory) throws VendingMachineDaoException {
//        for (VendingMachine i : items) {
//            if (i.getItemId().equalsIgnoreCase(updateInventory.getItemId())) {
//                    i.setItemCount(updateInventory.getItemCount() - 1);
                    return updateInventory;
//            } else if (i.getItemId().equalsIgnoreCase(updateInventory.getItemId()) && i.getItemPrice().compareTo(moneyBalance) > 0) {
//                i.setItemCount(updateInventory.getItemCount() - 0);
//            }
//  
//    }
//         return null;
}
}
