package fr.mbds.openhab.lifi.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Faliherizo on 23/03/2017.
 */

public class Preference {
    private String tv;
    private String prise;
    private String temperature_max;
    private String temperature_min;
    private String id_user;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    public String getPrise() {
        return prise;
    }

    public void setPrise(String prise) {
        this.prise = prise;
    }

    public String getTemperature_max() {
        return temperature_max;
    }

    public void setTemperature_max(String temperature_max) {
        this.temperature_max = temperature_max;
    }

    public String getTemperature_min() {
        return temperature_min;
    }

    public void setTemperature_min(String temperature_min) {
        this.temperature_min = temperature_min;
    }
    public Preference(){

    }
    public Preference(JSONObject jsonObject) {
        try {

            if(jsonObject.has("tv"))
                this.tv = jsonObject.getString("tv");
            if(jsonObject.has("prise"))
                this.prise = jsonObject.getString("prive");
            if(jsonObject.has("temperature_max"))
                this.temperature_max = jsonObject.getString("temperature_max");
            if(jsonObject.has("temperature_min"))
                this.temperature_min = jsonObject.getString("temperature_min");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Preference> fromJson(JSONArray jsonObjects) {
        ArrayList<Preference> per = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                per.add(new Preference(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return per;
    }
}
