package com.ronaldJmartBO.jmart_android.request;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.ronaldJmartBO.jmart_android.activity.AccountInvoiceDetail;
import com.ronaldJmartBO.jmart_android.model.ProductCategory;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Cancel payment request.
 */
public class CancelPaymentRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/payment/" + AccountInvoiceDetail.paymentId + "/cancel";

    /**
     * Instantiates a new Cancel payment request.
     *
     * @param listener      the listener
     * @param errorListener the error listener
     */
    public CancelPaymentRequest(Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
    }
}
