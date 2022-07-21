package com.example.infs3605ess.ui;

public class Description {
    private String name;
    private int quantity;
    private String price;
    private String total;

    Description(String mName,int mQuantity, String mPrice, String mTotal){
        this.name = mName;
        this.quantity = mQuantity;
        this.price = mPrice;
        this.total = mTotal;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }


}
