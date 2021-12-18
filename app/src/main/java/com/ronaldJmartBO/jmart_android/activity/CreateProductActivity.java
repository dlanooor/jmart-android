package com.ronaldJmartBO.jmart_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ronaldJmartBO.R;
import com.ronaldJmartBO.jmart_android.model.ProductCategory;
import com.ronaldJmartBO.jmart_android.request.CreateProductRequest;
import com.ronaldJmartBO.jmart_android.request.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The type Create product activity.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class CreateProductActivity extends AppCompatActivity {
    /**
     * The Condition used.
     */
    boolean conditionUsed = false;

    /**
     * onCreate
     * set All CreateProductActivity needed
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        this.setTitle("Create Product");

        EditText createProductName = findViewById(R.id.createProductName);
        EditText createProductWeight = findViewById(R.id.createProductWeight);
        EditText createProductPrice = findViewById(R.id.createProductPrice);
        EditText createProductDiscount = findViewById(R.id.createProductDiscount);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Spinner createProductSpinnerCategory = findViewById(R.id.createProductSpinnerCategory);
        Spinner createProductSpinnerPlan = findViewById(R.id.createProductSpinnerPlan);

        /**
         * create ProductActivity Function
         */
        Button productActivityCreateButton = findViewById(R.id.productActivityCreateButton);
        productActivityCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object != null){
                                Toast.makeText(getApplicationContext(), "Create Product Success", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Create Product Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "System Error Occurs..", Toast.LENGTH_SHORT).show();
                    }
                };

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.createProductNew:
                                conditionUsed = false;
                                break;
                            case R.id.createProductUsed :
                                conditionUsed = true;
                                break;
                        }
                    }
                });

                ProductCategory productCategory = ProductCategory.valueOf(createProductSpinnerCategory.getSelectedItem().toString());
                byte productShipment = 1 << 0;

                switch(createProductSpinnerPlan.getSelectedItem().toString()) {
                    case "INSTANT" :
                        productShipment = 1 << 0;
                        break;
                    case "SAME_DAY" :
                        productShipment = 1 << 1;
                        break;
                    case "NEXT DAY" :
                        productShipment = 1 << 2;
                        break;
                    case "REGULER" :
                        productShipment = 1 << 3;
                        break;
                    case "KARGO" :
                        productShipment = 1 << 4;
                        break;
                }

                if(createProductName.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Please Fill In Product Name", Toast.LENGTH_SHORT).show();
                else if(createProductWeight.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Please Fill In Product Weight", Toast.LENGTH_SHORT).show();
                else if(createProductPrice.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Please Fill In Product Price", Toast.LENGTH_SHORT).show();
                else if(createProductDiscount.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Please Fill In Product Discount", Toast.LENGTH_SHORT).show();
                else {
                    String productName = createProductName.getText().toString();
                    int productWeight = Integer.parseInt(createProductWeight.getText().toString());
                    double productPrice = Double.parseDouble(createProductPrice.getText().toString());
                    double productDiscount = Double.parseDouble(createProductDiscount.getText().toString());

                    //createProductRequest
                    CreateProductRequest createProduct = new CreateProductRequest(productName, productWeight, conditionUsed, productPrice, productDiscount, productCategory, productShipment, listener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(CreateProductActivity.this);
                    queue.add(createProduct);

                    createProductName.getText().clear();
                    createProductWeight.getText().clear();
                    createProductPrice.getText().clear();
                    createProductDiscount.getText().clear();
                }
            }
        });

        /**
         * cancel ProductActivity Function
         */
        Button productActivityCancel = findViewById(R.id.productActivityCancel);
        productActivityCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProductName.getText().clear();
                createProductWeight.getText().clear();
                createProductPrice.getText().clear();
                createProductDiscount.getText().clear();
                Intent i = new Intent(CreateProductActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}