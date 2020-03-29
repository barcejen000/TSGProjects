/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.week1assessment;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author jenni
 */
public class DogGenetics {

    public static void main(String[] args) {
        
        int totalArraySum = 0;
        String stringDogName = "";

        Scanner myScanner = new Scanner(System.in);

        System.out.print("What is your dog's name? ");
        stringDogName = myScanner.nextLine();

        System.out.println("Well then, I have this highly reliable report on " + stringDogName + "'s prestigious background right here.");

        System.out.println(stringDogName + " is: ");

        double[] randomNumbersArray = new double[5];
        Random rng = new Random();
        for (int i = 0; i < 5; i++) {
            int randomNumbers = rng.nextInt(100) + 1;
            randomNumbersArray[i] = randomNumbers;
            totalArraySum += randomNumbersArray[i];
        }
        
            double percentageEquationOne = 0;
            double percentageEquationTwo = 0;
            double percentageEquationThree = 0;
            double percentageEquationFour = 0;
            double percentageEquationFive = 0;

            percentageEquationOne = (randomNumbersArray[0] * 100) / totalArraySum;
            percentageEquationTwo = (randomNumbersArray[1] * 100) / totalArraySum;
            percentageEquationThree = (randomNumbersArray[2] * 100) / totalArraySum;
            percentageEquationFour = (randomNumbersArray[3] * 100) / totalArraySum;
            percentageEquationFive = (randomNumbersArray[4] * 100) / totalArraySum;

            System.out.println(percentageEquationOne + "% St. Bernard");
            System.out.println(percentageEquationTwo + "% Chihuahua");
            System.out.println(percentageEquationThree + "% Dramatic RedNosed Asian Pug");
            System.out.println(percentageEquationFour + "% Common Cur");
            System.out.println(percentageEquationFive + "% King Doberman");

            System.out.println("Wow, that's QUITE the dog!");
        }

       
    }

