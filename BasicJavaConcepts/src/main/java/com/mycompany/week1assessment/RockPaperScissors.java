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
public class RockPaperScissors {

    public static void main(String[] args) {
        int playAgain = 0;
        do {
            //declared the number variables and initialized to 0
            int numRounds = 0;
            int userChoice = 0;
            int countWins = 0;
            int countLosses = 0;
            int countTies = 0;

            //declared and initialized String variables to hold user input
            String stringRounds = "";
            String stringChoice = "";
            String stringPlayAgain = "";

            //declared and initialized a Scanner object
            Scanner myScanner = new Scanner(System.in);

            //Ask user to input the # of rounds they want to play
            System.out.println("How many rounds do you want to play?");
            stringRounds = myScanner.nextLine();
            //convert the String value into an int value
            numRounds = Integer.parseInt(stringRounds);

            if (numRounds > 0 && numRounds <= 10) {
            } else {
                System.out.println("Error! user must play between 1-10 rounds");
                System.exit(0);
            }

            //for loop to allow for iteration based on amount of rounds user is playing
            for (int x = 0; x < numRounds; x++) {
                //Ask user to input their choice for the round 
                System.out.println("What is your choice? 1=Rock 2=Paper 3=Scissors");
                stringChoice = myScanner.nextLine();
                userChoice = Integer.parseInt(stringChoice);

                //Generate a choice for the computer, between 1 and 3
                System.out.print("Computer choice: ");
                Random rng = new Random();

                int randomNumber = rng.nextInt(3) + 1;
                System.out.println(randomNumber);
                //Display results of the round
                System.out.print("Round results: ");
                if (userChoice == 1 && randomNumber == 2) {
                    System.out.println("You Lost!");
                    countLosses++;
                } else if (userChoice == 1 && randomNumber == 3) {
                    System.out.println("You Won!");
                    countWins++;
                } else if (userChoice == 2 && randomNumber == 3) {
                    System.out.println("You Lost!");
                    countLosses++;
                } else if (userChoice == 2 && randomNumber == 1) {
                    System.out.println("You Won!");
                    countWins++;
                } else if (userChoice == 3 && randomNumber == 2) {
                    System.out.println("You Won!");
                    countWins++;
                } else if (userChoice == 3 && randomNumber == 1) {
                    System.out.println("You Lost!");
                    countLosses++;
                } else {
                    System.out.println("Its a Tie!");
                    countTies++;
                }

            }

            //Declare overall winner based on who won the most rounds
            System.out.println("Final Results");
            System.out.println("Ties = " + countTies);
            System.out.println("Wins = " + countWins);
            System.out.println("Lost = " + countLosses);
            if (countWins > countLosses && countWins > countTies) {
                System.out.println("Overall Winner: You won!!");
            } else if (countLosses > countWins && countLosses > countTies) {
                System.out.println("Overall Winner: Computer");
            } else {
                System.out.println("Overall Winner: Tied game");
            }

            //Ask user if they want to play again
            System.out.println("Do you wish to play again? 1=YES 2=NO");
            stringPlayAgain = myScanner.nextLine();
            playAgain = Integer.parseInt(stringPlayAgain);
        } while (playAgain == 1);
        System.out.println("Thanks for playing!");
        //method exits current program by terminating JVM
        System.exit(0);
    }

}
