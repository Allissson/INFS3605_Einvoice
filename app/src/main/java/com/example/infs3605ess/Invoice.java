package com.example.infs3605ess;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.List;

public class Invoice {

    public String issuer;
    public String country;
    public String state;
    public String city;
    public String street;
    public String invoiceNum;

    public Bitmap getBankslip() {
        return bankslip;
    }

    public void setBankslip(Bitmap bankslip) {
        this.bankslip = bankslip;
    }

    public Bitmap bankslip;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String status;

    public Invoice(){

    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getShipHand() {
        return shipHand;
    }

    public void setShipHand(double shipHand) {
        this.shipHand = shipHand;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getExtra() {
        return extra;
    }

    public void setExtra(double extra) {
        this.extra = extra;
    }

    public List<Description> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(List<Description> descriptionList) {
        this.descriptionList = descriptionList;
    }

    public Date invoiceDate, dueDate;
    public double subTotal, shipHand, total, extra;

    public Invoice(String issuer, String country, String state, String city, String street, String invoiceNum, Date invoiceDate, Date dueDate, double subTotal, double shipHand, double total, double extra, List<Description> descriptionList,String status) {
        this.issuer = issuer;
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
        this.invoiceNum = invoiceNum;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.subTotal = subTotal;
        this.shipHand = shipHand;
        this.total = total;
        this.extra = extra;
        this.descriptionList = descriptionList;
        this.status=status;
    }

    public Invoice(String issuer, String country, String state, String city, String street, String invoiceNum, Date invoiceDate, Date dueDate, double subTotal, double shipHand, double total, double extra, List<Description> descriptionList,String status,Bitmap bankslip) {
        this.issuer = issuer;
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
        this.invoiceNum = invoiceNum;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.subTotal = subTotal;
        this.shipHand = shipHand;
        this.total = total;
        this.extra = extra;
        this.descriptionList = descriptionList;
        this.status=status;
        this.bankslip=bankslip;
    }

    public List<Description> descriptionList;
}
