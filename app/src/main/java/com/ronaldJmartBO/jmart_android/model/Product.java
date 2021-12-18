package com.ronaldJmartBO.jmart_android.model;

/**
 * Product Model
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class Product extends Serializable
{
    /**
     * The Account id.
     */
    public int accountId;
    /**
     * The Category.
     */
    public ProductCategory category;
    /**
     * The Condition used.
     */
    public boolean conditionUsed;
    /**
     * The Discount.
     */
    public double discount;
    /**
     * The Name.
     */
    public String name;
    /**
     * The Price.
     */
    public double price;
    /**
     * The Shipment plans.
     */
    public byte shipmentPlans;
    /**
     * The Weight.
     */
    public int weight;

    @Override
    public String toString(){
//        return  "Account ID: " + accountId +
//                "\nName: " + name +
//                "\nWeight: " + weight +
//                "\nCondition Used: " + conditionUsed +
//                "\nPrice: " + price +
//                "\nShipment Plans: " + shipmentPlans +
//                "\nDiscount: " + discount +
//                "\nCategory: " + category;
        return name;
    }
}