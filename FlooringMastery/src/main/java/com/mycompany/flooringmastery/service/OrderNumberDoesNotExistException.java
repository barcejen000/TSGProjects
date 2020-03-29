/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.service;

/**
 *
 * @author jenni
 */
public class OrderNumberDoesNotExistException extends Exception {

    public OrderNumberDoesNotExistException(String message) {
        super(message);
    }

    public OrderNumberDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
