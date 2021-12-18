package com.ronaldJmartBO.jmart_android.other;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ronaldJmartBO.jmart_android.activity.LoginActivity;
import com.ronaldJmartBO.jmart_android.activity.MainActivity;

/**
 * The Session management.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class SessionManagement {
    /**
     * The Pref.
     */
// Shared Preferences
    SharedPreferences pref;

    /**
     * The Editor.
     */
// Editor for Shared preferences
    Editor editor;

    /**
     * The Context.
     */
// Context
    Context _context;

    /**
     * The Private mode.
     */
// Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidRonald";

    // All Shared Preferences Keys

    /**
     * The constant KEY_LOGIN.
     */
    public static final String KEY_LOGIN = "false";

    /**
     * The constant KEY_ID.
     */
// User name (make variable public to access from outside)
    public static final String KEY_ID = "id";

    /**
     * Instantiates a new Session management.
     *
     * @param context the context
     */
// Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     *
     * @param id the id
     */
    public void createLoginSession(String id){
        // Storing login value as TRUE
        editor.putString(KEY_LOGIN, "true");

        // Storing name in pref
        editor.putString(KEY_ID, id);

        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     *
     * @return the hash map
     */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user id
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        // user login
        user.put(KEY_LOGIN, pref.getString(KEY_LOGIN, null));
        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }
}