package com.ronaldJmartBO.jmart_android.model;

/**
 * Account Model
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class Account extends Serializable{
    /**
     * The Name.
     */
    public String name;
    /**
     * The Email.
     */
    public String email;
    /**
     * The Password.
     */
    public String password;
    /**
     * The Balance.
     */
    public Double balance;
    /**
     * The Store.
     */
    public Store store;
    /**
     * The constant REGEX_EMAIL.
     */
    public static final String REGEX_EMAIL = "^\\w+([\\.]?[&\\*~\\w+])*@\\w+([\\.-]?)*(\\.\\w{2,3})+$";
    /**
     * The constant REGEX_PASSWORD.
     */
    public static final String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$)(?=.*[A-Z]).{8,}$";
}