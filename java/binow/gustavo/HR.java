/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binow.gustavo;

/**
 *
 * @author binow
 */
public class HR {
    
    String name;
    
    Double cost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    String getCostAsString() {
        return cost != -1 ? cost.toString() : "";
    }
    
    
    
}
