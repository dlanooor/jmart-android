package com.ronaldJmartBO.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.ronaldJmartBO.jmart_android.activity.AccountInvoiceDetail;

/**
 * The type Accept payment request.
 */
public class AcceptPaymentRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/payment/" + AccountInvoiceDetail.paymentId + "/accept";

    /**
     * Instantiates a new Accept payment request.
     *
     * @param listener      the listener
     * @param errorListener the error listener
     */
    public AcceptPaymentRequest(Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
    }
}
