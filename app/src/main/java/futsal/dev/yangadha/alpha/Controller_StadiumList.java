package futsal.dev.yangadha.alpha;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import futsal.dev.yangadha.R;


/**
 * Created by bima on 07/05/17.
 */

public class Controller_StadiumList extends Activity{
    String JSON_STRING;
    LatLng latLng[], destination, currentLoc;


    LocationManager locationManager;
    String provider;
    Location location;
    final ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    GridView gridView;


    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Controller_StadiumList.this, "Fetching Data", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                show();

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_LAPANGAN);Log.d("==",":"+s);
                return s;

            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void show() {


        try {
            JSONArray result = new JSONArray(JSON_STRING);
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String nm_lap = jo.getString(Config.TAG_NAMA_LAP);
                String almt_lap = jo.getString(Config.TAG_ALAMAT_LAP);
                String rating_lap = jo.getString(Config.TAG_RATING_LAP);
                String foto_lap  = jo.getString(Config.TAG_FOTO_LAP);
                LatLng destination = new LatLng(jo.getDouble("latitude_lap"),jo.getDouble("longitude_lap"));

                HashMap<String, Object> StadiumList = new HashMap<>();
                StadiumList.put(Config.TAG_NAMA_LAP, nm_lap);
                StadiumList.put(Config.TAG_ALAMAT_LAP, almt_lap);
                StadiumList.put(Config.TAG_RATING_LAP, rating_lap);
                StadiumList.put(Config.TAG_FOTO_LAP, foto_lap);
                StadiumList.put("destination",destination);
                list.add(StadiumList);
                Log.d("FOTO",  "" + foto_lap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(new BaseAdapter() {
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
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) Controller_StadiumList.this.getSystemService(
                        Controller_StadiumList.this.LAYOUT_INFLATER_SERVICE);
                View gridview;
                gridview = inflater.inflate(R.layout.item_stadium,null);
                TextView nm_std = (TextView)gridview.findViewById(R.id.namaStd);
                nm_std.setText((CharSequence) list.get(position).get(Config.TAG_NAMA_LAP));
                TextView almt_std = (TextView)gridview.findViewById(R.id.alamatStd);
                almt_std.setText((CharSequence) list.get(position).get(Config.TAG_ALAMAT_LAP));
                RatingBar rateTeam = (RatingBar)gridview.findViewById(R.id.rating_stadium);
                rateTeam.setRating(Float.parseFloat((String) list.get(position).get(Config.TAG_RATING_LAP)));
                ImageView image_stadium = (ImageView) gridview.findViewById(R.id.image_stadium);
                Glide.with(Controller_StadiumList.this)
                        .load(list.get(position).get(Config.TAG_FOTO_LAP))
                        .into(image_stadium);
                return gridview;
            }
        });

        gridView.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                destination = (LatLng) list.get(position).get("destination");
                currentLoc = new LatLng(-6.9828315,110.4085595);
                Intent i = null;
                i = new Intent(Controller_StadiumList.this, SimpleDirectionActivity.class);

                Bundle args = new Bundle();
                args.putParcelable("destination", destination);
                args.putParcelable("origin", currentLoc);
                i.putExtra("bundle", args);

                Log.e("Message", String.valueOf(destination));
                startActivity(i);

            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.stadium_layout);

        getJSON();

    }

}