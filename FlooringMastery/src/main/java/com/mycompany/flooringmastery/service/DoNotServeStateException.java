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
public class DoNotServeStateException extends Exception{

    public DoNotServeStateException(String message) {
        super(message);
    }

    public DoNotServeStateException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
