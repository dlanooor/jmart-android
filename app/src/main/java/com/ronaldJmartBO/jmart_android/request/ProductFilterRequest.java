package com.ronaldJmartBO.jmart_android.request;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.ronaldJmartBO.jmart_android.model.ProductCategory;

import java.util.HashMap;
import java.util.Map;

/**
 * The Product filter request.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class ProductFilterRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/product/getFiltered?page=%d&pageSize=%d&accountId=%d&search=%s&minPrice=%d&maxPrice=%d&category=%s";

    /**
     * Instantiates a new Product filter request.
     *
     * @param page          the page
     * @param accountId     the account id
     * @param search        the search
     * @param minPrice      the min price
     * @param maxPrice      the max price
     * @param category      the category
     * @param listener      the listener
     * @param errorListener the error listener
     */
    public ProductFilterRequest(int page, int accountId, String search, int minPrice, int maxPrice,
                                     ProductCategory category, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, String.format(URL, page, 9, accountId, search, minPrice, maxPrice, String.valueOf(category)), listener, errorListener);
    }
}
