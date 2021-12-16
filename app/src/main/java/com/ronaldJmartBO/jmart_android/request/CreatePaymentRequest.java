package com.ronaldJmartBO.jmart_android.request;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.ronaldJmartBO.jmart_android.model.ProductCategory;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Create payment request.
 */
public class CreatePaymentRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/payment/create";
    private final Map<String, String> params;

    /**
     * Instantiates a new Create payment request.
     *
     * @param productId       the product id
     * @param productCount    the product count
     * @param shipmentAddress the shipment address
     * @param shipmentPlan    the shipment plan
     * @param listener        the listener
     * @param errorListener   the error listener
     */
    public CreatePaymentRequest(int productId, int productCount, String shipmentAddress, byte shipmentPlan, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("buyerId", String.valueOf(getLoggedAccount().id));
        params.put("productId", String.valueOf(productId));
        params.put("productCount", String.valueOf(productCount));
        params.put("shipmentAddress", shipmentAddress);
        params.put("shipmentPlan", String.valueOf(shipmentPlan));
    }

    public Map<String, String> getParams() {
        return params;
    }
}
