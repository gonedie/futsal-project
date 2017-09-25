package futsal.dev.yangadha.alpha;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Classes.DbModel;
import futsal.dev.yangadha.R;

/**
 * Created by Hendra on 09/05/2017.
 */
public class Controller_MyTeamList extends Activity {
    private List<DbModel> dbModels = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    String JSON_String="";

    private void getDataOff(){
        dbModels.add(new DbModel("Bima Dinda", "21 th", "172 cm","50 kg"));
        dbModels.add(new DbModel("Candika Abdi", "20 th", "175 cm","60 kg"));
        dbModels.add(new DbModel("Dede Noor", "20 th", "179 cm","61 kg"));
        dbModels.add(new DbModel("Mahendra", "21 th", "172 cm","50 kg"));
        dbModels.add(new DbModel("Hico Purwadinata", "20 th", "175 cm","60 kg"));
        dbModels.add(new DbModel("Kiki Febriana", "20 th", "179 cm","61 kg"));
        dbModels.add(new DbModel("Tatang Sunarya", "20 th", "179 cm","61 kg"));
        dbModels.add(new DbModel("Gomez Pamungkas", "21 th", "172 cm","50 kg"));
        dbModels.add(new DbModel("Danang Dinda", "21 th", "172 cm","50 kg"));
        dbModels.add(new DbModel("Koman Putra", "20 th", "175 cm","60 kg"));
        dbModels.add(new DbModel("Tora Messi", "20 th", "179 cm","61 kg"));
        dbModels.add(new DbModel("Panca Suya", "21 th", "172 cm","50 kg"));
        dbModels.add(new DbModel("Rico Cahyadi", "20 th", "175 cm","60 kg"));
        dbModels.add(new DbModel("Leleng Trendy", "20 th", "179 cm","61 kg"));
        dbModels.add(new DbModel("Bravu Iskandar", "20 th", "179 cm","61 kg"));
        dbModels.add(new DbModel("Tole Cahyadi", "21 th", "172 cm","50 kg"));
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Controller_MyTeamList.this, "Fetching Data", "Wait...", false, false);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest("http://rasyid.esy.es/GetUsers.php");
                return s;

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_String = s;
                Log.e("JSON", ":" + JSON_String);

                try {
                    JSONArray result = new JSONArray(JSON_String);
                    Log.e("result", ":" + result);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        String nama_user = jo.getString("nama_user");
                        String image_user = jo.getString("image_user");
                        String age_user = jo.getString("age_user");
                        Log.e("LL"," "+ nama_user);

                        HashMap<String, Object> MyTeamList = new HashMap<>();
                        MyTeamList.put("nama", nama_user);
                        MyTeamList.put("foto", image_user);
                        MyTeamList.put("umur", age_user);
                        list.add(MyTeamList);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                show();

            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.myteam_layout);
        getJSON();

        HashMap<String, Object> MyTeamList = new HashMap<>();
        MyTeamList.put("nama", "Mahendra Prat");
        MyTeamList.put("foto", "null");
        MyTeamList.put("umur", "20");
        list.add(MyTeamList);
        Log.e("list", ":" + MyTeamList);



    }

    private void show(){
        TextView nmDetPm = (TextView) findViewById(R.id.namaPemain);
        nmDetPm.setText((CharSequence) list.get(0).get("nama"));
        GridView gv = (GridView) findViewById(R.id.grid_view_listteam);
        gv.setAdapter(new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return true;
            }

            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) Controller_MyTeamList.this.getSystemService(
                        Controller_MyTeamList.this.LAYOUT_INFLATER_SERVICE);
                View gridview;
                gridview = inflater.inflate(R.layout.item_myteamlist,null);
                TextView nm_std = (TextView)gridview.findViewById(R.id.namalistPemain);
                nm_std.setText((CharSequence) list.get(position).get("nama"));

                return gridview;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                TextView nmDetPm = (TextView) findViewById(R.id.namaPemain);
                nmDetPm.setText((CharSequence) list.get(position).get("nama"));

                TextView umrDetPm = (TextView) findViewById(R.id.umurPemain);
                umrDetPm.setText((CharSequence) list.get(position).get("umur"));

                ImageView iv = (ImageView) findViewById(R.id.fotoplayer);

                Picasso.with(getApplicationContext())
                        .load((String) list.get(position).get("foto"))
                        .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                        .error(R.mipmap.ic_marker)
                        .into(iv);

            }
        });

    }
}