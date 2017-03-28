package fr.mbds.openhab.lifi.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Faliherizo on 27/03/2017.
 */

public class ScenarioDevice {
    private String id_scenario;
    private String id_device;
    public String getId_scenario() {
        return id_scenario;
    }
    public void setId_scenario(String id_scenario) {
        this.id_scenario = id_scenario;
    }
    public String getId_device() {
        return id_device;
    }
    public void setId_device(String id_device) {
        this.id_device = id_device;
    }
    public ScenarioDevice(JSONObject jsonObject) {
        try {
            if(jsonObject.has("id_device"))
                this.id_device = jsonObject.getString("id_device");
            if(jsonObject.has("id_scenario"))
                this.id_device = jsonObject.getString("id_scenario");
            if(jsonObject.has("id_scenario"))
                this.id_scenario = jsonObject.getString("id_scenario");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Device> fromJson(JSONArray jsonObjects) {
        ArrayList<Device> scenarios = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                scenarios.add(new Device(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return scenarios;
    }
}
