package com.ronaldJmartBO.jmart_android.request;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * The Top up request.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class TopUpRequest extends StringRequest{
    private final static String URL = "http://10.0.2.2:8080/account/" + getLoggedAccount().id + "/topUp";
    private final Map<String, String> params;

    /**
     * Instantiates a new Top up request.
     *
     * @param amount        the amount
     * @param listener      the listener
     * @param errorListener the error listener
     */
    public TopUpRequest(Double amount, Response.Listener<String> listener,
                        Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("balance", String.valueOf(amount));
    }

    public Map<String, String> getParams() {
        return params;
    }
}
