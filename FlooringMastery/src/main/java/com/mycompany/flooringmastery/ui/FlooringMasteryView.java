/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.ui;

import com.mycompany.flooringmastery.dto.Orders;
import com.mycompany.flooringmastery.dto.Products;
import com.mycompany.flooringmastery.dto.StateTax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author jenni
 */
public class FlooringMasteryView {

    private UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public void displayWelcome() {
        io.print("<<TSG Corp. Flooring Program>>");

    }

    public int printMenuAndGetChoice() {
        io.print("==== Main Menu ====");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");
        int menuChoice = 0;
        boolean menuErrors = false;
        do {
            try {
                menuChoice = io.readInt("Please select from the above choices", 1, 6);
                menuErrors = false;
            } catch (NumberFormatException ex) {
                menuErrors = true;
                io.print("Invalid menu choice, please select menu options 1-6.");
            }
        } while (menuErrors);
        return menuChoice;
    }

    public LocalDate getSelectedOrderDate() {
        boolean dateErrors = false;
        LocalDate localDate = LocalDate.now();

        do {
            try {
                localDate = io.readLocalDate("Please enter the order date (format MM-DD-YYYY):");
                dateErrors = false;
            } catch (DateTimeParseException ex) {
                dateErrors = true;
                io.print("Invalid entry, please enter a date in MM-DD-YYYY format");
            }
        } while (dateErrors);
        return localDate;
    }

    public int getOrderNumber() {
        int orderNumber = 0;
        boolean numberErrors = false;
        do {
            try {
                orderNumber = io.readInt("Please enter the order number:");
                numberErrors = false;
            } catch (NumberFormatException ex) {
                numberErrors = true;
                io.print("Invalid entry, order number must be a numeric value.");
            }
        } while (numberErrors);
        return orderNumber;
    }

    public void displayAllOrdersBanner(LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        io.print("===================Displaying all orders for " + formattedDate + "===================");
    }

    public void displayAllOrdersForSelectedDate(List<Orders> orders) {
        for (Orders o : orders) {
            io.print("Order Number: " + o.getOrderNumber());
            io.print("Customer Name: " + o.getCustomerName());
            io.print("State: " + o.getState());
            io.print("Tax Rate: " + o.getTaxRate() + "%");
            io.print("Product Type: " + o.getProductType());
            io.print("Area: " + o.getArea() + " Sq Ft");
            io.print("Cost per Sq Ft: $" + o.getCostPerSquareFoot());
            io.print("Labor cost per Sq Ft: $" + o.getLaborCostPerSquareFoot());
            io.print("Material Cost: $" + o.getMaterialCost());
            io.print("Labor Cost: $" + o.getLaborCost());
            io.print("Total Tax: $" + o.getTax());
            io.print("Order Total: $" + o.getTotal());
            io.print("-------------------------------------------------------------------------------");
        }
        io.print("");
    }

    public void displayCreateNewOrderBanner() {
        io.print("=======Create New Order=======");
    }

    public void displayNewOrderDate(LocalDate newOrderDate) {
        String formattedDate = newOrderDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        io.print("New Order Date: " + formattedDate);
    }

    public Orders createNewOrder() {
        BigDecimal areaString = new BigDecimal(0);
        String area = "";
        String customerName = "";

        do {
            customerName = io.readString("Please enter the customer name for the order: ");
            if (customerName.contains("^")) {
                io.print("Customer name cannot contain the symbol ^, please correct");
            }
        } while (customerName.contains("^"));

        String stateName = io.readString("Please enter the State for the order (TX, WA, KY, CA): ");
        String productType = io.readString("Please enter a product type for your order (Carpet, Laminate, Tile, Wood): ");
        do {
            try {
                area = io.readString("Please enter the area for the order in Sq Ft (Minimum 100 SqFt - Maximum 100,000 SqFt): ");
                if (!area.isEmpty()) { //&& area.contains("[0-9]+")){
                    areaString = new BigDecimal(area);
                }
                if (areaString.compareTo(new BigDecimal(100)) < 0 || areaString.compareTo(new BigDecimal(100000)) > 0 || area.isEmpty()) {
                    io.print("Invalid area. Area must be a between 100-100,000 SqFt");
                }
            } catch (NumberFormatException ex) {
                io.print("Invalid area. Area must be a between 100-100,000 SqFt");
            }

        } while (areaString.compareTo(new BigDecimal(100)) < 0 || areaString.compareTo(new BigDecimal(100000)) > 0 || area.isEmpty()); //|| !area.contains("[0-9]+"));

        
        Orders o = new Orders(customerName, stateName, productType, areaString);
        return o;
    }

