package com.ronaldJmartBO.jmart_android.request;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class ProductRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/product/" + getLoggedAccount().id + "/store?page=%d&pageSize=%d";

    public ProductRequest(Integer page, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Request.Method.GET, String.format(URL, page, 9), listener, errorListener);
    }
}
