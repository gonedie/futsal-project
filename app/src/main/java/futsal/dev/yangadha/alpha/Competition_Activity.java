package futsal.dev.yangadha.alpha;

import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import futsal.dev.yangadha.R;

/**
 * Created by bima on 09/05/17.
 */

public class Competition_Activity extends Activity implements ListView.OnItemClickListener{

    private ListView listView;

    private String JSON_STRING;
    private ProgressDialog progressDialog;

    private void showTurnament(){
        JSONObject jsonObject;
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++){
                JSONObject jo   = result.getJSONObject(i);
                String nama_turnament       = jo.getString(Config.TAG_NAME_TURNAMEN);
                String deskripsi_turnamen   = jo.getString(Config.TAG_DESKRIPSI_TURNAMEN);
                String end_date             = jo.getString(Config.TAG_END_DATE);
                String id_turnamen          = jo.getString(Config.TAG_ID_TURNAMEN);

                HashMap<String,String> turnamen = new HashMap<>();
                turnamen.put(Config.TAG_ID_TURNAMEN,id_turnamen);
                turnamen.put(Config.TAG_NAME_TURNAMEN,nama_turnament);
                turnamen.put(Config.TAG_DESKRIPSI_TURNAMEN,deskripsi_turnamen);
                turnamen.put(Config.TAG_END_DATE,"end date - "+end_date);
                list.add(turnamen);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                Competition_Activity.this, list, R.layout.list_item_turnamen,
                new String[]{Config.TAG_ID_TURNAMEN,Config.TAG_NAME_TURNAMEN,Config.TAG_DESKRIPSI_TURNAMEN,Config.TAG_END_DATE},
                new int[]{R.id.id_turnamen,R.id.nama_turnamen,R.id.deskripsi_turnamen,R.id.end_date});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String > {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                JSON_STRING = s;
                showTurnament();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequest(Config.URL_GET_TURNAMEN);
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.competition_layout);
        listView = (ListView) findViewById(R.id.listViewTurnamen);
        listView.setOnItemClickListener(this);

        progressDialog      = new ProgressDialog(this);
        progressDialog.setMessage("fetch data..");
        getJSON();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), Detail_Competition_Activity.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String turnamenID = map.get(Config.TAG_ID_TURNAMEN);
        intent.putExtra(Config.TURNAMEN_ID,turnamenID);
        startActivity(intent);
    }
}
