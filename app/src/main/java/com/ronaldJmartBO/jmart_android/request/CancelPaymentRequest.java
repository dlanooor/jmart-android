package com.ronaldJmartBO.jmart_android.request;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.ronaldJmartBO.jmart_android.activity.AccountInvoiceDetail;
import com.ronaldJmartBO.jmart_android.model.ProductCategory;

import java.util.HashMap;
import java.util.Map;

public class CancelPaymentRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/payment/" + AccountInvoiceDetail.paymentId + "/cancel";
    private final Map<String, String> params;

    public CancelPaymentRequest(Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
    }
    public Map<String, String> getParams() {
        return params;
    }
}
