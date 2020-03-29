/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.service;

import com.mycompany.flooringmastery.dao.OrdersDao;
import com.mycompany.flooringmastery.dao.DaoPersistenceException;
import com.mycompany.flooringmastery.dao.ProductsDao;
import com.mycompany.flooringmastery.dao.StateTaxDao;
import com.mycompany.flooringmastery.dto.Orders;
import com.mycompany.flooringmastery.dto.Products;
import com.mycompany.flooringmastery.dto.StateTax;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 *
 * @author jenni
 */
public class FlooringMasteryService {

    private OrdersDao ordersDao;
    private ProductsDao productsDao;
    private StateTaxDao taxDao;

    public FlooringMasteryService(OrdersDao ordersDao, ProductsDao productsDao, StateTaxDao taxDao) {
        this.ordersDao = ordersDao;
        this.productsDao = productsDao;
        this.taxDao = taxDao;
    }

    public List<Orders> getAllOrdersByDate(LocalDate date) throws DaoPersistenceException, OrderNumberDoesNotExistException {
        try{
        return ordersDao.getAllOrdersByDate(date);
        }catch(DaoPersistenceException ex){
            throw new OrderNumberDoesNotExistException("No orders exist for the date you entered ("+date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) +"), please verify your date is correct.");
        }
    }

    public Orders addNewOrder(Orders newOrder) throws DaoPersistenceException {
        return ordersDao.addNewOrder(newOrder);
    }

    public Orders getOrderByOrderNumber(int orderNumber, LocalDate date) throws DaoPersistenceException, OrderNumberDoesNotExistException {
        Orders orderNumberCheck = ordersDao.getOrderByOrderNumber(orderNumber, date);
        if(orderNumberCheck == null){
            throw new OrderNumberDoesNotExistException("Order Number entered does not exist for date " + date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        }
        return ordersDao.getOrderByOrderNumber(orderNumber, date);
    }

    public Orders editOrder(Orders updateOrder, LocalDate date) throws DaoPersistenceException {
        return ordersDao.editOrder(updateOrder, date);
    }

    public Orders removeOrder(int orderNumber, LocalDate orderDate) throws DaoPersistenceException, OrderNumberDoesNotExistException {
        Orders orderNumberCheck = ordersDao.getOrderByOrderNumber(orderNumber, orderDate);
        if(orderNumberCheck == null){
            throw new OrderNumberDoesNotExistException("Order Number entered does not exist for date " + orderDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        }
        return ordersDao.removeOrder(orderNumber, orderDate);
    }

    public void ExportAll() throws DaoPersistenceException, IOException, FileNotFoundException {
        ordersDao.exportAll();
    }

    public List<Products> getAllProducts() throws DaoPersistenceException {
        return productsDao.getallProducts();
    }
    
    public Products getProduct(String productName)throws DaoPersistenceException, ProductNotSoldByCompanyException{
        Products productCheck = productsDao.getProduct(productName);
        if(productCheck == null){
            throw new ProductNotSoldByCompanyException("Product type is not available at TSG Corp.");
        }
        return productsDao.getProduct(productName);
    }
    
    public List<StateTax> getAllStates() throws DaoPersistenceException {
        return taxDao.getallStates();
    }
    
    public StateTax getState(String stateName) throws DaoPersistenceException, DoNotServeStateException{
        StateTax stateCheck = taxDao.getState(stateName);
        if(stateCheck == null){
            throw new DoNotServeStateException("TSG Corp. does not conduct any business in your State.");
        }
        return taxDao.getState(stateName);
    }
    
    public Orders newOrderAddOns(Orders orderComponents) throws DaoPersistenceException, DoNotServeStateException, ProductNotSoldByCompanyException, FlooringMasteryDataValidationException{
        validateNewOrderInformation(orderComponents);
        isBusinessConductedInThisState(orderComponents);
        isProductSoldByCompany(orderComponents);
        taxSetUp(orderComponents);
        materialSetUp(orderComponents);
        laborSetUp(orderComponents);
        orderCalculations(orderComponents);
        return orderComponents;
    }
    
    public Orders editOrderAddOns(Orders orderComponents) throws DaoPersistenceException, DoNotServeStateException, ProductNotSoldByCompanyException{
        isBusinessConductedInThisState(orderComponents);
        isProductSoldByCompany(orderComponents);
        taxSetUp(orderComponents);
        materialSetUp(orderComponents);
        laborSetUp(orderComponents);
        orderCalculations(orderComponents);
        return orderComponents;
    }
    
    public Orders setOrderNumber(Orders orders, LocalDate date) throws DaoPersistenceException, IOException {
        return ordersDao.setOrderNumber(orders);
    }
    
    private void taxSetUp(Orders orders) throws DaoPersistenceException {
        String stateAbbreviation = orders.getState();
        StateTax customerState = taxDao.getState(stateAbbreviation);
        BigDecimal taxRate = customerState.getTaxRate();
        orders.setTaxRate(taxRate);
    }

    private void materialSetUp(Orders orders) throws DaoPersistenceException {
        String productName = orders.getProductType();
        Products customerProduct = productsDao.getProduct(productName);
        orders.setProductType(customerProduct.getProductType());
        BigDecimal costPerSqFt = customerProduct.getCostPerSquareFoot();
        orders.setCostPerSquareFoot(costPerSqFt);
       
    }

    private void laborSetUp(Orders orders) throws DaoPersistenceException {
        String productName = orders.getProductType();
        Products customerProduct = productsDao.getProduct(productName);
        BigDecimal laborCostPerSqFt = customerProduct.getCostLaborPerSquareFoot();
        orders.setLaborCostPerSquareFoot(laborCostPerSqFt);
    }

    private void orderCalculations(Orders orders) {
       BigDecimal orderMaterialCost = (orders.getArea().multiply(orders.getCostPerSquareFoot())).setScale(2, RoundingMode.HALF_UP);
       orders.setMaterialCost(orderMaterialCost);
       BigDecimal orderLaborCost = (orders.getArea().multiply(orders.getLaborCostPerSquareFoot())).setScale(2, RoundingMode.HALF_UP);
       orders.setLaborCost(orderLaborCost);
       BigDecimal orderTaxTotal = (orders.getMaterialCost().add(orders.getLaborCost())).multiply(orders.getTaxRate().divide(new BigDecimal(100))).setScale(2, RoundingMode.HALF_UP);
       orders.setTax(orderTaxTotal);
       BigDecimal orderTotal =(orders.getLaborCost().add(orders.getMaterialCost()).add(orders.getTax()));
       orders.setTotal(orderTotal);
    }

    private void isProductSoldByCompany(Orders orders) throws DaoPersistenceException, ProductNotSoldByCompanyException {
        String productName = orders.getProductType();
        Products customerProduct = productsDao.getProduct(productName);
        if (customerProduct == null) {
            throw new ProductNotSoldByCompanyException("Product type is not available at TSG Corp.");
        }
    }

    private void isBusinessConductedInThisState(Orders orders) throws DaoPersistenceException, DoNotServeStateException {
        String stateAbbreviation = orders.getState();
        StateTax customerState = taxDao.getState(stateAbbreviation);
        if (customerState == null) {
            throw new DoNotServeStateException("TSG Corp. does not conduct any business in your State.");
        }
    }
    
    private void validateNewOrderInformation(Orders orders) throws FlooringMasteryDataValidationException{
        if(orders.getCustomerName()== null
                ||orders.getCustomerName().trim().length() == 0 
                ||orders.getState() == null
                ||orders.getState().trim().length() == 0
                ||orders.getProductType() == null
                ||orders.getProductType().trim().length() == 0
                ||orders.getArea() == null || orders.getArea().compareTo(new BigDecimal("0")) ==  0){
        throw new FlooringMasteryDataValidationException(" All fields [Customer Name, State, Product Type, Area(Min 100 SqFt-Max 100,000 SqFt)] are required.");
    }
}
    
}
