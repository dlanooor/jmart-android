package com.ronaldJmartBO.jmart_android.model;

import java.util.ArrayList;
import java.util.Date;

public class Payment extends Invoice {
    public static class Record {
        public Invoice.Status status;
        public final Date date;
        public String message;

        public Record (Invoice.Status status, String message) {
            this.status = status;
            this.message = message;
            date = new Date();
        }
    }
    public Shipment shipment;
    public int productCount;
    public ArrayList<Record> history;

    @Override
    public String toString() {
        return  "Address: " + shipment.address +
                "\nCost: " + shipment.cost +
                "\nPlan: " + shipment.plan +
                "\nReceipt: " + shipment.receipt +
                "\nProduct Count: " + productCount;
    }
}