    public void displayAllStates(List<StateTax> states) {
        io.print("*States TSG Corp. serves*");
        for (StateTax s : states) {
            io.print(s.getStateAbbreviation() + " - " + s.getStateName());
        }
        io.print("");
    }

    public void displayAllProducts(List<Products> products) {
        io.print("*Product types at TSG Corp.*");
        for (Products p : products) {
            io.print(p.getProductType());
        }
        io.print("");
    }

    public void displayNewOrderSummary(Orders newOrder, LocalDate date) {
        date = newOrder.getOrderDate();
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        io.print("======= New Order Summary =======");
        io.print("Date: " + formattedDate);
        io.print("Order Number: " + newOrder.getOrderNumber());
        io.print("Customer Name: " + newOrder.getCustomerName());
        io.print("State: " + newOrder.getState());
        io.print("Tax Rate for your State: " + newOrder.getTaxRate() + "%");
        io.print("Product Type: " + newOrder.getProductType());
        io.print("Area: " + newOrder.getArea() + " Sq Ft");
        io.print("Cost per Sq Ft: $" + newOrder.getCostPerSquareFoot());
        io.print("Labor cost per Sq Ft: $" + newOrder.getLaborCostPerSquareFoot());
        io.print("Material cost for your order: $" + newOrder.getMaterialCost());
        io.print("Labor cost for your order: $" + newOrder.getLaborCost());
        io.print("Total Taxes for your order: $" + newOrder.getTax());
        io.print("Grand Total for your order: $" + newOrder.getTotal());
    }

    public String confirmNewOrder() {
        return io.readString("Place order? confirm(y/n)");
    }

    public void displayCreateSuccessBanner(Orders newOrder) {
        io.readString("====Order #" + newOrder.getOrderNumber() + " has been submitted successfully! Hit enter to return to the Main Menu====");
    }

    public String getUpdatedCustomerName(Orders selectedOrder) {
        String editedName = "";
        do {
            editedName = io.readString("Enter the updated customer name for the order (" + selectedOrder.getCustomerName() + "):");
            if (editedName.contains("^")) {
                io.print("Customer name cannot contain the symbol ^, please correct");
            }
        } while (editedName.contains("^"));

        return editedName;
    }

    public String getUpdatedState(Orders selectedOrder) {
        return io.readString("Enter the updated State for the order (" + selectedOrder.getState() + "):");
    }

    public String getUpdatedProductType(Orders selectedOrder) {
        return io.readString("Enter the updated product type for the order (" + selectedOrder.getProductType() + "):");
    }

    public String getUpdatedArea(Orders selectedOrder) {
        BigDecimal areaString = new BigDecimal(0);
        String area = "";
        
         do {
            try {
                area = io.readString("Please enter the area for the order in Sq Ft (Minimum 100 SqFt - Maximum 100,000 SqFt): ");
                if (area.isEmpty()) {
                    return area;
                }
                if(!area.isEmpty()){
                   areaString = new BigDecimal(area);
                }
                if (areaString.compareTo(new BigDecimal(100)) < 0 || areaString.compareTo(new BigDecimal(100000)) > 0 ) {
                    io.print("Invalid area. Area must be a between 100-100,000 SqFt");
                }
            } catch (NumberFormatException ex) {
                io.print("Invalid area. Area must be a between 100-100,000 SqFt");
            }
         } while (areaString.compareTo(new BigDecimal(100)) < 0 || areaString.compareTo(new BigDecimal(100000)) > 0 || area.isEmpty());
           return area;
           
    }

