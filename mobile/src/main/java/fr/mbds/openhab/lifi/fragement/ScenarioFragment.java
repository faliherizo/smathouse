package fr.mbds.openhab.lifi.fragement;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openhab.habdroid.R;
import org.openhab.habdroid.ui.OpenHABBindingFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.mbds.openhab.lifi.adapteur.AdapteurScenario;
import fr.mbds.openhab.lifi.model.Person;
import fr.mbds.openhab.lifi.model.Scenario;
import fr.mbds.openhab.lifi.model.ScenarioDtl;
import fr.mbds.openhab.lifi.service.ApiCallCenter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScenarioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScenarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScenarioFragment extends ListFragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String ARG_USERNAME = "openHABUsername";
    private static final String ARG_PASSWORD = "openHABPassword";
    private static final String ARG_BASEURL = "openHABBaseUrl";

    private OnFragmentInteractionListener mListener;
    private AdapteurScenario adapter;
    ListView listScenario;
    List<Scenario> list= null;
    private SharedPreferences sharedpreferences;
    private static final String PREF_NAME = "SessionManager";
    public static final String KEY_USER = "user";
    public ScenarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScenarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScenarioFragment newInstance(String param1, String param2) {
        ScenarioFragment fragment = new ScenarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static ScenarioFragment newInstance(String baseUrl, String username, String password) {
        ScenarioFragment fragment = new ScenarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        args.putString(ARG_PASSWORD, password);
        args.putString(ARG_BASEURL, baseUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scenario, container, false);
        listScenario = (ListView) rootView.findViewById(R.id.listscenario);
        setProgressDialog();
        sharedpreferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        new ListScenario().execute();
        // Inflate the layout for this fragment
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button3) {
            try {
                //Todo Onclick set request ws to Openhab
                int pos =(Integer) v.getTag();
                Scenario p =(Scenario) adapter.getItem(pos);
                for (ScenarioDtl dtl:p.getScenarioDtls()) {
                    switch(dtl.getType()){
                        case "Switch":
                            String[] params = new String[2];
                            params[1]=dtl.getValue();
                            if(dtl.getName()=="wemo_insight_Insight_1_0_221512K120051F_state") {
                                params[0]="wemo_insight_Insight_1_0_221512K120051F_state";

                            }else{
                                params[0]= "wemo_insight_Insight_1_0_221606K1200165_state";
                            }
                            new ExecuteScenario().execute(params);
                            break;
                        case "checkbox":

                            break;
                        case "tempmax":

                            break;
                        case "tempmin":

                            break;
                        default: break;
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void refresh() {
        //Log.d(TAG, "refresh()");
    }

    public class ExecuteScenario extends AsyncTask<String[], Void, Boolean>{

        @Override
        protected Boolean doInBackground(String[]... params) {
            //Todo get value of Prise ( if ON )
            start_ws(params[0]);
            return null;
        }
        public void start_ws(String[] status){
            final String state=null;
            String command="ON";
            try{
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(getString(R.string.openhab_ws_item_url)+status[0]);
                post.setHeader("Content-Type", "text/plain");
                org.apache.http.entity.StringEntity entity = new org.apache.http.entity.StringEntity(status[1]);
                post.setEntity(entity);
                HttpResponse responses = client.execute(post);
                Log.d("Post parameters : " , post.getEntity().toString());
                Log.d("Response Code : " ,"ok"+responses.getStatusLine().getStatusCode());

                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(responses.getEntity().getContent()));
                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                Log.d("huhuhu",result.toString());
                    command=status[0];
            }catch (Exception e){

            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }
    }
    ProgressDialog progressDialog;
    public void setProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Patientez...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
    public class ListScenario extends AsyncTask<Integer, Void, List<Scenario>> {
        @Override
        protected List<Scenario> doInBackground(Integer... params) {
            JSONArray jsonArray = null;
            String result;
            try {
                String  p=sharedpreferences.getString(KEY_USER, "");
                Gson gson = new Gson();
                Person person =  gson.fromJson(p, Person.class);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet(getString(R.string.nodejs_server_url)+"scenarioMobile?idUser="+person.getId());

                HttpResponse response = client.execute(request);
                InputStream  inputStream= response.getEntity().getContent();
                if(inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                    jsonArray = new JSONArray(result);
                }
                if(jsonArray.toString().equals("[]"))
                    return Scenario.GetIniList();

                return Scenario.fromJson(jsonArray);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }
        @Override
        protected void onPostExecute(List<Scenario> listscenario) {
            super.onPostExecute(listscenario);
            adapter = new AdapteurScenario(getActivity(), listscenario, ScenarioFragment.this);
            listScenario.setAdapter(adapter);
            Toast.makeText(getActivity(), "Liste chargée", Toast.LENGTH_LONG).show();
        }
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
