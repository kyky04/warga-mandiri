package co.id.wargamandiri.utils;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import co.id.wargamandiri.activities.LoginActivity;
import co.id.wargamandiri.activities.MainActivity;

/**
 * Created by Comp on 7/29/2017.
 */

public class Session {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "online-shop";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoged";

    // User ID (make variable public to access from outside)
    public static final String KEY_ID = "id";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "user.name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_COUNT_PEGAWAI = "pegawai_count";
    public static final String KEY_COUNT_PROYEK = "proyek_count";


    // Constructor
    public Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String userName, String email, String access_token, String credential, String nip, Integer id) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NAME, userName);

        editor.putString(KEY_EMAIL, email);

        editor.putString(KEY_ACCESS_TOKEN, access_token);

        editor.putInt(KEY_ID, id);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (isLoggedIn()) {
            // user is not logged in redirect him to Login Aktifitas
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Aktifitas
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Aktifitas
            _context.startActivity(i);

        }

    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        editor.putBoolean(IS_LOGIN, false);
        // After logout redirect user to Loing Aktifitas
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Aktifitas
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Aktifitas
        _context.startActivity(i);
    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        return user;
    }

    public String getFullname() {
        return pref.getString(KEY_NAME, null);
    }

    public void setFullName(String v) {
        editor.putString(KEY_NAME, v);
        editor.commit();
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, null);
    }

    public void setEmail(String v) {
        editor.putString(KEY_EMAIL, v);
        editor.commit();
    }

    public void setKeyApiKey(String v) {
        editor.putString(KEY_ACCESS_TOKEN, v);
        editor.commit();
    }

    public void setId(int id) {
        editor.putInt(KEY_ID, id);
        editor.commit();
    }

    public void setProyekCount(int id) {
        editor.putInt(KEY_COUNT_PROYEK, id);
        editor.commit();
    }

    public void setPegawaiCount(int id) {
        editor.putInt(KEY_COUNT_PEGAWAI, id);
        editor.commit();
    }

    public String getAccessToken() {
        return pref.getString(KEY_ACCESS_TOKEN, null);
    }


    public String getUserName() {
        return pref.getString(KEY_NAME, null);
    }

    public Integer getID() {
        return pref.getInt(KEY_ID, 0);
    }

    public Integer getProyekCount() {
        return pref.getInt(KEY_COUNT_PROYEK, 0);
    }

    public Integer getPegawaiCount() {
        return pref.getInt(KEY_COUNT_PEGAWAI, 0);
    }


    public int getIntID() {
        return Integer.parseInt(pref.getString(KEY_ID, "0"));
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


    public void setIsLogin(Boolean v) {
        editor.putBoolean(IS_LOGIN, v);
        editor.commit();
    }


}