    public void editSummary(Orders updatedOrder) {
        io.print("======= Edit Summary=======");
        io.print("Customer Name: " + updatedOrder.getCustomerName());
        io.print("State : " + updatedOrder.getState());
        io.print("Product Type: " + updatedOrder.getProductType());
        io.print("Area: " + updatedOrder.getArea());
        io.print("======== Costs =========");
        io.print("Cost per Sq Ft: $" + updatedOrder.getCostPerSquareFoot());
        io.print("Labor cost per Sq Ft: $" + updatedOrder.getLaborCostPerSquareFoot());
        io.print("Material cost for your order: $" + updatedOrder.getMaterialCost());
        io.print("Labor cost for your order: $" + updatedOrder.getLaborCost());
        io.print("Total Taxes for your order: $" + updatedOrder.getTax());
        io.print("Grand Total for your order: $" + updatedOrder.getTotal());
    }

    public String confirmEdit() {
        return io.readString("Would you like to save this edit? confirm(y/n)");
    }

    public void displayNewOrderNotSaved() {
        io.print("==== Order cancelled ====");
    }

    public void displayEditingBanner(int orderNumber, LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        io.print("===Now editing order #" + orderNumber + " from " + formattedDate + "===");
    }

    public void displayBeforeEditSummary(Orders order) {
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Tax Rate: " + order.getTaxRate() + "%");
        io.print("Product Type: " + order.getProductType());
        io.print("Area: " + order.getArea() + " Sq Ft");
        io.print("Cost per Sq Ft: $" + order.getCostPerSquareFoot());
        io.print("Labor cost per Sq Ft: $" + order.getLaborCostPerSquareFoot());
        io.print("Material Cost: $" + order.getMaterialCost());
        io.print("Labor Cost: $" + order.getLaborCost());
        io.print("Total Tax: $" + order.getTax());
        io.print("Order Total: $" + order.getTotal());
        io.print("=============================================");
        io.print("If no change for a field, leave it blank and hit enter.");
    }

    public void displayEditSuccessBanner(int orderNumber, LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        io.print("===Order #" + orderNumber + " from " + formattedDate + " has been edited successfully===");
    }

    public void displayOrderNotEdit() {
        io.print("==== Edit cancelled! ====");
    }

    public String confirmRemoval(int orderNumber, LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        return io.readString("Are you sure you would like to remove Order #" + orderNumber + " from " + formattedDate + "? confirm(y/n)");
    }

    public void displayRemovalCancelled(int orderNumber) {
        io.print("==== Removal cancelled for Order#" + orderNumber + " ====");
    }

    public void displayOrderSummaryForRemove(Orders order) {
        io.print("======= Order Summary for Order#" + order.getOrderNumber() + "=======");
        io.print("Date: " + order.getOrderDate());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Tax Rate for order State: " + order.getTaxRate() + "%");
        io.print("Product Type: " + order.getProductType());
        io.print("Area: " + order.getArea() + " Sq Ft");
        io.print("Cost per Sq Ft: $" + order.getCostPerSquareFoot());
        io.print("Labor cost per Sq Ft: $" + order.getLaborCostPerSquareFoot());
        io.print("Material cost for this order: $" + order.getMaterialCost());
        io.print("Labor cost for this order: $" + order.getLaborCost());
        io.print("Total Taxes for this order: $" + order.getTax());
        io.print("Grand Total for this order: $" + order.getTotal());
    }

    public void displayRemovalSuccessBanner(int orderNumber, LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        io.print("===Order #" + orderNumber + " that was placed on " + formattedDate + " deleted successfully===");
    }

    public void displayExportData() {
        io.print("===All data exported successfully!===");
    }

    public void displayExitMessage() {
        io.print("Exiting the TSG Corp. Flooring program, thanks for using it!");
    }

    public void printError(String message) {
        io.print("ERROR: " + message);
    }

}
