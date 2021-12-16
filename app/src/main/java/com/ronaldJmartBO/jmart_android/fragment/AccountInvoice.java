package com.ronaldJmartBO.jmart_android.fragment;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;
import static com.ronaldJmartBO.jmart_android.fragment.Products.productShare;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ronaldJmartBO.R;
import com.ronaldJmartBO.jmart_android.activity.AccountInvoiceDetail;
import com.ronaldJmartBO.jmart_android.model.Payment;
import com.ronaldJmartBO.jmart_android.model.Product;
import com.ronaldJmartBO.jmart_android.request.PaymentRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountInvoice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountInvoice extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    /**
     * The Payment intent.
     */
    List<Payment> paymentIntent = new ArrayList<>();

    /**
     * Instantiates a new Account invoice.
     */
    public AccountInvoice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AccountInvoice.
     */
    public static AccountInvoice newInstance(String param1) {
        AccountInvoice fragment = new AccountInvoice();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_invoice, container, false);
        ListView listItems = view.findViewById(R.id.lvAccInvoice);
        Gson gson = new Gson();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<String> paymentReturned = new ArrayList<>();

                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject newObj = jsonArray.getJSONObject(i);
                        Payment payment = gson.fromJson(newObj.toString(), Payment.class);
                        paymentIntent.add(payment);

                        for(Product product : productShare) {
                            if(product.id == payment.productId)
                                paymentReturned.add(product.name);
                        }

                    }
                    ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, paymentReturned);
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

        PaymentRequest paymentRequest = new PaymentRequest(0, listener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
        queue.add(paymentRequest);

        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //intentObject
                Intent i = new Intent(getActivity().getBaseContext(), AccountInvoiceDetail.class);
                
                //packData
                i.putExtra("SENDER_KEY", "AccountInvoiceFragment");
                i.putExtra("BUYERID_KEY", String.valueOf(paymentIntent.get(position).buyerId));
                i.putExtra("PRODID_KEY", String.valueOf(paymentIntent.get(position).productId));
                i.putExtra("ADDRESS_KEY", String.valueOf(paymentIntent.get(position).shipment.address));
                i.putExtra("COST_KEY", String.valueOf(paymentIntent.get(position).shipment.cost));

                if(paymentIntent.get(position).shipment.plan == 1)
                    i.putExtra("PLAN_KEY", "INSTANT");
                else if(paymentIntent.get(position).shipment.plan == 2)
                    i.putExtra("PLAN_KEY", "SAME DAY");
                else if(paymentIntent.get(position).shipment.plan == 4)
                    i.putExtra("PLAN_KEY", "NEXT DAY");
                else if(paymentIntent.get(position).shipment.plan == 8)
                    i.putExtra("PLAN_KEY", "REGULER");
                else if(paymentIntent.get(position).shipment.plan == 16)
                    i.putExtra("PLAN_KEY", "KARGO");

                i.putExtra("RECEIPT_KEY", String.valueOf(paymentIntent.get(position).shipment.receipt));
                i.putExtra("PAYMENT_ID", String.valueOf(paymentIntent.get(position).id));
                i.putExtra("HISTORY_SIZE", String.valueOf(paymentIntent.get(position).history.size()));
                i.putExtra("ACCOUNT_STORE", "ACCOUNT");
                for(int j = 0; j < paymentIntent.get(position).history.size(); j++) {
                    i.putExtra("HISTORY_KEY" + String.valueOf(j), String.valueOf(paymentIntent.get(position).history.get(j).status));
                }

                //startActivity
                getActivity().startActivity(i);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}