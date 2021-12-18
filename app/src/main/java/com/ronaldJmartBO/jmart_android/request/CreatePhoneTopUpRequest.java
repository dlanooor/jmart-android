package com.ronaldJmartBO.jmart_android.request;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * The Create phone top up request.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class CreatePhoneTopUpRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/phonetopup/create";
    private final Map<String, String> params;

    /**
     * Instantiates a new Create phone top up request.
     *
     * @param phoneNumber   the phone number
     * @param listener      the listener
     * @param errorListener the error listener
     */
    public CreatePhoneTopUpRequest(String phoneNumber, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("accountId", String.valueOf(getLoggedAccount().id));
        params.put("phoneNumber", phoneNumber);
    }

    public Map<String, String> getParams() {
        return params;
    }
}
