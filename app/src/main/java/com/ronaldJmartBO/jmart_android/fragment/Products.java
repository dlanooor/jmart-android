package com.ronaldJmartBO.jmart_android.fragment;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;
import static com.ronaldJmartBO.jmart_android.fragment.Filter.*;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ronaldJmartBO.R;

import com.ronaldJmartBO.jmart_android.activity.ProductDetail;
import com.ronaldJmartBO.jmart_android.model.Product;
import com.ronaldJmartBO.jmart_android.model.ProductCategory;
import com.ronaldJmartBO.jmart_android.request.ProductFilterRequest;
import com.ronaldJmartBO.jmart_android.request.ProductRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Products#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Products extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    /**
     * The Product intent.
     */
    List<Product> productIntent = new ArrayList<>();

    /**
     * The constant productShare.
     */
    public static List<Product> productShare = new ArrayList<>();

    private String mParam1;
    private String mParam2;

    /**
     * The Page number.
     */
    EditText pageNumber;
    /**
     * The Prev button.
     */
    Button prevButton, /**
     * The Next button.
     */
    nextButton, /**
     * The Go button.
     */
    goButton;

    /**
     * Instantiates a new Products.
     */
    public Products() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Products.
     */
    public static Products newInstance(String param1) {
        Products fragment = new Products();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        ListView listItems = view.findViewById(R.id.listView);

        Gson gson = new Gson();
        productShare.clear();

        pageNumber = (EditText) view.findViewById(R.id.pageNumber);

        prevButton = (Button) view.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!pageNumber.getText().toString().isEmpty()){
                    int page = Integer.parseInt(pageNumber.getText().toString());
                    if(page <= 1) {
                        Toast.makeText(getContext(), "Already In Least Page!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        pageNumber.setText(String.valueOf(page - 1));
                    }
                }
                else {
                    Toast.makeText(getContext(), "No Page Number Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button nextButton = (Button) view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!pageNumber.getText().toString().isEmpty()){
                    int page = Integer.parseInt(pageNumber.getText().toString());
                    pageNumber.setText(String.valueOf(page + 1));
                }
                else {
                    Toast.makeText(getContext(), "No Page Number Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button goButton = (Button) view.findViewById(R.id.goButton);
        goButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // if isFiltered from Filter Fragment Return False
                if(!isFiltered) {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                List<String> productReturned = new ArrayList<>();

                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject newObj = jsonArray.getJSONObject(i);
                                    Product product = gson.fromJson(newObj.toString(), Product.class);
                                    productReturned.add(product.name);
                                    productShare.add(product);
                                    productIntent.add(product);
                                }

                                ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, productReturned);
                                listItems.setAdapter(allItemsAdapter);
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "System Error Occurs..", Toast.LENGTH_SHORT).show();
                        }
                    };

                    if(!pageNumber.getText().toString().isEmpty()) {
                        int page = Integer.parseInt(pageNumber.getText().toString()) - 1;
                        ProductRequest productRequest = new ProductRequest(page, listener, errorListener);
                        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
                        queue.add(productRequest);
                    }
                    else
                        Toast.makeText(getContext(), "No Page Number Input", Toast.LENGTH_SHORT).show();
                }

                // if isFiltered from Filter Fragment Return True
                else {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                List<String> productReturned = new ArrayList<>();

                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject newObj = jsonArray.getJSONObject(i);
                                    Product product = gson.fromJson(newObj.toString(), Product.class);
                                    productReturned.add(product.name);
                                    productShare.add(product);
                                    productIntent.add(product);
                                }

                                ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, productReturned);
                                listItems.setAdapter(allItemsAdapter);
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "System Error Occurs..", Toast.LENGTH_SHORT).show();
                        }
                    };

                    if(!pageNumber.getText().toString().isEmpty()) {
                        int page = Integer.parseInt(pageNumber.getText().toString()) - 1;
                        String searchName = nameFilter;
                        int minPrice = lowestPriceFilter;
                        int maxPrice = highestPriceFilter;
                        ProductCategory category = categoryFilter;

                        ProductFilterRequest productFilteredRequest = new ProductFilterRequest(page, getLoggedAccount().id, searchName, minPrice, maxPrice, category, listener, errorListener);
                        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
                        queue.add(productFilteredRequest);
                    }
                    else
                        Toast.makeText(getContext(), "No Page Number Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //intentObject
                Intent i = new Intent(getActivity().getBaseContext(), ProductDetail.class);

                int page = (Integer.parseInt(pageNumber.getText().toString()) - 1) * 9;

                //packData
                i.putExtra("SENDER_KEY", "ProductFragment");
                i.putExtra("PRODID_KEY", String.valueOf(productIntent.get(page + position).id));
                i.putExtra("ACCID_KEY", String.valueOf(productIntent.get(page + position).accountId));
                i.putExtra("NAME_KEY", String.valueOf(productIntent.get(page + position).name));
                i.putExtra("WEIGHT_KEY", String.valueOf(productIntent.get(page + position).weight));
                i.putExtra("CONDITION_KEY", String.valueOf(productIntent.get(page + position).conditionUsed));
                i.putExtra("CATEGORY_KEY", String.valueOf(productIntent.get(page + position).category));
                i.putExtra("PRICE_KEY", String.valueOf(productIntent.get(page + position).price));
                i.putExtra("DISCOUNT_KEY", String.valueOf(productIntent.get(page + position).discount));
                i.putExtra("SHIPMENT_KEY", String.valueOf(productIntent.get(page + position).shipmentPlans));

                //startActivity
                getActivity().startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ListView listItems = getView().findViewById(R.id.listView);

        Gson gson = new Gson();

        productShare.clear();

        // if isFiltered from Filter Fragment Return False
        if (!isFiltered) {
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        List<String> productReturned = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject newObj = jsonArray.getJSONObject(i);
                            Product product = gson.fromJson(newObj.toString(), Product.class);
                            productReturned.add(product.name);
                            productShare.add(product);
                            productIntent.add(product);
                        }

                        ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, productReturned);
                        listItems.setAdapter(allItemsAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "System Error Occurs..", Toast.LENGTH_SHORT).show();
                }
            };

            if (!pageNumber.getText().toString().isEmpty()) {
                int page = Integer.parseInt(pageNumber.getText().toString()) - 1;
                ProductRequest productRequest = new ProductRequest(page, listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
                queue.add(productRequest);
            } else
                Toast.makeText(getContext(), "No Page Number Input", Toast.LENGTH_SHORT).show();
        }

        // if isFiltered from Filter Fragment Return True
        else {
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        List<String> productReturned = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject newObj = jsonArray.getJSONObject(i);
                            Product product = gson.fromJson(newObj.toString(), Product.class);
                            productReturned.add(product.name);
                            productShare.add(product);
                            productIntent.add(product);
                        }

                        ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, productReturned);
                        listItems.setAdapter(allItemsAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "System Error Occurs..", Toast.LENGTH_SHORT).show();
                }
            };

            if (!pageNumber.getText().toString().isEmpty()) {
                int page = Integer.parseInt(pageNumber.getText().toString()) - 1;
                String searchName = nameFilter;
                int minPrice = lowestPriceFilter;
                int maxPrice = highestPriceFilter;
                ProductCategory category = categoryFilter;

                ProductFilterRequest productFilteredRequest = new ProductFilterRequest(page, getLoggedAccount().id, searchName, minPrice, maxPrice, category, listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
                queue.add(productFilteredRequest);
            } else
                Toast.makeText(getContext(), "No Page Number Input", Toast.LENGTH_SHORT).show();
        }
    }
}