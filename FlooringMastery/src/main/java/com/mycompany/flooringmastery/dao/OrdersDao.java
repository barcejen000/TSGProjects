/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.dao;

import com.mycompany.flooringmastery.dto.Orders;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author jenni
 */
public interface OrdersDao {
    
    List<Orders> getAllOrdersByDate(LocalDate date) throws DaoPersistenceException;
    Orders addNewOrder(Orders newOrder) throws DaoPersistenceException;
    Orders editOrder(Orders updateOrder, LocalDate orderDate) throws DaoPersistenceException;
    Orders removeOrder(int orderNumber, LocalDate orderDate) throws DaoPersistenceException;
    Orders getOrderByOrderNumber(int orderNumber, LocalDate date) throws DaoPersistenceException;
    void exportAll() throws DaoPersistenceException, FileNotFoundException, IOException;
    Orders setOrderNumber(Orders newOrderNumber) throws DaoPersistenceException, IOException;
}
