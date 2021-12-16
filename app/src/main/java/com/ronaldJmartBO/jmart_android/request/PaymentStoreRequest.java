package com.ronaldJmartBO.jmart_android.request;

import static com.ronaldJmartBO.jmart_android.activity.MainActivity.loggedId;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * The type Payment store request.
 */
public class PaymentStoreRequest extends StringRequest {
    private static String id = loggedId;
    private static final String URL = "http://10.0.2.2:8080/payment/" + id + "/invoiceStore?page=%d&pageSize=%d";

    /**
     * Instantiates a new Payment store request.
     *
     * @param page          the page
     * @param listener      the listener
     * @param errorListener the error listener
     */
    public PaymentStoreRequest(Integer page, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, String.format(URL, page, 100), listener, errorListener);
        System.out.println("Payment Store Request :" + URL);
    }
}
