/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.service;

import com.mycompany.vendingmachine.dao.VendingMachineAuditDao;
import com.mycompany.vendingmachine.dao.VendingMachineDao;
import com.mycompany.vendingmachine.dao.VendingMachineDaoException;
import com.mycompany.vendingmachine.dto.Change;
import com.mycompany.vendingmachine.dto.VendingMachine;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author jenni
 */
public class VendingMachineService {

    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;

    public VendingMachineService(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    public VendingMachine getItemById(String itemId) throws VendingMachineDaoException, NoItemInventoryException {// , InsufficientFundsException{
        VendingMachine itemIdCheck = dao.getItemById(itemId);
        if(itemIdCheck == null){
        throw new NoItemInventoryException("No such vending ID exists, please enter a valid selection.");
        //checkAffordability(i, moneyBalance);
        }
        return dao.getItemById(itemId);
    }

    public List<VendingMachine> getAllItems() throws VendingMachineDaoException {
        return dao.getAllItems();
    }

    //error in void probably
    public void updateInventory(VendingMachine updateInventory, BigDecimal moneyBalance) throws VendingMachineDaoException, InsufficientFundsException, NoItemInventoryException {
        isItemInStock(updateInventory);
        checkAffordability(updateInventory, moneyBalance);
        dao.updateInventory(updateInventory);
        auditDao.writeAuditEntry("Inventory count for item " + updateInventory.getItemId() + "-" + updateInventory.getItemName() + " UPDATED");
    }


    public Change GetChange(BigDecimal moneyBalance) throws VendingMachineDaoException {
        BigDecimal getChange = moneyBalance.divide(new BigDecimal("0.01"));
        Integer pennies = getChange.intValue();
        
        Integer extraPennies = 0;
        Integer quarters = 0;
        Integer dimes = 0;
        Integer nickels = 0;
        

        if ( 0 < (pennies/25)){
            quarters = pennies/25;
            extraPennies = pennies%25;

            if (0 < (extraPennies/10)){
                dimes = extraPennies/10;
                extraPennies = extraPennies%10;
            }
            if (0 < (extraPennies/5)) {
                nickels = extraPennies/5;
                extraPennies = extraPennies%5;
            }
        } else if ( 0 < (pennies/10)){
            dimes = pennies/10;
            extraPennies = pennies%10;
            
            if (0 < (extraPennies/5)){
                nickels = extraPennies/5;
                extraPennies = extraPennies%5;
            }
        } else if ( 0 < (pennies/5)){
            nickels = pennies/5;
            extraPennies = pennies%5;
        } else {
            extraPennies = pennies;
        }
        
        pennies = extraPennies;


        Change moneyOwedBack = new Change();
        moneyOwedBack.setPennies(pennies);
        moneyOwedBack.setNickels(nickels);
        moneyOwedBack.setDimes(dimes);
        moneyOwedBack.setQuarters(quarters);
    
        return moneyOwedBack;
    }

    private void isItemInStock(VendingMachine i) throws VendingMachineDaoException, NoItemInventoryException {
        if (i.getItemCount() == 0) {
            throw new NoItemInventoryException("Item is not in stock");
        }
    }

    private void checkAffordability(VendingMachine i, BigDecimal moneyBalance) throws VendingMachineDaoException, InsufficientFundsException {
        BigDecimal price = i.getItemPrice();
        if (moneyBalance == null || moneyBalance.compareTo(price) < 0) {
            throw new InsufficientFundsException("Insufficient funds for vending choice");
        }
    }
}
