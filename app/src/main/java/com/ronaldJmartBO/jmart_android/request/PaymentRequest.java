package com.ronaldJmartBO.jmart_android.request;

import static com.ronaldJmartBO.jmart_android.activity.MainActivity.loggedId;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * The Payment request.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class PaymentRequest extends StringRequest {
    private static String id = loggedId;
    private static final String URL = "http://10.0.2.2:8080/payment/" + id + "/invoice?page=%d&pageSize=%d";

    /**
     * Instantiates a new Payment request.
     *
     * @param page          the page
     * @param listener      the listener
     * @param errorListener the error listener
     */
    public PaymentRequest(Integer page, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, String.format(URL, page, 100), listener, errorListener);
        System.out.println("Payment Request :" + URL);
    }
}
