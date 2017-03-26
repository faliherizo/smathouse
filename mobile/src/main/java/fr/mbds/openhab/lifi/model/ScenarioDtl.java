package fr.mbds.openhab.lifi.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Faliherizo on 25/03/2017.
 */

public class ScenarioDtl {
    private String id_scenario;
    private  String name;
    private  String value;
    private  String type;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId_scenario() {
        return id_scenario;
    }

    public void setId_scenario(String id_scenario) {
        this.id_scenario = id_scenario;
    }

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

    public ScenarioDtl(JSONObject jsonObject) {
        try {
            if(jsonObject.has("id_scenario"))
                this.id_scenario =jsonObject.getString("id_scenario");
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


    public static ArrayList<ScenarioDtl> fromJson(JSONArray jsonObjects) {
        ArrayList<ScenarioDtl> scenarios = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                scenarios.add(new ScenarioDtl(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return scenarios;
    }

}
