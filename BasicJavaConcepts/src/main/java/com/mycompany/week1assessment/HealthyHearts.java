/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.week1assessment;

import java.util.Scanner;

/**
 *
 * @author jenni
 */
public class HealthyHearts {
    public static void main(String[] args) {
       
       int userAge;
       int maxRate;
       double lowTarget;
       double highTarget;        
               
       String stringAge;
       
       Scanner myScanner = new Scanner(System.in);
       
        System.out.print("What is your age? ");
        stringAge = myScanner.nextLine();
        userAge = Integer.parseInt(stringAge);
        
        maxRate = 220 - userAge;
        System.out.println("Your maximum heart rate should be " + maxRate + " beats per minute.");
        
        lowTarget =  (maxRate * 0.5);
        highTarget =  (maxRate * 0.85);
        
        System.out.println("Your target HR Zone is " + Math.round(lowTarget) + " - " + Math.round(highTarget) + " beats per minute.");
        
    }
}
 