/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery;

import com.mycompany.flooringmastery.controller.FlooringMasteryController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jenni
 */
public class App {
    public static void main(String[] args) {
        
         ApplicationContext appContext
                = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        FlooringMasteryController controller = appContext.getBean("controller", FlooringMasteryController.class);
        controller.run();
    }
    
}
