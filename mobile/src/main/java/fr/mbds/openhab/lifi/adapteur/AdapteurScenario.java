package fr.mbds.openhab.lifi.adapteur;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.openhab.habdroid.R;

import java.util.List;

import fr.mbds.openhab.lifi.model.Scenario;

/**
 * Created by Faliherizo on 25/03/2017.
 */

public class AdapteurScenario extends BaseAdapter {
    private Context context;
    public List<Scenario> listScenario;
    private View.OnClickListener listener;
    public AdapteurScenario(Context context, List<Scenario> listProduct, View.OnClickListener listener){
        this.context = context;
        this.listScenario = listProduct;
        this.listener = listener;
    }
    @Override
    public int getCount() {
        return 0;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ScenarioModel scenarioview;
        if(view==null){
            view = View.inflate(context, R.layout.scenario_dtl, null);
            scenarioview = new ScenarioModel();
        }

        Scenario scenario = listScenario.get(position);

        return view;
    }
}
