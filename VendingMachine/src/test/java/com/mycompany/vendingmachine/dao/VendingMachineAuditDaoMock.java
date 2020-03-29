/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dao;

/**
 *
 * @author jenni
 */
public class VendingMachineAuditDaoMock implements VendingMachineAuditDao{

    @Override
    public void writeAuditEntry(String entry) throws VendingMachineDaoException {
        //do nothing
    }
    
}
