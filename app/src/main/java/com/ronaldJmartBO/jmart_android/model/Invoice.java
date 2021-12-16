package com.ronaldJmartBO.jmart_android.model;

import java.util.Date;

/**
 * The type Invoice.
 */
public abstract class Invoice extends Serializable {
    /**
     * The enum Rating.
     */
    public static enum Rating {
        /**
         * None rating.
         */
        NONE,
        /**
         * Bad rating.
         */
        BAD,
        /**
         * Neutral rating.
         */
        NEUTRAL,
        /**
         * Good rating.
         */
        GOOD;
    }

    /**
     * The enum Status.
     */
    public static enum Status {
        /**
         * Waiting confirmation status.
         */
        WAITING_CONFIRMATION,
        /**
         * Cancelled status.
         */
        CANCELLED,
        /**
         * On progress status.
         */
        ON_PROGRESS,
        /**
         * On delivery status.
         */
        ON_DELIVERY,
        /**
         * Complaint status.
         */
        COMPLAINT,
        /**
         * Finished status.
         */
        FINISHED,
        /**
         * Delivered status.
         */
        DELIVERED,
        /**
         * Failed status.
         */
        FAILED;
    }

    /**
     * The Date.
     */
    public Date date;
    /**
     * The Buyer id.
     */
    public int buyerId;
    /**
     * The Product id.
     */
    public int productId;
    /**
     * The Complaint id.
     */
    public int complaintId;
    /**
     * The Rating.
     */
    public Rating rating;

    @Override
    public String toString() {
        return  "Date: " + date +
                "\nBuyerId: " + buyerId +
                "\nProductId: " + productId +
                "\nComplaintId: " + complaintId +
                "\nRating: " + rating;
    }
}
