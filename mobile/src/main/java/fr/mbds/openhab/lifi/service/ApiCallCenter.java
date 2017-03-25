package fr.mbds.openhab.lifi.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Faliherizo on 08/03/2017.
 */

public class ApiCallCenter {
    private static ApiCallCenter instance = null;
    private AQuery aq;
    private String result;
    private boolean ready = false;

    public static ApiCallCenter getInstance() {
        if (instance == null) {
            synchronized (ApiCallCenter.class) {
                if (instance == null) {
                    instance = new ApiCallCenter();
                }
            }
        }
        return instance;
    }
    public AjaxCallback<String> doGet(Activity caller, ProgressDialog progress, String url) {
        return execute(caller, progress, url, AQuery.METHOD_GET, null);
    }



    public AjaxCallback<String> doDelete(Activity caller, ProgressDialog progress, String url) {
        return execute(caller, progress, url, AQuery.METHOD_DELETE, null);
    }

    public AjaxCallback<String> doPost(Activity caller, ProgressDialog progress, String url, HashMap<String, Object> params) {
        try {
            return execute(caller, progress, url, AQuery.METHOD_POST, params);
        }catch (Exception e){
            Log.d("error",e.getMessage());
            return null;
        }

    }
    public AjaxCallback<String> doPostURl(Activity caller, ProgressDialog progress, String url, HashMap<String, Object> params) {
        return execute(caller, progress, url, AQuery.METHOD_POST, params);
    }

    public AjaxCallback<String> doPut(Activity caller, ProgressDialog progress, String url, HashMap<String, Object> params) {
        return execute(caller, progress, url, AQuery.METHOD_PUT, params);
    }

    private AjaxCallback<String> execute(Activity caller, ProgressDialog progress, String url, int method, Map<String, Object> params) {
        aq = new AQuery(caller);

        AjaxCallback<String> cb = new AjaxCallback<>();
        cb.url(url).encoding("UTF-8").type(String.class).method(method);

        if (method == AQuery.METHOD_POST) {
            cb.params(params);
        }
        if (method == AQuery.METHOD_PUT) {
            cb.params(params);
        }
        aq.progress(progress).sync(cb);

        return cb;
    }
    private AjaxCallback<String> execute(Activity caller, ProgressDialog progress, String url, int method, Map<String, Object> params, JSONObject type) {
        aq = new AQuery(caller);

        AjaxCallback<String> cb = new AjaxCallback<>();
        cb.url(url).type(String.class).method(method);

        if (method == AQuery.METHOD_POST) {
            cb.params(params);
        }
        else if (method == AQuery.METHOD_PUT) {
            cb.params(params);
        }
        aq.progress(progress).sync(cb);

        return cb;
    }
    
}

