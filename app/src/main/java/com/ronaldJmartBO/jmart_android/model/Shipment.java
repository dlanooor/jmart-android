package com.ronaldJmartBO.jmart_android.model;

import java.text.SimpleDateFormat;

/**
 * The Shipment Model.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class Shipment {
    /**
     * The Address.
     */
    public String address;
    /**
     * The Cost.
     */
    public int cost;
    /**
     * The Plan.
     */
    public byte plan;
    /**
     * The Receipt.
     */
    public String receipt;
    /**
     * The constant INSTANT.
     */
    public static final Plan INSTANT = new Plan((byte)(1 << 0));
    /**
     * The constant SAME_DAY.
     */
    public static final Plan SAME_DAY = new Plan((byte)(1 << 1));
    /**
     * The constant NEXT_DAY.
     */
    public static final Plan NEXT_DAY = new Plan((byte)(1 << 2));
    /**
     * The constant REGULER.
     */
    public static final Plan REGULER = new Plan((byte)(1 << 3));
    /**
     * The constant KARGO.
     */
    public static final Plan KARGO = new Plan((byte)(1 << 4));
    /**
     * The constant ESTIMATION_FORMAT.
     */
    public static final SimpleDateFormat ESTIMATION_FORMAT = new SimpleDateFormat("EEE MMMM dd yyyy");

    /**
     * The type Plan.
     */
    public static class Plan {
        /**
         * The Bit.
         */
        public final byte bit;
        private Plan(byte bit){
            this.bit = bit;
        }
    }
}
