/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.controller;

import com.mycompany.vendingmachine.dao.VendingMachineDaoException;
import com.mycompany.vendingmachine.dto.Change;

import com.mycompany.vendingmachine.dto.VendingMachine;
import com.mycompany.vendingmachine.service.VendingMachineService;
import com.mycompany.vendingmachine.service.InsufficientFundsException;
import com.mycompany.vendingmachine.service.NoItemInventoryException;
import com.mycompany.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author jenni
 */
public class VendingMachineController {

    private VendingMachineView view;
    private VendingMachineService service;
    private VendingMachineBalance money;

    public VendingMachineController(VendingMachineView view, VendingMachineService service) {
        this.view = view;
        this.service = service;
        this.money = new VendingMachineBalance();
    }

    public void run() {
        view.displayWelcome();
        try {
            displayAllItems();
        } catch (VendingMachineDaoException ex) {
            view.printErrorMessage(ex.getMessage());
        }
        addToBalance(getInitialMoney());
        boolean keepGoing = true;
        int menuChoice = 0;

        while (keepGoing) {
            try {
                menuChoice = getMenuChoice();

                switch (menuChoice) {
                    case 1:
                        displayAllItems();
                        getChoice();
                        break;
                    case 2:
                        addToBalance(getMoreMoney());
                        view.displayNewBalance(getCurrentBalance());
                        break;
                    case 3:
                        getChange();
                        returnRemaindingBalance(getCurrentBalance());
                        exit();

                }
            } catch (VendingMachineDaoException | InsufficientFundsException | NoItemInventoryException ex) {
                view.printErrorMessage(ex.getMessage());
            }
        }

    }

    private int getMenuChoice() {
        return view.printMenuAndGetChoice();
    }

    public void displayAllItems() throws VendingMachineDaoException {
        view.displayOptionsBanner();
        List<VendingMachine> items = service.getAllItems();
        view.displayAllItems(items);
    }
    
    public void addToBalance(BigDecimal money) {
        this.money.addToBalance(money);
    }

    public BigDecimal getInitialMoney() {
        return view.getInitialMonetaryInput();
    }

    public BigDecimal getMoreMoney() {
        return view.getMoreMonetaryInput();
    }
    
    public BigDecimal getCurrentBalance() {
        return this.money.getCurrentBalance();
    }

    public void getChoice() throws VendingMachineDaoException, InsufficientFundsException, NoItemInventoryException {
        String choice = view.getItemChoice();
        VendingMachine item = service.getItemById(choice);
        view.displayItemChoice(item, getCurrentBalance());
        //service.updateInventory(item, getCurrentBalance());// need a check here
        view.displayInsufficientFundsMessage(getCurrentBalance(), item);
        view.displayRemaindingBalance(getCurrentBalance(), item);
        if (item.getItemCount() > 0 && item.getItemPrice().compareTo(getCurrentBalance()) <= 0) {
            service.updateInventory(item, getCurrentBalance());
            returnRemaindingBalance(item.getItemPrice());
        }

    }

    public void getChange() throws VendingMachineDaoException {
        Change changeOwed = service.GetChange(getCurrentBalance());
        view.displayChange(changeOwed);
    }


    public void returnRemaindingBalance(BigDecimal money) {
        this.money.returnBalance(money);
    }

    private void exit() {
        view.exit();
    }

}
