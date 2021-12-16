package com.ronaldJmartBO.jmart_android.activity;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ronaldJmartBO.R;
import com.ronaldJmartBO.jmart_android.request.CreatePhoneTopUpRequest;
import com.ronaldJmartBO.jmart_android.request.PaymentRequest;
import com.ronaldJmartBO.jmart_android.model.PhoneTopUp;
import com.ronaldJmartBO.jmart_android.request.PhoneTopUpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class PhoneTopUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_top_up);

        Locale myIndonesianLocale = new Locale("in", "ID");
        java.text.NumberFormat formater = NumberFormat.getCurrencyInstance(myIndonesianLocale);

        TextView name = (TextView) findViewById(R.id.tvPhoneTopUpName);
        name.setText(getLoggedAccount().name);

        TextView balance = (TextView) findViewById(R.id.tvPhoneTopUpBalance);
        balance.setText(String.valueOf(formater.format(getLoggedAccount().balance)));

        EditText phoneNumber = (EditText) findViewById(R.id.etPhoneTopUpPhone);

        Button checkPhone = (Button) findViewById(R.id.btnPhoneTopUpCheck);
        checkPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Pattern.matches("^(^\\+62|62|^08)(\\d{3,4}-?){2}\\d{3,4}$", phoneNumber.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Phone Number Valid", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Phone Number Not Valid", Toast.LENGTH_SHORT).show();
            }
        });

        Button topUp = (Button) findViewById(R.id.btnPhoneTopUpTopUp);
        topUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object != null){
                                Toast.makeText(getApplicationContext(), "Phone Top Up Success", Toast.LENGTH_SHORT).show();
                                Intent j = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(j);
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Phone Top Up Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "System Error Occurs..", Toast.LENGTH_SHORT).show();
                    }
                };

                if(Pattern.matches("^(^\\+62|62|^08)(\\d{3,4}-?){2}\\d{3,4}$", phoneNumber.getText().toString())) {
                    CreatePhoneTopUpRequest topUpRequest = new CreatePhoneTopUpRequest(phoneNumber.getText().toString(), listener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(PhoneTopUpActivity.this);
                    queue.add(topUpRequest);
                }
                else
                    Toast.makeText(getApplicationContext(), "Phone Number Not Valid", Toast.LENGTH_SHORT).show();

            }
        });

        ListView listItems = (ListView) findViewById(R.id.lvPhoneTopUpHistory);
        Gson gson = new Gson();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<String> phoneTopUpReturned = new ArrayList<>();

                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject newObj = jsonArray.getJSONObject(i);
                        PhoneTopUp phoneTopUp = gson.fromJson(newObj.toString(), PhoneTopUp.class);
                        phoneTopUpReturned.add(phoneTopUp.phoneNumber + " - " + phoneTopUp.status);
                    }
                    ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, phoneTopUpReturned);
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
                Toast.makeText(getApplicationContext(), "System Error Occurs..", Toast.LENGTH_SHORT).show();
            }
        };

        PhoneTopUpRequest phoneTopUp = new PhoneTopUpRequest(0, listener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(phoneTopUp);
    }
}