/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vendingmachine.dao;

import com.mycompany.vendingmachine.dto.VendingMachine;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jenni
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {

    private final String FILE;
    final String DELIMITER = "::";

    List<VendingMachine> items = new ArrayList<>();

    public VendingMachineDaoFileImpl() {
        FILE = "inventory.txt";
    }

    public VendingMachineDaoFileImpl(String filename) {
        FILE = filename;
    }

    @Override
    public VendingMachine getItemById(String itemId) throws VendingMachineDaoException {
        loadFile();
        for (VendingMachine i : items) {
            if (i.getItemId().equalsIgnoreCase(itemId)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public List<VendingMachine> getAllItems() throws VendingMachineDaoException {
        loadFile();
        return items;
    }

    @Override
    public VendingMachine updateInventory(VendingMachine updateInventory) throws VendingMachineDaoException {
        for (VendingMachine i : items) {
            if (i.getItemId().equalsIgnoreCase(updateInventory.getItemId())) {
                    i.setItemCount(updateInventory.getItemCount() - 1);
                    writeFile();
                    return updateInventory;
            }
  
    }
         return null;
    }
   

    private void loadFile() throws VendingMachineDaoException {
        Scanner sc = null; 
        try {
            sc = new Scanner(new FileReader(FILE));
        } catch (FileNotFoundException ex) {
            throw new VendingMachineDaoException("File not found");
        }
        items = new ArrayList<>();// new array list

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(DELIMITER);
            VendingMachine i = new VendingMachine();
            i.setItemId(parts[0]);
            i.setItemName(parts[1]);
            i.setItemPrice(new BigDecimal(parts[2]));
            i.setItemCount(Integer.parseInt(parts[3]));
            
            items.add((i));
        }
        sc.close();
    }

    private void writeFile() throws VendingMachineDaoException {
        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new FileWriter(FILE));
        } catch (IOException ex) {
            throw new VendingMachineDaoException("Error writing to file");
        }
        for (VendingMachine i : items) {
            pw.println(i.getItemId() + DELIMITER + i.getItemName() + DELIMITER + i.getItemPrice() + DELIMITER + i.getItemCount());
        }
        pw.flush();
        pw.close();
    }

}
