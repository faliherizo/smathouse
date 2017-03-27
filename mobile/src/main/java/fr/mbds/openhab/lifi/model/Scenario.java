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
    private List<ScenarioDtl> scenarioDtls=new ArrayList<>();
    public Scenario(){

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

    public List<ScenarioDtl> getScenarioDtls() {
        return scenarioDtls;
    }

    public void setScenarioDtls(List<ScenarioDtl> scenarioDtls) {
        this.scenarioDtls = scenarioDtls;
    }

    public Scenario(JSONObject jsonObject) {
        try {
            if(jsonObject.has("name"))
                this.titre =jsonObject.getString("name");
            if(jsonObject.has("status"))
                this.status = Boolean.valueOf(jsonObject.getString("status"));
            if(jsonObject.has("_id"))
                this._id = jsonObject.getString("_id");
            if(jsonObject.has("scenariodtl"))
                this.scenarioDtls = ScenarioDtl.fromJson(jsonObject.getJSONArray("scenariodtl"));

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



    public static List<Scenario> GetIniList(){
        List<Scenario> list=new ArrayList<>();
        Scenario s = new Scenario();
        s.setTitre("Scenario 1");
        ScenarioDtl dtl1 = new ScenarioDtl();
        dtl1.setName("wemo_insight_Insight_1_0_221512K120051F_state");
        dtl1.setType("Switch");
        dtl1.setValue("ON");
        s.scenarioDtls.add(dtl1);
        ScenarioDtl dtl2 = new ScenarioDtl();
        dtl2.setName("Porte");
        dtl2.setType("checkbox");
        dtl2.setValue("true");
        s.scenarioDtls.add(dtl2);

        ScenarioDtl dtl5 = new ScenarioDtl();
        dtl5.setName("wemo_insight_Insight_1_0_221606K1200165_state");
        dtl5.setType("Switch");
        dtl5.setValue("OFF");
        s.scenarioDtls.add(dtl5);
        list.add(s);

        Scenario s2 = new Scenario();
        ScenarioDtl dtl4 = new ScenarioDtl();
        dtl4.setName("wemo_insight_Insight_1_0_221512K120051F_state");
        dtl4.setType("Switch");
        dtl4.setValue("OFF");

        s2.scenarioDtls.add(dtl5);
        s2.scenarioDtls.add(dtl4);
        s2.scenarioDtls.add(dtl2);
        s2.setTitre("Scenario 2");
        list.add(s2);
        return list;
    }
}
