/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.ui;

import com.mycompany.vendingmachine.dto.VendingMachine;
import com.mycompany.vendingmachine.dto.Change;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author jenni
 */
public class VendingMachineView {

    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void displayWelcome() {
        io.print("Welcome to Jennifer's Vending Machine");
    }
    
    public void displayOptionsBanner() {
        io.print("=== Displaying Vending Options ===");
    }
    
    public void displayAllItems(List<VendingMachine> items) {
        for (VendingMachine i : items) {
            io.print(i.getItemId() + " " + i.getItemName() + " " + "$" + i.getItemPrice());
        }
        io.print("");
    }
    
    public BigDecimal getInitialMonetaryInput() { 
       io.print("=== Min $0.01 up to $10.00 Max ===");
       return io.readBigDecimal("Please enter money to get started: ", new BigDecimal("0.01"), new BigDecimal("10.00"));
    }
    
    public BigDecimal getMoreMonetaryInput() {
        io.print("=== Min $0.01 up to $10.00 Max ===");
       return io.readBigDecimal("Enter more money: ", new BigDecimal("0.01"), new BigDecimal("10.00"));
    }
    
    public int printMenuAndGetChoice(){
        io.print("1. Make a Selection");
        io.print("2. Add More Money");
        io.print("3. End Transaction");
        
        return io.readInt("Please select from the above choices.", 1, 3);
    } 
    

    public String getItemChoice() { 
        io.print("Please make a selection from the above vending options");
        return io.readString("Enter selection:");
        
    }

    public void displayItemChoice(VendingMachine choice, BigDecimal money) {
            io.print("You chose item " + choice.getItemId() + " " + choice.getItemName() + ", which costs $" + choice.getItemPrice());
             if (choice.getItemCount() > 0 && choice.getItemPrice().compareTo(money) <= 0 ) {
                io.print(choice.getItemCount() + " available, the machine will now vend your selection!");
                io.print("=== Now vending ===");
                io.print("Please retrieve your dispensed item");
            } else if(choice.getItemCount() <= 0) {
                io.print("The item you chose is out of stock, please make another selection");
            }
   
    }
    
    
    public void displayNewBalance(BigDecimal moneyBalance){
        io.print("=== New Balance: $"+ moneyBalance + " ===");
    }
    
    public void displayRemaindingBalance(BigDecimal moneyBalance, VendingMachine choice){
        if (choice.getItemCount() > 0 && choice.getItemPrice().compareTo(moneyBalance) <= 0 ){
        io.print("Your Remainding Balance is: " + moneyBalance.subtract(choice.getItemPrice()));//have to assign it
    }
    }
        
    public void displayInsufficientFundsMessage(BigDecimal moneyBalance, VendingMachine choice){
        BigDecimal amountNeeded = choice.getItemPrice().subtract(moneyBalance);
        if(choice.getItemCount() > 0 && choice.getItemPrice().compareTo(moneyBalance) > 0){
        io.print("Insufficient funds for " + choice.getItemId() + " " + choice.getItemName());
        io.print("=== Current Balance: $" + moneyBalance + " ===");
        io.print("Add $" + amountNeeded + " more to purchase " + choice.getItemId());
        }
    }
    
    public void displayChange(Change changeOwed){
     io.print("=== Change Dispensed ===");
     io.print(changeOwed.getQuarters() + " Quarters");
     io.print(changeOwed.getDimes() + " Dimes");
     io.print(changeOwed.getNickels() + " Nickels");
     io.print(changeOwed.getPennies() + " Pennies");
     io.print("Don't forget your change!");
    }
    
    public void exit() {
        io.print("=== Transaction complete. Thank you for your business! ===");
    }

    public void printErrorMessage(String Message) {
        io.print("Error:" + Message);
    }

}
