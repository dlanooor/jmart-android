package com.ronaldJmartBO.jmart_android.request;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Register store request.
 */
public class RegisterStoreRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/account/" + getLoggedAccount().id + "/registerStore";
    private final Map<String, String> params;

    /**
     * Instantiates a new Register store request.
     *
     * @param name          the name
     * @param address       the address
     * @param phoneNumber   the phone number
     * @param listener      the listener
     * @param errorListener the error listener
     */
    public RegisterStoreRequest(String name, String address, String phoneNumber, Response.Listener<String> listener,
                                Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
        params.put("address", address);
        params.put("phoneNumber", phoneNumber);
    }

    public Map<String, String> getParams() {
        return params;
    }
}