package com.ronaldJmartBO.jmart_android.request;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * The Product request.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class ProductRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/product/" + getLoggedAccount().id + "/store?page=%d&pageSize=%d";

    /**
     * Instantiates a new Product request.
     *
     * @param page          the page
     * @param listener      the listener
     * @param errorListener the error listener
     */
    public ProductRequest(Integer page, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, String.format(URL, page, 9), listener, errorListener);
    }
}