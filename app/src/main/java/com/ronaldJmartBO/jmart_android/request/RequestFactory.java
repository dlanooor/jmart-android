package com.ronaldJmartBO.jmart_android.request;

import com.android.volley.*;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.net.URL;

/**
 * The Request factory.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class RequestFactory
{
    private static final String URL_FORMAT_ID = "http://10.0.2.2:8080/%s/%d";
    private static final String URL_FORMAT_PAGE = "http://10.0.2.2:8080/%s/page?page=%s&pageSize=%s";

    /**
     * Gets by id.
     *
     * @param parentURI     the parent uri
     * @param id            the id
     * @param listener      the listener
     * @param errorListener the error listener
     * @return the by id
     */
    public static StringRequest getById
        (
                String parentURI,
                int id,
                Response.Listener<String> listener,
                Response.ErrorListener errorListener
        )
    {
        String url = String.format(URL_FORMAT_ID, parentURI, id);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }

    /**
     * Gets page.
     *
     * @param parentURI     the parent uri
     * @param page          the page
     * @param pageSize      the page size
     * @param listener      the listener
     * @param errorListener the error listener
     * @return the page
     */
    public static StringRequest getPage
        (
                String parentURI,
                int page,
                int pageSize,
                Response.Listener<String> listener,
                Response.ErrorListener errorListener
        )
    {
        String url = String.format(URL_FORMAT_PAGE, parentURI, page, pageSize);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }

    /**
     * Gets url format id.
     *
     * @return the url format id
     */
    public static String getUrlFormatId() {
        return URL_FORMAT_ID;
    }
}