package com.ronaldJmartBO.jmart_android.model;

import java.util.Date;

public abstract class Invoice extends Serializable {
    public static enum Rating {
        NONE,
        BAD,
        NEUTRAL,
        GOOD;
    }

    public static enum Status {
        WAITING_CONFIRMATION,
        CANCELLED,
        ON_PROGRESS,
        ON_DELIVERY,
        COMPLAINT,
        FINISHED,
        DELIVERED,
        FAILED;
    }

    public Date date;
    public int buyerId;
    public int productId;
    public int complaintId;
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
