package fr.mbds.openhab.lifi.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Faliherizo on 27/03/2017.
 */

public class Device {
    private  String name;
    private  String value;
    private  String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Device(JSONObject jsonObject) {
        try {

            if(jsonObject.has("name"))
                this.name = jsonObject.getString("name");
            if(jsonObject.has("value"))
                this.value = jsonObject.getString("value");
            if(jsonObject.has("type"))
                this.type = jsonObject.getString("type");

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
