package futsal.dev.yangadha.alpha;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import futsal.dev.yangadha.R;

public class MapsActivity extends FragmentActivity implements LocationListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener {

    private GoogleMap mMap;
    private String[] nama_lap, deskripsi_lap, alamat_lap, kontak_lap;
    private String namaLap;
    int numData;
    private Integer[] id_lap;
    LatLng latLng[], destination, currentLoc;
    Boolean markerD[];
    private Double[] latitude_lap, longitude_lap;
    private Marker myMarker;
    private Button btn_go;
    private Marker[] mk;

    LocationManager locationManager;
    String provider;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        btn_go = (Button) findViewById(R.id.btn_go);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        if (provider != null && !provider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            location = locationManager.getLastKnownLocation(provider);

            locationManager.requestLocationUpdates(provider, 50000, 1, this);

            if(location!=null){
                onLocationChanged(location);
            } else {
                Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
        }

        btn_go.setVisibility(View.GONE);
        btn_go.setOnClickListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getLokasi();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);

        mMap.setOnMarkerClickListener(this);
    }

    private void getLokasi() {
        JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET, Config.URL_GET_LAPANGAN, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        numData = response.length();
                        Log.d("DEBUG_", "Parse JSON");
                        latLng = new LatLng[numData];
                        markerD = new Boolean[numData];
                        nama_lap = new String[numData];
                        alamat_lap = new String[numData];
                        deskripsi_lap = new String[numData];
                        kontak_lap = new String[numData];
                        latitude_lap = new Double[numData];
                        longitude_lap = new Double[numData];
                        id_lap = new Integer[numData];

                        for (int i = 0; i < numData; i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                id_lap[i] = data.getInt("id_lap");
                                latLng[i] = new LatLng(data.getDouble("latitude_lap"),
                                        data.getDouble("longitude_lap"));
                                nama_lap[i] = data.getString("nama_lap");
                                alamat_lap[i] = data.getString("alamat_lap");
                                deskripsi_lap[i] = data.getString("deskripsi_lap");
                                kontak_lap[i] = data.getString("kontak_lap");
                                latitude_lap[i] = data.getDouble("latitude_lap");
                                longitude_lap[i] = data.getDouble("longitude_lap");

                                markerD[i] = false;
                                myMarker = mMap.addMarker(new MarkerOptions()
                                        .position(latLng[i])
                                        .title(nama_lap[i])
                                        .snippet(kontak_lap[i])
                                        .position(latLng[i])
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
                            } catch (JSONException je) {
                            }
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng[i], 15.5f));

                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                        builder.setTitle("Error!");
                        builder.setMessage("No Internet Connection");
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        builder.setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getLokasi();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
        Volley.newRequestQueue(this).add(request);
    }


    @Override
    public void onClick(View v) {
        if (v == btn_go)

        destination = myMarker.getPosition();
        namaLap     = myMarker.getTitle();

//        currentLoc  = new LatLng(location.getLatitude(),location.getLongitude());

        currentLoc = new LatLng(-6.9828315,110.4085595);

        Intent i = null;
        i = new Intent(MapsActivity.this, SimpleDirectionActivity.class);

        Bundle args = new Bundle();
        args.putParcelable("destination", destination);
        args.putString("nama_lapangan", namaLap);
        args.putParcelable("origin", currentLoc);
        i.putExtra("bundle", args);

        startActivity(i);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        if (marker.equals(myMarker))
//

        btn_go.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}