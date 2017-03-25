package fr.mbds.openhab.lifi.model;

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



}
