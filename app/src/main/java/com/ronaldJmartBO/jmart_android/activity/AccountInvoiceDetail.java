package com.ronaldJmartBO.jmart_android.activity;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ronaldJmartBO.R;
import com.ronaldJmartBO.jmart_android.model.Payment;
import com.ronaldJmartBO.jmart_android.model.Product;
import com.ronaldJmartBO.jmart_android.request.AcceptPaymentRequest;
import com.ronaldJmartBO.jmart_android.request.CancelPaymentRequest;
import com.ronaldJmartBO.jmart_android.request.PaymentSubmitRequest;
import com.ronaldJmartBO.jmart_android.request.TopUpRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The Account invoice detail.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class AccountInvoiceDetail extends AppCompatActivity {

    private TextView tvAccountInvoiceDetailBuyerId, tvAccountInvoiceDetailProductId, tvAccountInvoiceDetailProductAddress, tvAccountInvoiceDetailShipmentCost, tvAccountInvoiceDetailShipmentPlan, tvAccountInvoiceDetailShipmentReceipt;
    /**
     * The constant paymentId.
     */
    public static int paymentId;

    /**
     * onCreate
     * set All AccountInvoiceDetail needed
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_invoice_detail);
        this.setTitle("Invoice Detail");

        Locale myIndonesianLocale = new Locale("in", "ID");
        NumberFormat formater = NumberFormat.getCurrencyInstance(myIndonesianLocale);

        ListView listItems = (ListView) findViewById(R.id.lvAccountInvoiceHistory);

        final String sender = this.getIntent().getExtras().getString("SENDER_KEY");
        Intent i = getIntent();
        String buyerId = i.getStringExtra("BUYERID_KEY");
        String prodId = i.getStringExtra("PRODID_KEY");
        String address = i.getStringExtra("ADDRESS_KEY");
        String cost = i.getStringExtra("COST_KEY");
        String plan = i.getStringExtra("PLAN_KEY");
        String receipt = i.getStringExtra("RECEIPT_KEY");
        String history = i.getStringExtra("HISTORY_KEY");
        String accStore = i.getStringExtra("ACCOUNT_STORE");

        paymentId = Integer.parseInt(i.getStringExtra("PAYMENT_ID"));

        Integer historySize = Integer.parseInt(i.getStringExtra("HISTORY_SIZE"));

        List<String> historyReturned = new ArrayList<>();

        for(int j = 0; j < historySize; j++) {
            historyReturned.add(i.getStringExtra("HISTORY_KEY" + String.valueOf(j)));
        }

        ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<>(AccountInvoiceDetail.this, android.R.layout.simple_list_item_1, historyReturned);
        listItems.setAdapter(allItemsAdapter);

        tvAccountInvoiceDetailBuyerId = (TextView) findViewById(R.id.tvAccountInvoiceDetailBuyerId);
        tvAccountInvoiceDetailProductId = (TextView) findViewById(R.id.tvAccountInvoiceDetailProductId);
        tvAccountInvoiceDetailProductAddress = (TextView) findViewById(R.id.tvAccountInvoiceDetailProductAddress);
        tvAccountInvoiceDetailShipmentCost = (TextView) findViewById(R.id.tvAccountInvoiceDetailShipmentCost);
        tvAccountInvoiceDetailShipmentPlan = (TextView) findViewById(R.id.tvAccountInvoiceDetailShipmentPlan);
        tvAccountInvoiceDetailShipmentReceipt = (TextView) findViewById(R.id.tvAccountInvoiceDetailShipmentReceipt);

        tvAccountInvoiceDetailBuyerId.setText(buyerId);
        tvAccountInvoiceDetailProductId.setText(prodId);
        tvAccountInvoiceDetailProductAddress.setText(address);
        tvAccountInvoiceDetailShipmentCost.setText(String.valueOf(formater.format(Double.parseDouble(cost))));
        tvAccountInvoiceDetailShipmentPlan.setText(plan);
        tvAccountInvoiceDetailShipmentReceipt.setText(receipt);

        /**
         * cancel AccountInvoice Detail Function
         */
        Button cancelButton = (Button) findViewById(R.id.btnCancelAccountInvoice);

        if(!historyReturned.get(historySize - 1).equals("WAITING_CONFIRMATION"))
            cancelButton.setVisibility(View.GONE);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toLowerCase().equals("true")) {
                            Toast.makeText(getApplicationContext(), "Cancel Success", Toast.LENGTH_SHORT).show();
                            Intent j = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(j);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Cancel Failed", Toast.LENGTH_SHORT).show();
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AccountInvoiceDetail.this, "System Error Occurs..", Toast.LENGTH_SHORT).show();
                    }
                };

                //cancelPaymentRequest
                CancelPaymentRequest cancelPaymentRequest = new CancelPaymentRequest(listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(AccountInvoiceDetail.this);
                queue.add(cancelPaymentRequest);

            };
        });

        /**
         * accept Payment in Account Invoice Detail Function
         */
        if(accStore.equals("STORE") && historyReturned.get(historySize - 1).equals("WAITING_CONFIRMATION")) {
            Button acceptButton = (Button) findViewById(R.id.btnAcceptAccountInvoice);
            acceptButton.setVisibility(View.VISIBLE);
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.toLowerCase().equals("true")) {
                                Toast.makeText(getApplicationContext(), "Accept Success", Toast.LENGTH_SHORT).show();
                                Intent j = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(j);
                            }
                            else
                                Toast.makeText(getApplicationContext(), "Accept Failed", Toast.LENGTH_SHORT).show();
                        }
                    };

                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AccountInvoiceDetail.this, "System Error Occurs..", Toast.LENGTH_SHORT).show();
                        }
                    };

                    //acceptPaymentRequest
                    AcceptPaymentRequest acceptPaymentRequest = new AcceptPaymentRequest(listener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(AccountInvoiceDetail.this);
                    queue.add(acceptPaymentRequest);
                }
            });
        }

        /**
         * submit Payment in Account Invoice Detail Function
         */
        else if(accStore.equals("STORE") && historyReturned.get(historySize - 1).equals("ON_PROGRESS")) {
            Button submitButton = (Button) findViewById(R.id.btnSubmitAccountInvoice);
            submitButton.setVisibility(View.VISIBLE);
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.toLowerCase().equals("true")) {
                                Toast.makeText(getApplicationContext(), "Submit Success", Toast.LENGTH_SHORT).show();
                                Intent j = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(j);
                            }
                            else
                                Toast.makeText(getApplicationContext(), "Submit Failed", Toast.LENGTH_SHORT).show();
                        }
                    };

                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AccountInvoiceDetail.this, "System Error Occurs..", Toast.LENGTH_SHORT).show();
                        }
                    };

                    //submitPaymentRequest
                    PaymentSubmitRequest submitPaymentRequest = new PaymentSubmitRequest(listener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(AccountInvoiceDetail.this);
                    queue.add(submitPaymentRequest);
                }
            });
        }

    }
}