package com.example.infs3605ess;

// Product class will provide dataset for the adapter
public class Product {
    private String name;
    private int price;

    public Product(String mName, int mPrice){
        this.name = mName;
        this.price = mPrice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
