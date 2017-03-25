package fr.mbds.openhab.lifi.adapteur;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import org.openhab.habdroid.R;

import java.util.List;

import fr.mbds.openhab.lifi.model.Scenario;
import fr.mbds.openhab.lifi.model.ScenarioDtl;

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
        ScenarioModel scenarioview = null;;
        if(view==null){
            view = View.inflate(context, R.layout.scenario_dtl, null);
            scenarioview = new ScenarioModel();
            scenarioview.prise =(Switch) view.findViewById(R.id.switchprise);
            scenarioview.OpenDor =(CheckBox) view.findViewById(R.id.radioButtonOpendor);
            scenarioview.titre  =(TextView) view.findViewById(R.id.textView);
            scenarioview.temperaturemin  =(TextView) view.findViewById(R.id.tempMin);
            scenarioview.temperaturemax  =(TextView) view.findViewById(R.id.tempmax);
            view.setTag(view);
        }

        Scenario scenario = listScenario.get(position);
        scenarioview.prise.setChecked(true);

        for (ScenarioDtl dtl: scenario.getScenarioDtls()) {
            switch(dtl.getType()){
                case "Switch":

                    break;
                case "checkbox":

                    break;
                case "text":

                    break;
                default: break;
            }
        }
        return view;
    }
}
