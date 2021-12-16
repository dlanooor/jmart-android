package com.ronaldJmartBO.jmart_android.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * The type Payment.
 */
public class Payment extends Invoice {
    /**
     * The type Record.
     */
    public static class Record {
        /**
         * The Status.
         */
        public Invoice.Status status;
        /**
         * The Date.
         */
        public final Date date;
        /**
         * The Message.
         */
        public String message;

        /**
         * Instantiates a new Record.
         *
         * @param status  the status
         * @param message the message
         */
        public Record (Invoice.Status status, String message) {
            this.status = status;
            this.message = message;
            date = new Date();
        }
    }

    /**
     * The Shipment.
     */
    public Shipment shipment;
    /**
     * The Product count.
     */
    public int productCount;
    /**
     * The History.
     */
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
