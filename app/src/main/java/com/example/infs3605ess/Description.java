package com.example.infs3605ess;

import java.io.Serializable;

public class Description implements Serializable {
    private String name;
    private int quantity;
    private double price;
    private double total;

    Description(String mName,int mQuantity, double mPrice, double mTotal){
        this.name = mName;
        this.quantity = mQuantity;
        this.price = mPrice;
        this.total = mTotal;
    }

    public Description(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


}
