package com.ronaldJmartBO.jmart_android.model;

public class Product extends Serializable
{
    public int accountId;
    public ProductCategory category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public byte shipmentPlans;
    public int weight;

    @Override
    public String toString(){
        return  "accountID: " + accountId +
                "\nName: " + name +
                "\nWeight: " + weight +
                "\nconditionUsed: " + conditionUsed +
                "\nprice: " + price +
                "\nshipmentPlans: " + shipmentPlans +
                "\ndiscount: " + discount +
                "\ncategory: " + category;
    }
}