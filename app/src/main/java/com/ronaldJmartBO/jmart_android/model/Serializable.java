package com.ronaldJmartBO.jmart_android.model;

import java.util.HashMap;

/**
 * Serializable Model.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class Serializable {
    /**
     * The Id.
     */
    public int id;
    private static HashMap<Class<?>, Integer> mapCounter = new HashMap<>();
}
