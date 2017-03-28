package fr.mbds.openhab.lifi.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faliherizo on 23/03/2017.
 */

public class Preference {
    private String id_user;
    private String titre;
    private Boolean status;
    private String _id;
    private List<Device> devices=new ArrayList<>();

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

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


    public Preference(){

    }
    public Preference(JSONObject jsonObject) {
        try {

            if(jsonObject.has("titre"))
                this.titre = jsonObject.getString("titre");
            if(jsonObject.has("status"))
                this.status = Boolean.valueOf(jsonObject.getString("status"));
            if(jsonObject.has("_id"))
                this._id = jsonObject.getString("_id");
            if(jsonObject.has("id_user"))
                this.id_user = jsonObject.getString("id_user");

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
