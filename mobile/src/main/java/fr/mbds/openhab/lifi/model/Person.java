package fr.mbds.openhab.lifi.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Faliherizo on 08/03/2017.
 */

public class Person {
    private String id;
    private String name;
    private String firstname;
    private String login;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person(JSONObject jsonObject) {
        try {

            if(jsonObject.has("_id"))
                this.id = jsonObject.getString("_id");
            if(jsonObject.has("name"))
                this.name = jsonObject.getString("name");
            if(jsonObject.has("firstName"))
                this.firstname = jsonObject.getString("firstName");
            if(jsonObject.has("username"))
                this.login = jsonObject.getString("username");
            if(jsonObject.has("password"))
                this.password = jsonObject.getString("password");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Person> fromJson(JSONArray jsonObjects) {
        ArrayList<Person> persons = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                persons.add(new Person(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return persons;
    }
}
