package fr.mbds.openhab.lifi.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faliherizo on 25/03/2017.
 */

public class Scenario {
    private String titre;
    private Boolean status;
    private String _id;
    private List<ScenarioDtl> scenarioDtls;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<ScenarioDtl> getScenarioDtls() {
        return scenarioDtls;
    }

    public void setScenarioDtls(List<ScenarioDtl> scenarioDtls) {
        this.scenarioDtls = scenarioDtls;
    }

    public Scenario(JSONObject jsonObject) {
        try {
            if(jsonObject.has("status"))
                this.status = Boolean.valueOf(jsonObject.getString("status"));
            if(jsonObject.has("_id"))
                this._id = jsonObject.getString("_id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Scenario> fromJson(JSONArray jsonObjects) {
        ArrayList<Scenario> scenarios = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                scenarios.add(new Scenario(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return scenarios;
    }
}
