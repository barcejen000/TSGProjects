/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.dao;

import com.mycompany.flooringmastery.dto.Orders;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jenni
 */
public class OrdersDaoFileImpl implements OrdersDao {

    private final String FILE_HEADER;
    final String DELIMITER = ",";
    private int getPreviousOrderNumber;

    List<Orders> orders = new ArrayList<>();
    List<Orders> orderNumbers = new ArrayList<>();

    public OrdersDaoFileImpl() {
        FILE_HEADER = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,"
                + "CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
    }

    public OrdersDaoFileImpl(String headername) {
        FILE_HEADER = headername;
    }

    @Override
    public List<Orders> getAllOrdersByDate(LocalDate date) throws DaoPersistenceException {
        return loadFile(date);
    }

    @Override
    public Orders getOrderByOrderNumber(int orderNumber, LocalDate date) throws DaoPersistenceException {
        loadFile(date);
        for (Orders o : orders) {
            if (o.getOrderNumber() == (orderNumber)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public Orders addNewOrder(Orders newOrder) throws DaoPersistenceException {
        try {
            loadFile(newOrder.getOrderDate());
        } catch (DaoPersistenceException ex) {
            //left blank because the write file is creating a file
        }
        orders.add(newOrder);
        writeFile(newOrder.getOrderDate());
        return newOrder;
    }

    @Override
    public Orders setOrderNumber(Orders newOrderNumber) throws DaoPersistenceException, IOException {
        loadOrderNumber(newOrderNumber.getOrderDate());
        getPreviousOrderNumber++;
        newOrderNumber.setOrderNumber(getPreviousOrderNumber);
        return newOrderNumber;
    }

    @Override
    public Orders editOrder(Orders updateOrder, LocalDate orderDate) throws DaoPersistenceException {
        for (Orders o : orders) {
            if (o.getOrderNumber() == updateOrder.getOrderNumber()) {
                o.setCustomerName(updateOrder.getCustomerName());
                o.setState(updateOrder.getState());
                o.setProductType(updateOrder.getProductType());
                o.setArea(updateOrder.getArea());
                writeFile(orderDate);
                return updateOrder;
            }

        }
        return null;
    }

    @Override
    public Orders removeOrder(int orderNumber, LocalDate orderDate) throws DaoPersistenceException {
        loadFile(orderDate);
        for (Orders o : orders) {
            if (o.getOrderNumber() == orderNumber) {
                orders.remove(o);
                writeFile(orderDate);
                return o;
            }
        }
        return null;
    }

    private List<Orders> loadFile(LocalDate date) throws DaoPersistenceException {
        Scanner sc = null;
        String ordersDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        File file = new File(String.format("Orders\\Orders_" + ordersDate + ".txt"));
        try {
            sc = new Scanner(new FileReader(file));
        } catch (FileNotFoundException ex) {
            throw new DaoPersistenceException("Orders file not found");
        }

        orders = new ArrayList<>();
        sc.nextLine();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] part = line.split(DELIMITER);
            Orders o = new Orders();
             
            o.setOrderNumber(Integer.parseInt(part[0]));
            o.setCustomerName(part[1].replace("^", ","));
            o.setState(part[2]);
            o.setTaxRate(new BigDecimal(part[3]));
            o.setProductType(part[4]);
            o.setArea(new BigDecimal(part[5]));
            o.setCostPerSquareFoot(new BigDecimal(part[6]));
            o.setLaborCostPerSquareFoot(new BigDecimal(part[7]));
            o.setMaterialCost(new BigDecimal(part[8]));
            o.setLaborCost(new BigDecimal(part[9]));
            o.setTax(new BigDecimal(part[10]));
            o.setTotal(new BigDecimal(part[11]));
            o.setOrderDate(LocalDate.parse(ordersDate, DateTimeFormatter.ofPattern("MMddyyyy")));
            orders.add(o);
        }
        sc.close();
        return orders;
    }

    private void writeFile(LocalDate date) throws DaoPersistenceException {
        PrintWriter pw = null;
        try {
            String ordersDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
            File file = new File(String.format("Orders\\Orders_" + ordersDate + ".txt"));
            FileWriter fw = new FileWriter(file);
            pw = new PrintWriter(fw);
        } catch (IOException ex) {
            throw new DaoPersistenceException("Error writing to Orders file");
        }
        pw.println(FILE_HEADER);
        for (Orders o : orders) {
            
            pw.println(o.getOrderNumber() + DELIMITER + o.getCustomerName().replace(",","^") + DELIMITER
                    + o.getState() + DELIMITER + o.getTaxRate() + DELIMITER
                    + o.getProductType() + DELIMITER + o.getArea() + DELIMITER + o.getCostPerSquareFoot()
                    + DELIMITER + o.getLaborCostPerSquareFoot() + DELIMITER + o.getMaterialCost()
                    + DELIMITER + o.getLaborCost() + DELIMITER + o.getTax()
                    + DELIMITER + o.getTotal());
        }
        pw.flush();//pw writes to file
        pw.close();//cleans

    }


    @Override
    public void exportAll() throws DaoPersistenceException, FileNotFoundException, IOException {
        PrintWriter pw = null;
        //directory instance created here
        File directory = new File("Orders\\");

        //print writer object for data export file
        try {
            pw = new PrintWriter("Backup\\MainDataExport.txt");
        } catch (IOException ex) {
            throw new DaoPersistenceException("Error writing to Data Export file");
        }
        //list of all files in String array form
        String[] allFileNames = directory.list();
        pw.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< TSG Corp. Active Orders >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        //loop to read contents of all files in directory
        for (String fileName : allFileNames) {

            File file = new File(directory, fileName);
            //buffered reader object
            BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
            pw.println("============================================File content for file " + fileName + "============================================");
            //reading file
            bufferedreader.readLine();//reading first line 
            String readLine = bufferedreader.readLine();
            //pw writing to the file in the Backup folder
            pw.println(FILE_HEADER + DELIMITER + "Date");
            while (readLine != null) {//skips over first line
                //new string that is a substring of the fileName with specified beginning and end index
                String stringDate = fileName.substring(7, 15);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
                LocalDate localDate = LocalDate.parse(stringDate, formatter);
                //changed back to string with correct pattern
                String formattedDate = localDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
                //pw writing to the file in the Backup folder
                pw.println(readLine + DELIMITER + formattedDate);
                readLine = bufferedreader.readLine();
            }
            bufferedreader.close();
        }
        pw.flush();
        pw.close();
    }

    private int loadOrderNumber(LocalDate date) throws DaoPersistenceException, IOException {
        int orderNumber = 0;
        BufferedReader bufferedreader = null;
        String ordersDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        File file = new File(String.format("Orders\\Orders_" + ordersDate + ".txt"));
        if (file.exists()) {
            bufferedreader = new BufferedReader(new FileReader(file));
            orders = new ArrayList<>();
            bufferedreader.readLine();
            String last, line;
            while ((line = bufferedreader.readLine()) != null) {
                last = line;
                String[] part = line.split(DELIMITER);
                orderNumber = Integer.parseInt(part[0]);
            }
            bufferedreader.close();
            return getPreviousOrderNumber = orderNumber;
        } else {
            orders = new ArrayList<>();
            return getPreviousOrderNumber = 0;
        }
    }

}
