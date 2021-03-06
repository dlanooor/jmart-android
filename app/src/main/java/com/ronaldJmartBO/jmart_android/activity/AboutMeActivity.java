package com.ronaldJmartBO.jmart_android.activity;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;
import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.setLoggedAccount;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.gson.Gson;
import com.ronaldJmartBO.jmart_android.request.RegisterStoreRequest;
import com.ronaldJmartBO.jmart_android.request.TopUpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * The About me activity.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class AboutMeActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();

    /**
     * onCreate
     * set All AboutMeActivity needed
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        this.setTitle("About Me");

        Locale myIndonesianLocale = new Locale("in", "ID");
        NumberFormat formater = NumberFormat.getCurrencyInstance(myIndonesianLocale);

        TextView tvNama = (TextView) findViewById(R.id.tvNama);
        TextView tvEmail = (TextView) findViewById(R.id.tvEmail);
        TextView tvBalance = (TextView) findViewById(R.id.tvBalance);

        TextView etName = (TextView) findViewById(R.id.etName);
        TextView etAddress = (TextView) findViewById(R.id.etAddress);
        TextView etPhoneNumber = (TextView) findViewById(R.id.etPhoneNumber);

        TextView tvNameDetail = (TextView) findViewById(R.id.tvStoreNameDetail);
        TextView tvAddressDetail = (TextView) findViewById(R.id.tvStoreAddressDetail);
        TextView tvPhoneNumberDetail = (TextView) findViewById(R.id.tvStorePhoneNumberDetail);
        TextView tvBalanceDetail = (TextView) findViewById(R.id.tvStoreBalanceDetail);

        RequestQueue queue = Volley.newRequestQueue(AboutMeActivity.this);

        String name = getLoggedAccount().name;
        String email = getLoggedAccount().email;
        String balance = String.valueOf(formater.format(getLoggedAccount().balance));

        tvNama.setText(name);
        tvEmail.setText(email);
        tvBalance.setText(balance);

        EditText topUpAmount = findViewById(R.id.etTopUp);

        if(getLoggedAccount().store != null) {
            findViewById(R.id.layoutStoreDetail).setVisibility(View.VISIBLE);
            findViewById(R.id.layoutRegister).setVisibility(View.GONE);
            findViewById(R.id.btnRegisterStore).setVisibility(View.GONE);

            tvNameDetail.setText(getLoggedAccount().store.name);
            tvAddressDetail.setText(getLoggedAccount().store.address);
            tvPhoneNumberDetail.setText(getLoggedAccount().store.phoneNumber);
            tvBalanceDetail.setText(String.valueOf(formater.format(getLoggedAccount().store.balance)));
        }

        else {
            findViewById(R.id.layoutStoreDetail).setVisibility(View.GONE);
            findViewById(R.id.layoutRegister).setVisibility(View.GONE);
            findViewById(R.id.btnRegisterStore).setVisibility(View.VISIBLE);
        }

        /**
         * Top Up Account Balance Function
         */
        final Button topUp = (Button) findViewById(R.id.btnTopUp);
        topUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // topUpBalance
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toLowerCase().equals("true")){
                            Double updateBalance = getLoggedAccount().balance + Double.parseDouble(topUpAmount.getText().toString());
                            tvBalance.setText(String.valueOf(formater.format(updateBalance)));
                            getLoggedAccount().balance = updateBalance;

                            Toast.makeText(getApplicationContext(), "Top Up Success", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Top Up Failed", Toast.LENGTH_SHORT).show();
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AboutMeActivity.this, "System Error Occurs..", Toast.LENGTH_SHORT).show();
                    }
                };

                if(topUpAmount.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Fill In The Top Up Amount", Toast.LENGTH_SHORT).show();
                else {
                    Double amount = Double.valueOf(topUpAmount.getText().toString());

                    if(amount < 10000)
                        Toast.makeText(getApplicationContext(), "Minimum Top Up is 10000", Toast.LENGTH_SHORT).show();
                    else {
                        //topUp Request
                        TopUpRequest newTopUpRequest = new TopUpRequest(amount, listener, errorListener);
                        RequestQueue queue = Volley.newRequestQueue(AboutMeActivity.this);
                        queue.add(newTopUpRequest);
                    }
                }
            }
        });

        /**
         * Register Store Function.
         */
        final Button registerStore = (Button) findViewById(R.id.btnRegisterStore);
        registerStore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(R.id.layoutRegister).setVisibility(View.VISIBLE);
                findViewById(R.id.btnRegisterStore).setVisibility(View.GONE);
            }
        });

        /**
         * Register Store Submit Function
         */
        final Button register = (Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object != null){

                                Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_SHORT).show();
                                findViewById(R.id.layoutStoreDetail).setVisibility(View.VISIBLE);
                                findViewById(R.id.layoutRegister).setVisibility(View.GONE);
                                findViewById(R.id.btnRegisterStore).setVisibility(View.GONE);

                                tvNameDetail.setText(etName.getText().toString());
                                tvAddressDetail.setText(etAddress.getText().toString());
                                tvPhoneNumberDetail.setText(etPhoneNumber.getText().toString());
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "System Error Occurs..", Toast.LENGTH_SHORT).show();
                    }
                };

                String newStoreName = etName.getText().toString();
                String newStoreAddress = etAddress.getText().toString();
                String newStorePhoneNumber = etPhoneNumber.getText().toString();

                //registerNewStore Request
                RegisterStoreRequest newRegisterStore = new RegisterStoreRequest(newStoreName, newStoreAddress, newStorePhoneNumber, listener, errorListener);

                queue.add(newRegisterStore);
            }
        });

        /**
         * Cancel Submit Store Function
         */
        final Button cancelStore = (Button) findViewById(R.id.btnCancel);
        cancelStore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(R.id.layoutStoreDetail).setVisibility(View.GONE);
                findViewById(R.id.layoutRegister).setVisibility(View.GONE);
                findViewById(R.id.btnRegisterStore).setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * onResume
     * set All AboutMeActivity needed onResume
     */
    @Override
    protected void onResume() {
        super.onResume();
        Locale myIndonesianLocale = new Locale("in", "ID");
        NumberFormat formater = NumberFormat.getCurrencyInstance(myIndonesianLocale);

        TextView tvBalance = (TextView) findViewById(R.id.tvBalance);
        tvBalance.setText(String.valueOf(formater.format(getLoggedAccount().balance)));

        if(getLoggedAccount().store != null) {
            TextView tvBalanceDetail = (TextView) findViewById(R.id.tvStoreBalanceDetail);
            tvBalanceDetail.setText(String.valueOf(formater.format(getLoggedAccount().store.balance)));
        }
    }
}