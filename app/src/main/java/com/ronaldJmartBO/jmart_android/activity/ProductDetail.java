package com.ronaldJmartBO.jmart_android.activity;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ronaldJmartBO.R;
import com.ronaldJmartBO.jmart_android.request.CreatePaymentRequest;
import com.ronaldJmartBO.jmart_android.request.CreateProductRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * The type Product detail.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class ProductDetail extends AppCompatActivity {

    private TextView prodDetailName, prodDetailWeight, prodDetailCondition, prodDetailCategory, prodDetailPrice, prodDetailDiscount, prodDetailShipment;

    /**
     * onCreate
     * set All ProductDetail needed
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        this.setTitle("Product Detail");

        Locale myIndonesianLocale = new Locale("in", "ID");
        NumberFormat formater = NumberFormat.getCurrencyInstance(myIndonesianLocale);

        prodDetailName = (TextView) findViewById(R.id.tvProductDetailName);
        prodDetailWeight = (TextView) findViewById(R.id.tvProductDetailWeight);
        prodDetailCondition = (TextView) findViewById(R.id.tvProductDetailCondition);
        prodDetailCategory = (TextView) findViewById(R.id.tvProductDetailCategory);
        prodDetailPrice = (TextView) findViewById(R.id.tvProductDetailPrice);
        prodDetailDiscount = (TextView) findViewById(R.id.tvProductDetailDiscount);
        prodDetailShipment = (TextView) findViewById(R.id.tvProductDetailShipment);

        final String sender = this.getIntent().getExtras().getString("SENDER_KEY");
        Intent i = getIntent();
        String prodId = i.getStringExtra("PRODID_KEY");
        int accId = Integer.parseInt(i.getStringExtra("ACCID_KEY"));
        String name = i.getStringExtra("NAME_KEY");
        String weight = i.getStringExtra("WEIGHT_KEY");
        String condition = i.getStringExtra("CONDITION_KEY");
        String category = i.getStringExtra("CATEGORY_KEY");
        String price = i.getStringExtra("PRICE_KEY");
        String discount = i.getStringExtra("DISCOUNT_KEY");
        String shipment = i.getStringExtra("SHIPMENT_KEY");

        prodDetailName.setText(name);
        prodDetailWeight.setText(weight + " kg");
        if(condition.equals("true")) {
            prodDetailCondition.setText("Used");
            prodDetailCondition.setTextColor(Color.parseColor("#950101"));
        }
        else {
            prodDetailCondition.setText("New");
            prodDetailCondition.setTextColor(Color.parseColor("#064635"));
        }

        prodDetailCategory.setText(category);
        prodDetailPrice.setText(String.valueOf(formater.format(Double.parseDouble(price))));
        prodDetailDiscount.setText(discount + "%");
        if(shipment.equals("1"))
            prodDetailShipment.setText("INSTANT");
        else if(shipment.equals("2"))
            prodDetailShipment.setText("SAME DAY");
        else if(shipment.equals("4"))
            prodDetailShipment.setText("NEXT DAY");
        else if(shipment.equals("8"))
            prodDetailShipment.setText("REGULER");
        else if(shipment.equals("16"))
            prodDetailShipment.setText("KARGO");


        EditText prodCount = (EditText) findViewById(R.id.btnAmountProdDetail);

        /**
         * prevButton Function
         */
        Button prevButton = (Button) findViewById(R.id.btnMinusProdDetail);
        prevButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!prodCount.getText().toString().isEmpty()){
                    int page = Integer.parseInt(prodCount.getText().toString());
                    if(page <= 1) {
                        Toast.makeText(getApplicationContext(), "Minimum Amount is 1", Toast.LENGTH_SHORT).show();
                        prodCount.setText("1");
                    }
                    else {
                        prodCount.setText(String.valueOf(page - 1));
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "No Amount Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * nextButton Function
         */
        Button nextButton = (Button) findViewById(R.id.btnPlusProdDetail);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!prodCount.getText().toString().isEmpty()){
                    int page = Integer.parseInt(prodCount.getText().toString());
                    prodCount.setText(String.valueOf(page + 1));
                }
                else {
                    Toast.makeText(getApplicationContext(), "No Amount Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        EditText etProductDetailAddress = (EditText) findViewById(R.id.etProductDetailAddress);

        /**
         * confirmButton Function
         */
        Button confirmButton = (Button) findViewById(R.id.btnConfirmProdDetail);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object != null){
                                Toast.makeText(getApplicationContext(), "Product Payment Success", Toast.LENGTH_SHORT).show();
                                Intent j = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(j);
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Product Payment Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "System Error Occurs..", Toast.LENGTH_SHORT).show();
                    }
                };

                //createPaymentRequest
                CreatePaymentRequest paymentRequest = new CreatePaymentRequest(Integer.parseInt(prodId), Integer.parseInt(prodCount.getText().toString()), etProductDetailAddress.getText().toString(), Byte.parseByte(shipment), listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(ProductDetail.this);
                queue.add(paymentRequest);
            }
        });

        /**
         * cancelProduct Function
         */
        Button cancelProdDetail = (Button) findViewById(R.id.btnCancelProdDetail);
        cancelProdDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(j);
            }
        });

        /**
         * buyProduct Function
         */
        Button buyButton = (Button) findViewById(R.id.btnBuyProdDetail);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getLoggedAccount().balance < (Integer.parseInt(prodCount.getText().toString()) * (Double.parseDouble(price) - Double.parseDouble(price) * (Double.parseDouble (discount) / 100))))
                    Toast.makeText(getApplicationContext(), "Balance Is Insufficient", Toast.LENGTH_SHORT).show();
                else {
                    buyButton.setVisibility(View.GONE);
                    confirmButton.setVisibility(View.VISIBLE);

                    CardView cvProdDetail = (CardView) findViewById(R.id.cvProdDetail);
                    cvProdDetail.setVisibility(View.VISIBLE);
                }
            }
        });

        if(accId == getLoggedAccount().id) {
            prodCount.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
            prevButton.setVisibility(View.GONE);
            cancelProdDetail.setVisibility(View.GONE);
            buyButton.setVisibility(View.GONE);
        }
    }
}