package fr.mbds.openhab.lifi.fragement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.openhab.habdroid.R;

import java.io.BufferedReader;
import java.util.HashMap;

import fr.mbds.openhab.lifi.model.Preference;
import fr.mbds.openhab.lifi.service.ApiCallCenter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreferenceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PreferenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreferenceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Preference p = new Preference();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PreferenceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreferenceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreferenceFragment newInstance(String param1, String param2) {
        PreferenceFragment fragment = new PreferenceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preference, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    ProgressDialog progressDialog;
    public void showProgressDialog(boolean isVisible) {
        if (isVisible) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage(getActivity().getResources().getString(R.string.please_wait));
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        progressDialog = null;
                    }
                });
                progressDialog.show();
            }
        } else {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    }
    public class SavePreference extends AsyncTask<Preference, Void, Preference>{

        @Override
        protected Preference doInBackground(Preference... params) {
            Preference preference=null;

            String reponseDuWebService;
            BufferedReader reader=null;
            //Effectuer la requete vers les WS ici (Send data )
            try
            {
                HashMap<String, Object> postParams = new HashMap<>();
                postParams.put("tv", p.getTv());
                postParams.put("prise", p.getPrise());
                postParams.put("temperature_max", p.getTemperature_max());
                postParams.put("temperature_min", p.getTemperature_min());

                //initProgressDialog();
                JSONObject jsonObjectReturn = new JSONObject(ApiCallCenter.getInstance()
                        .doPost(getActivity(), progressDialog, getString(R.string.nodejs_server_url)+"/preference/", postParams).getResult());
                Preference p = new Preference(jsonObjectReturn);
                return p;
            }
            catch(JSONException ex)
            {
                //Error = ex.getMessage();
            }
            catch(Exception ex)
            {
                //Error = ex.getMessage();
            }
            finally
            {
                try
                {
                    reader.close();
                }

                catch(Exception ex) {
                    //Error = ex.getMessage();
                }
            }

            return null;
        }
        @Override
        protected void onPostExecute(Preference pref) {
            super.onPostExecute(pref);
            //Enlever le loading
            showProgressDialog(false);
            if (pref!=null) {
            }else{
                Toast.makeText(getActivity(), "error before submitting...", Toast.LENGTH_LONG).show();
            }
            //Renvoyer vers le login
            startActivity(new Intent(getActivity(), Preference.class));
        }
    }

}
