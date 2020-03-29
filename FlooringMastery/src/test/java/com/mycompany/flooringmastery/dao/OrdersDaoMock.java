/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.dao;

import com.mycompany.flooringmastery.dto.Orders;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jenni
 */
public class OrdersDaoMock implements OrdersDao {

    public Orders mockOrder;
    List<Orders> orders = new ArrayList<>();

    public OrdersDaoMock() {
        mockOrder = new Orders();
        mockOrder.setOrderDate(LocalDate.parse("03012020", DateTimeFormatter.ofPattern("MMddyyyy")));
        mockOrder.setOrderNumber(1);
        mockOrder.setCustomerName("Floors and more");
        mockOrder.setState("TX");
        mockOrder.setTaxRate(new BigDecimal("5.50"));
        mockOrder.setProductType("Tile");
        mockOrder.setArea(new BigDecimal(200));
        mockOrder.setCostPerSquareFoot(new BigDecimal("3.65"));
        mockOrder.setLaborCostPerSquareFoot(new BigDecimal("7.75"));
        BigDecimal orderMaterialCost = (mockOrder.getArea().multiply(mockOrder.getCostPerSquareFoot())).setScale(2, RoundingMode.HALF_UP);
        mockOrder.setMaterialCost(orderMaterialCost);
        BigDecimal orderLaborCost = (mockOrder.getArea().multiply(mockOrder.getLaborCostPerSquareFoot())).setScale(2, RoundingMode.HALF_UP);
        mockOrder.setLaborCost(orderLaborCost);
        BigDecimal orderTaxTotal = (mockOrder.getMaterialCost().add(mockOrder.getLaborCost())).multiply(mockOrder.getTaxRate().divide(new BigDecimal(100))).setScale(2, RoundingMode.HALF_UP);
        mockOrder.setTax(orderTaxTotal);
        BigDecimal orderTotal = (mockOrder.getLaborCost().add(mockOrder.getMaterialCost()).add(mockOrder.getTax()));
        mockOrder.setTotal(orderTotal);
        orders.add(mockOrder);
    }

    public OrdersDaoMock(Orders testOrder) {
        this.mockOrder = testOrder;
    }

    @Override
    public List<Orders> getAllOrdersByDate(LocalDate date) throws DaoPersistenceException {
        if (mockOrder.getOrderDate().equals(date)) {
            return orders;
        } else {
           throw new DaoPersistenceException("");
            
        }
    }

    @Override
    public Orders addNewOrder(Orders newOrder) throws DaoPersistenceException {
        return newOrder;
    }

    @Override
    public Orders editOrder(Orders updateOrder, LocalDate orderDate) throws DaoPersistenceException {
        if (mockOrder.getOrderNumber() == updateOrder.getOrderNumber() && mockOrder.getOrderDate().equals(orderDate)) {
            return mockOrder;
        } else{
        return null;
    }
}

@Override
        public Orders removeOrder(int orderNumber, LocalDate orderDate) throws DaoPersistenceException {
      if(orderNumber == mockOrder.getOrderNumber() && mockOrder.getOrderDate().equals(orderDate)){
          return mockOrder;
      } else{
          return null;
      }
    }

    @Override
        public Orders getOrderByOrderNumber(int orderNumber, LocalDate date) throws DaoPersistenceException {
        if(orderNumber == mockOrder.getOrderNumber() && mockOrder.getOrderDate().equals(date)){
          return mockOrder;
      } else{
      return null;
        }
    }

    @Override
        public Orders setOrderNumber(Orders newOrderNumber) throws DaoPersistenceException, IOException {
        return newOrderNumber;
    }

   

    @Override
        public void exportAll() throws DaoPersistenceException, FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
