/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.factorizerthymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author jenni
 */
@Controller
public class factorizercontroller {

    int number = 0;
    String error = "";
    int checkPerfect = 0;

    @GetMapping("/factorizergame")
    public String displayResults(Model model) {
        model.addAttribute("error", error);
        model.addAttribute("displayNumber", number);
        model.addAttribute("displayFactors", getResults());
        model.addAttribute("prime", checkPrime());
        model.addAttribute("perfect", checkPerfect());
        
        return "factorizergame";
    }

    @PostMapping("/number")
    public String submitNumber(Integer inputNumber) {
        if (inputNumber == null) {
            error = "Number field cannot be left empty";
        } else {
            error = "";
            number = inputNumber;
        }
        return "redirect:/factorizergame";
    }

    public String getResults() {
        String factors = "";
         checkPerfect = 0;
        for (int i = 1; i <= number; i++) {
             int checkFactor = number % i;
            if (0 == checkFactor) {
                checkPerfect += i;
                factors = factors + String.valueOf(i) + ", ";
            }
        }
        return factors;
    } 
   public boolean checkPerfect(){
       Boolean perfect = false;
        if (checkPerfect - number == number) {
            perfect = true;
        } else {
            perfect = false;
        }
        return perfect;
   }
   
   public boolean checkPrime(){
        Boolean prime = false;
        if (number + 1 == checkPerfect) {
            prime = true;
        } else {
            prime = false;
        }
        return prime;
    }
}
