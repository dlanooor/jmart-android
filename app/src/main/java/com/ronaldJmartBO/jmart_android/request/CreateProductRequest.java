package com.ronaldJmartBO.jmart_android.request;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.ronaldJmartBO.jmart_android.model.ProductCategory;

import java.util.HashMap;
import java.util.Map;

/**
 * The Create product request.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class CreateProductRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/product/create";
    private final Map<String, String> params;

    /**
     * Instantiates a new Create product request.
     *
     * @param name          the name
     * @param weight        the weight
     * @param conditionUsed the condition used
     * @param price         the price
     * @param discount      the discount
     * @param category      the category
     * @param shipmentPlans the shipment plans
     * @param listener      the listener
     * @param errorListener the error listener
     */
    public CreateProductRequest(String name, int weight, boolean conditionUsed, double price, double discount, ProductCategory category, byte shipmentPlans, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("accountId", String.valueOf(getLoggedAccount().id));
        params.put("name", name);
        params.put("weight", String.valueOf(weight));
        params.put("conditionUsed", String.valueOf(conditionUsed));
        params.put("price", String.valueOf(price));
        params.put("discount", String.valueOf(discount));
        params.put("category", String.valueOf(category));
        params.put("shipmentPlans", String.valueOf(shipmentPlans));
    }

    public Map<String, String> getParams() {
        return params;
    }
}