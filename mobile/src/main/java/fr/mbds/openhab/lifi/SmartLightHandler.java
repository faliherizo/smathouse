package fr.mbds.openhab.lifi;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.androidquery.callback.AjaxStatus;
import com.luciom.opticallbs.LiFiMessage;
import com.luciom.opticallbs.SmartLightHandlerAbs;

import org.apache.http.HttpResponse;import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class SmartLightHandler extends SmartLightHandlerAbs {
    private TextView id_filtered, message;
    private Activity context;

    public SmartLightHandler(TextView id_filtered, TextView message, Activity context) {
        super();
        this.id_filtered = id_filtered;
        this.message = message;
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        String id_filtered_data = "";
        String message_data = "";

        if(msg.what==MsgWhat.NEW_MESSAGE.value) {
            if(msg.obj instanceof LiFiMessage) {
                LiFiMessage lifiMsg = (LiFiMessage)msg.obj;
                SimpleDateFormat sdf = new SimpleDateFormat("[HH'h'mm'm'ss.SSS]\n", Locale.getDefault());
                message_data = sdf.format(Calendar.getInstance().getTime())+"Last ID=";
                for(int i=0; i<lifiMsg.getUID().length; i++) {
                    message_data += String.format("%02X", lifiMsg.getUID()[i]);
                }

                message_data += System.getProperty("line.separator")+"Last DATA=";
                for(int i=0; i<lifiMsg.getUserData().length; i++) {
                    message_data += String.format("%02X", lifiMsg.getUserData()[i]);
                }
                switch(lifiMsg.getUserDataStatus()) {
                    case NO_DATA:
                        message_data += " NO_DATA";
                        break;
                    case DATA_ERROR:
                        message_data += " DATA_ERROR";
                        break;
                    case CRC_NOK:
                        message_data += " CRC_NOK";
                        break;
                    case NO_CRC:
                        message_data += " (NO_CRC)";
                        break;
                    case CRC_OK:
                        message_data += " (CRC_OK)";
                        break;
                }
            }
        }
        else if(msg.what==MsgWhat.FILTERED_UID.value) {
            if(msg.obj instanceof byte[]) {
                id_filtered_data = "FILTERED_ID=";
                byte[] filtered_id = (byte[])msg.obj;
                for(int i=0; i<filtered_id.length; i++) {
                    id_filtered_data += String.format("%02X", filtered_id[i]);
                }

                new WsOpenhab().execute();
            }
        }
        else if(msg.what==MsgWhat.DEAD.value) {
            id_filtered_data = "DEAD";
            message_data = ".";
        }
        else if(msg.what==MsgWhat.START_LBS.value) {
            id_filtered_data = "START";
            message_data = ".";
        }
        else if(msg.what==MsgWhat.STOP_LBS.value) {
            id_filtered_data = "STOP";
            message_data = ".";
        }

        if(!id_filtered_data.equals("")) {
            id_filtered.setText(id_filtered_data);
        }
        if(!message_data.equals("")) {


            message.setText(message_data);
        }
    }


    public class WsOpenhab extends AsyncTask<Void, Void, Void> {

        private final String state=null;
        public String command="OFF";

        public WsOpenhab() {

        }
        private String KEY_ID= "id";
        private String KEY_GCM="gcm";
        private String Error = null;
        @Override
        protected Void doInBackground(Void... params) {
            org.apache.http.HttpResponse response;
            //Todo get and set web service
            String ws_wemo =  "http://192.168.2.103:8080/rest/items/wemo_insight_Insight_1_0_221512K120051F_state";
            try {

                try{
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(ws_wemo);

                    post.setHeader("Content-Type", "text/plain");

                    org.apache.http.entity.StringEntity entity = new org.apache.http.entity.StringEntity(command);
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
                    if (command=="ON"){
                        command="OFF";
                    }
                    else
                        command="ON";

                }catch (Exception e){

                }


                Log.d("teste","http://192.168.202.134:8080/rest/items/wemo_insight_Insight_1_0_221512K120051F_state");

            }catch (Exception ex){
                String Error = ex.getMessage();
                System.out.println(Error);
                Log.d("qsqsqsq","dsdsdsds");
            }
            return null;
        }
        private void onComplete(){
        }
        public void jsonCallback(String url, JSONObject json, AjaxStatus status){
            System.out.println(json.toString());
            if(json != null){
                onComplete();
            }else{
                //ajax error
            }

        }



    }

}

