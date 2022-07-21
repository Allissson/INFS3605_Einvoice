package com.example.infs3605ess;

public class Description {
    private String name;
    private int quantity;
    private int price;
    private int total;

    Description(String mName,int mQuantity, int mPrice, int mTotal){
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


}
