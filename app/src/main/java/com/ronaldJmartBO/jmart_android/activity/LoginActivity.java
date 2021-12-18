package com.ronaldJmartBO.jmart_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.gson.Gson;
import com.ronaldJmartBO.R;
import com.ronaldJmartBO.jmart_android.model.Account;
import com.ronaldJmartBO.jmart_android.request.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The type Login activity.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class LoginActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Account loggedAccount = null;

    /**
     * Gets logged account.
     *
     * @return the logged account
     */
    public static Account getLoggedAccount() {
        return loggedAccount;
    }

    /**
     * Sets logged account.
     *
     * @param account the account
     */
    public static void setLoggedAccount(Account account) {
        loggedAccount = account;
    }

    /**
     * onCreate
     * set All LoginActivity needed
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Login");

        EditText editEmail = findViewById(R.id.loginEmailAddress);
        EditText editPassword = findViewById(R.id.loginPassword);
        Button loginButton = findViewById(R.id.loginButton);
        TextView registerLogin = findViewById(R.id.registerLoginButton);

        /**
         * login Account Function
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginRequest
                LoginRequest newLogin = new LoginRequest(
                    editEmail.getText().toString(),
                    editPassword.getText().toString(),
                    new Response.Listener<String>() {
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject != null) {
                                    Toast.makeText(getApplicationContext(), "Login Success.", Toast.LENGTH_SHORT).show();
                                    loggedAccount = gson.fromJson(jsonObject.toString(), Account.class);
                                    Intent loginSuccess = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(loginSuccess);
                                }
                            } catch (JSONException e) {
                                Toast.makeText(LoginActivity.this, "Login Failed.", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "System Error Occurs..", Toast.LENGTH_SHORT).show();
                        }
                    }
                );
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(newLogin);
            }
        });

        /**
         * register from LoginAcitvity Function
         */
        registerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerPage = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerPage);
            }
        });
    }
}