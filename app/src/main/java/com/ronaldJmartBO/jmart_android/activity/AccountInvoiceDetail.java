package com.ronaldJmartBO.jmart_android.activity;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.ronaldJmartBO.jmart_android.request.CancelPaymentRequest;
import com.ronaldJmartBO.jmart_android.request.TopUpRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AccountInvoiceDetail extends AppCompatActivity {

    private TextView tvAccountInvoiceDetailBuyerId, tvAccountInvoiceDetailProductId, tvAccountInvoiceDetailProductAddress, tvAccountInvoiceDetailShipmentCost, tvAccountInvoiceDetailShipmentPlan, tvAccountInvoiceDetailShipmentReceipt;
    public static int paymentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_invoice_detail);
        this.setTitle("Account Invoice Detail");

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
        tvAccountInvoiceDetailShipmentCost.setText(cost);
        tvAccountInvoiceDetailShipmentPlan.setText(plan);
        tvAccountInvoiceDetailShipmentReceipt.setText(receipt);

        Button cancelButton = (Button) findViewById(R.id.btnCancelAccountInvoice);

        if(historyReturned.get(historySize - 1).equals("WAITING_CONFIRMATION"))
            cancelButton.setVisibility(View.VISIBLE);

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

                CancelPaymentRequest cancelPaymentRequest = new CancelPaymentRequest(listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(AccountInvoiceDetail.this);
                queue.add(cancelPaymentRequest);

            };
        });
    }
}