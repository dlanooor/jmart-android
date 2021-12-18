package com.ronaldJmartBO.jmart_android.request;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import java.net.PortUnreachableException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * The Login request.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class LoginRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/account/login";
    private final Map<String, String> params;

    /**
     * Instantiates a new Login request.
     *
     * @param email         the email
     * @param password      the password
     * @param listener      the listener
     * @param errorListener the error listener
     */
    public LoginRequest(String email, String password, Response.Listener<String> listener,
                        Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("email", email.toLowerCase(Locale.ROOT));
        params.put("password", password);
    }

    public Map<String, String> getParams() {
        return params;
    }
}
