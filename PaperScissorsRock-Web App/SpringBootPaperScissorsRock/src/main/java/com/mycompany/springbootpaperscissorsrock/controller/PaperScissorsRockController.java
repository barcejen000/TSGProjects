/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.springbootpaperscissorsrock.controller;

import java.util.Random;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jenni
 */
@RestController
public class PaperScissorsRockController {

    int countWins = 0;
    int countLosses = 0;
    int countTies = 0;
    int userRounds = 0;
    int userPlayCount = 0;

    @PostMapping("/GetNumRounds")
    public String getNumRounds(int numRounds) {

        String message = "";
        if (numRounds > 0 && numRounds <= 10) {
            message = String.format("You will be playing %s rounds", numRounds);
            userRounds = numRounds;
        } else {
            message = String.format("Number of rounds '%s' is invalid,"
                    + " user must play between 1-10 rounds", numRounds);
            throw new IllegalArgumentException(message);
        }
        userPlayCount = 0;
        countWins = 0;
        countLosses = 0;
        countTies = 0;
        return message;
    }

    @PostMapping("/GetUserChoice")
    public String getUserChoice(int userChoice) {

        Random rng = new Random();
        int randomNumber = rng.nextInt(3) + 1;
        String message = "";
        if (userPlayCount < userRounds) {
            if (userChoice == 1) {
                if (randomNumber == 2) {
                    message = String.format("You Lost! Computer chose %s = Paper", randomNumber);
                    countLosses++;
                    userPlayCount++;
                } else if (randomNumber == 3) {
                    message = String.format("You Won! Computer chose %s = Scissors", randomNumber);
                    countWins++;
                    userPlayCount++;
                } else {
                    message = String.format("It's a Tie! Computer chose %s = Rock", randomNumber);
                    countTies++;
                    userPlayCount++;
                }
            } else if (userChoice == 2) {
                if (randomNumber == 1) {
                    message = String.format("You Won! Computer chose %s = Rock", randomNumber);
                    countWins++;
                    userPlayCount++;
                } else if (randomNumber == 3) {
                    message = String.format("You Lost! Computer chose %s = Scissors", randomNumber);
                    countLosses++;
                    userPlayCount++;
                } else {
                    message = String.format("It's a Tie! Computer chose %s = Paper", randomNumber);
                    countTies++;
                    userPlayCount++;
                }
            } else if (userChoice == 3) {
                if (randomNumber == 1) {
                    message = String.format("You Lost! Computer chose %s = Rock", randomNumber);
                    countLosses++;
                    userPlayCount++;
                } else if (randomNumber == 2) {
                    message = String.format("You Won! Computer chose %s = Paper", randomNumber);
                    countWins++;
                    userPlayCount++;
                } else {
                    message = String.format("It's a Tie! Computer chose %s = Scissors", randomNumber);
                    countTies++;
                    userPlayCount++;
                }
            } else {
                message = String.format("User choice %s is invalid, must choose "
                        + "1 = Scissors 2 = Paper 3 = Rock", userChoice);
                throw new IllegalArgumentException(message);
            }
        } else {
            message = String.format("Reached max number of rounds allowed to play, %s ", userRounds);
        }
        userChoice = 0;
        return message;

    }

//    
    @GetMapping("/GetResults")
    public String getResults() {
        String message = String.format("Here are the results "
                + "\n Your Wins %s"
                + "\n Your Losses %s"
                + "\n Ties %s", countWins, countLosses, countTies);

        countWins = 0;
        countLosses = 0;
        countTies = 0;
        userPlayCount = 0;
        userRounds = 0;
        return message;

    }

}
