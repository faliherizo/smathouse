package fr.mbds.openhab.lifi.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.androidquery.AQuery;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.openhab.habdroid.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fr.mbds.openhab.lifi.activity.LoginActivity;
import fr.mbds.openhab.lifi.model.Person;

/**
 * Created by Faliherizo on 20/12/2016.
 */
public class SessionManager extends IntentService {


    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "SessionManager";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String KEY_GCM = "gcm";
    private static String KEY_ID= "id";
    private static String KEY_API= "key_api";
    private static String KEY_PWD="pwd";
    private static String KEY_FIRSTNAME="firstname";
    private static String KEY_SEXE="sexe";
    private static String KEY_USER="user";


    // Constructor
    public SessionManager(final String name, Context context){
        super(name);
        this._context = context;
        pref = _context.getSharedPreferences(name, PRIVATE_MODE);
        editor = pref.edit();
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SessionManager(String name) {
        super(name);
    }
    public SessionManager() {
        super(PREF_NAME);
    }
    /**
     * Create login session
     * */
    public void createLoginSession(Person p){
        // Storing login value as TRUE
        editor.clear();
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_NAME, p.getName());
        // Storing email in pref
        editor.putString(KEY_EMAIL, p.getLogin());

        editor.putString(KEY_ID, p.getId());
        editor.putString(KEY_PWD, p.getPassword());
        Gson gson = new Gson();
        String json = gson.toJson(p);
        editor.putString(KEY_USER, json);
        // commit changes
        editor.commit();
        editor.apply();
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
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

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);

    }

}
