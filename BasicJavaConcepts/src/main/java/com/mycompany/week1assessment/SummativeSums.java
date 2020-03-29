/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.week1assessment;

/**
 *
 * @author jenni
 */
public class SummativeSums {

    public static void main(String[] args) {

        int[] firstArray = {1, 90, -33, -55, 67, -16, 28, -55, 15};
        int[] secondArray = {999, -60, -77, 14, 160, 301};
        int[] thirdArray = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200, -99};

        System.out.println("#1 Array Sum: " + sumArray(firstArray));
        System.out.println("#2 Array Sum: " + sumArray(secondArray));
        System.out.println("#3 Array Sum: " + sumArray(thirdArray));

    }

    public static int sumArray(int chooseArray[]) {
        int sumFirstArray = 0;
        for (int i = 0; i < chooseArray.length; i++) {
            sumFirstArray += chooseArray[i];
        }
        return sumFirstArray;
    }

}
