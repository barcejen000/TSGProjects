/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author jenni
 */
public class Products {
    
    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal costLaborPerSquareFoot;
    
    public Products(String productType, BigDecimal costPerSquareFoot, BigDecimal costLaborPerSquareFoot){
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.costLaborPerSquareFoot = costLaborPerSquareFoot;
}
    
    public Products(){
}

    public String getProductType() {
        return productType;
    }

   
    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }


    public BigDecimal getCostLaborPerSquareFoot() {
        return costLaborPerSquareFoot;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.productType);
        hash = 19 * hash + Objects.hashCode(this.costPerSquareFoot);
        hash = 19 * hash + Objects.hashCode(this.costLaborPerSquareFoot);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Products other = (Products) obj;
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.costPerSquareFoot, other.costPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.costLaborPerSquareFoot, other.costLaborPerSquareFoot)) {
            return false;
        }
        return true;
    }
    
}
