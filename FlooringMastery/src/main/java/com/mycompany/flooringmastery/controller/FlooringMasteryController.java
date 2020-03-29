/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.controller;

import com.mycompany.flooringmastery.dao.DaoPersistenceException;
import com.mycompany.flooringmastery.dto.Orders;
import com.mycompany.flooringmastery.dto.Products;
import com.mycompany.flooringmastery.dto.StateTax;
import com.mycompany.flooringmastery.service.DoNotServeStateException;
import com.mycompany.flooringmastery.service.FlooringMasteryDataValidationException;
import com.mycompany.flooringmastery.service.FlooringMasteryService;
import com.mycompany.flooringmastery.service.OrderNumberDoesNotExistException;
import com.mycompany.flooringmastery.service.ProductNotSoldByCompanyException;
import com.mycompany.flooringmastery.ui.FlooringMasteryView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author jenni
 */
public class FlooringMasteryController {

    private FlooringMasteryView view;
    private FlooringMasteryService service;

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        view.displayWelcome();
        boolean keepGoing = true;
        int menuChoice = 0;

        while (keepGoing) {
            try {
                menuChoice = getMenuChoice();

                switch (menuChoice) {
                    case 1:
                        displayAllOrdersByDate();
                        break;
                    case 2:
                        createNewOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportAllFiles();
                        break;
                    case 6:
                        view.displayExitMessage();
                        System.exit(0);
                }

            } catch (DaoPersistenceException | IOException | ProductNotSoldByCompanyException | DoNotServeStateException
                    | OrderNumberDoesNotExistException | FlooringMasteryDataValidationException ex) {
                view.printError(ex.getMessage());
            }
        }
    }

    private int getMenuChoice() {
        return view.printMenuAndGetChoice();
    }

    public void displayAllOrdersByDate() throws DaoPersistenceException, OrderNumberDoesNotExistException {
        LocalDate ld = view.getSelectedOrderDate();
        view.displayAllOrdersBanner(ld);
        view.displayAllOrdersForSelectedDate(service.getAllOrdersByDate(ld));
    }

    public void createNewOrder() throws DaoPersistenceException, ProductNotSoldByCompanyException, DoNotServeStateException, FlooringMasteryDataValidationException, IOException {
        view.displayCreateNewOrderBanner();
        displayAllStates();
        displayAllProducts();
        LocalDate ld = LocalDate.now();
        view.displayNewOrderDate(ld);
        Orders newOrder = view.createNewOrder();
        newOrder.setOrderDate(ld);
        service.setOrderNumber(newOrder, ld);
        service.newOrderAddOns(newOrder);
        view.displayNewOrderSummary(newOrder, ld);
        String confirmNewOrder = view.confirmNewOrder();
        if (confirmNewOrder.equalsIgnoreCase("y")) {
            service.addNewOrder(newOrder);
            view.displayCreateSuccessBanner(newOrder);
        } else {
            view.displayNewOrderNotSaved();
        }
    }

    public void displayAllStates() throws DaoPersistenceException {
        List<StateTax> states = service.getAllStates();
        view.displayAllStates(states);
    }

    public void displayAllProducts() throws DaoPersistenceException {
        List<Products> products = service.getAllProducts();
        view.displayAllProducts(products);
    }

    public void editOrder() throws DaoPersistenceException, ProductNotSoldByCompanyException, DoNotServeStateException, OrderNumberDoesNotExistException {
        LocalDate ld = view.getSelectedOrderDate();
        int orderNumber = view.getOrderNumber();
        //view.displayEditingBanner(orderNumber, ld);
        Orders updateOrder = service.getOrderByOrderNumber(orderNumber, ld);
        if (updateOrder != null) {
            view.displayEditingBanner(orderNumber, ld);
        }
        view.displayBeforeEditSummary(updateOrder);
        String customerName = view.getUpdatedCustomerName(updateOrder);
        if (!customerName.isEmpty()) {
            updateOrder.setCustomerName(customerName);
        }
        displayAllStates();
        String stateName = view.getUpdatedState(updateOrder);
        if (!stateName.isEmpty()) {
            updateOrder.setState(stateName);
        }
        displayAllProducts();
        String productType = view.getUpdatedProductType(updateOrder);
        if (!productType.isEmpty()) {
            updateOrder.setProductType(productType);
        }
        String area = view.getUpdatedArea(updateOrder);
        if (!area.isEmpty()) { 
            BigDecimal areaString = new BigDecimal(area);
            updateOrder.setArea(areaString);
        }
        service.editOrderAddOns(updateOrder);
        view.editSummary(updateOrder);
        String decision = view.confirmEdit();
        if (decision.equalsIgnoreCase("y")) {
            service.editOrder(updateOrder, ld);
            view.displayEditSuccessBanner(orderNumber, ld);
        } else {
            view.displayOrderNotEdit();
        }
    }

    public void removeOrder() throws DaoPersistenceException, OrderNumberDoesNotExistException {
        LocalDate ld = view.getSelectedOrderDate();
        int orderNumber = view.getOrderNumber();
        Orders removeOrder = service.getOrderByOrderNumber(orderNumber, ld);
        view.displayOrderSummaryForRemove(removeOrder);
        String deleteConfirmation = view.confirmRemoval(orderNumber, ld);
        if (deleteConfirmation.equalsIgnoreCase("y")) {
            service.removeOrder(orderNumber, ld);
            view.displayRemovalSuccessBanner(orderNumber, ld);
        } else {
            view.displayRemovalCancelled(orderNumber);
        }
    }

    public void exportAllFiles() throws IOException, FileNotFoundException, DaoPersistenceException {
        service.ExportAll();
        view.displayExportData();
    }

}
