package com.ronaldJmartBO.jmart_android.model;

/**
 * Store Model
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class Store extends Serializable{
    /**
     * The Name.
     */
    public String name;
    /**
     * The Address.
     */
    public String address;
    /**
     * The Phone number.
     */
    public String phoneNumber;
    /**
     * The Balance.
     */
    public double balance;
    /**
     * The constant REGEX_PHONE.
     */
    public static final String REGEX_PHONE = "^[0-9]{9,12}\b";
    /**
     * The constant REGEX_NAME.
     */
    public static final String REGEX_NAME = "^[A-Z][a-z\\sa-z]{4,19}\b";
}