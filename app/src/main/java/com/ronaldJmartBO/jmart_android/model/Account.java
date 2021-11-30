package com.ronaldJmartBO.jmart_android.model;

public class Account extends Serializable{
    public String name;
    public String email;
    public String password;
    public double balance;
    public Store store;
    public static final String REGEX_EMAIL = "^\\w+([\\.]?[&\\*~\\w+])*@\\w+([\\.-]?)*(\\.\\w{2,3})+$";
    public static final String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$)(?=.*[A-Z]).{8,}$";
}