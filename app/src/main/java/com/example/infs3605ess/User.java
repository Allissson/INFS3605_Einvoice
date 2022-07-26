package com.example.infs3605ess;

import android.text.style.IconMarginSpan;

public class User {

    public String companyName, userName,email;
    public int bonus;

    public User(){

    }

    public String getFullName() {
        return companyName;
    }

    public void setFullName(String fullName) {
        this.companyName = companyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String companyName, String userName, String email,int bonus){
        this.email=email;
        this.companyName= companyName;
        this.userName=userName;
        this.bonus=bonus;
    }
}
