package com.ronaldJmartBO.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.ronaldJmartBO.jmart_android.activity.AccountInvoiceDetail;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Payment submit request.
 */
public class PaymentSubmitRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/payment/" + AccountInvoiceDetail.paymentId + "/submit";
    private final Map<String, String> params;

    /**
     * Instantiates a new Payment submit request.
     *
     * @param listener      the listener
     * @param errorListener the error listener
     */
    public PaymentSubmitRequest(Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("receipt", "Receipt");
    }
    public Map<String, String> getParams() {
        return params;
    }
}