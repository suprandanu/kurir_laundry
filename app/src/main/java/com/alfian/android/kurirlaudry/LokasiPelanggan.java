package com.alfian.android.kurirlaudry;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.alfian.android.kurirlaudry.adapter.AdapterLokasi;
import com.alfian.android.kurirlaudry.model.DataPesanan;
import com.alfian.android.kurirlaudry.model.ResponseDataPesanan;
import com.alfian.android.kurirlaudry.model.Responses;
import com.alfian.android.kurirlaudry.volley.Constants;
import com.alfian.android.kurirlaudry.volley.SessionManager;
import com.alfian.android.kurirlaudry.volley.WebServiceConnect;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class LokasiPelanggan extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private GoogleMap mMap;
    ArrayList<LatLng> MarkerPoints;
    ArrayList<Marker> arrayMarker;
    Marker mMarker;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;

    AdapterLokasi adapter;
    ArrayList<DataPesanan> mPesanan;
    WebServiceConnect wsc;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambil);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Initializing
        MarkerPoints = new ArrayList<>();
        arrayMarker = new ArrayList<>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //String id_pesanan   = sm.getId__Pesanan();
        //String user_plg     = sm.getUser_plg();
        //Double longitute    = Double.parseDouble(sm.getLongitute());
        //Double latitute     = Double.parseDouble(sm.getLatitute());

        //get ALL Data Pesanan
        /*wsc = new WebServiceConnect();
        Map<String, String> param = new Hashtable<String, String>();
        wsc.connectNow(Constants.BASE_API_KURIR + "&state=get_all_pesanan", param, new WebServiceConnect.WscCallBack() {
            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), "Tidak bisa terkoneksi dengan server", Toast.LENGTH_SHORT).show();
                Log.d("WSC", "onError: ");
            }

            @Override
            public void onResponse(String response) {
                Log.d("WSC", "onResponse: " + response);
                Responses r = new Gson().fromJson(response, Responses.class);
                if (r.getStatus().equals("success")){
                    TextView cek = (TextView) findViewById(R.id.cek);
                    ResponseDataPesanan said = new Gson().fromJson(response, ResponseDataPesanan.class);
                    cek.setText(said.getData().get(0).getLatitute() + said.getData().get(0).getLongitute());
                    //for (int i=0; i<said.getData().size(); i++){
                        mMap.addMarker(new MarkerOptions().title(said.getData().get(0).getUser_plg()).position(new LatLng(-7.651658, 110.828482)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location)));
                    //}
                }else {
                    Toast.makeText(LokasiPelanggan.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                }

            }
        });*/
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
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

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


        wsc = new WebServiceConnect();
        Map<String, String> param = new Hashtable<String, String>();
        wsc.connectNow(Constants.BASE_API_KURIR + "&state=get_all_pesanan", param, new WebServiceConnect.WscCallBack() {
            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), "Tidak bisa terkoneksi dengan server", Toast.LENGTH_SHORT).show();
                Log.d("WSC", "onError: ");
            }

            @Override
            public void onResponse(String response) {
                Log.d("WSC", "onResponse: " + response);
                Responses r = new Gson().fromJson(response, Responses.class);

                if (r.getStatus().equals("success")){
                    TextView cek = (TextView) findViewById(R.id.cek);
                    final ResponseDataPesanan rdp = new Gson().fromJson(response, ResponseDataPesanan.class);
                    //DataPesanan dp = new Gson().fromJson(response, DataPesanan.class);
                    //cek.setText(rdp.getData().get(0).getLatitute() + rdp.getData().get(0).getLongitute());

                    //cek.setText(getIntent().getStringExtra("filter"));
                    if (getIntent().getStringExtra("filter").equals("ambil")){
                        for(final DataPesanan dp : rdp.getData()){
                            if (dp.getStatus_brg().equals("Menunggu")){
                                final Marker lokasi = mMap.addMarker(new MarkerOptions().title(dp.getUser_plg()).
                                        position(new LatLng(Double.parseDouble(dp.getLatitute()),
                                                Double.parseDouble(dp.getLongitute())))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location)));
                                lokasi.showInfoWindow();
                                //mMap.setOnMarkerClickListener(this);

                                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                    @Override
                                    public void onInfoWindowClick(Marker marker) {
                                        Intent track = new Intent(LokasiPelanggan.this, Navigasi.class);
                                        track.putExtra("filter", "track");
                                        track.putExtra("user", dp.getUser_plg());
                                        track.putExtra("id", dp.getId_pesanan());
                                        Log.d("KIRIM", "onInfoWindowClick: "+ dp.getId_pesanan());
                                        track.putExtra("longitute", dp.getLongitute());
                                        Log.d("KIRIM", "onInfoWindowClick: "+ dp.getLongitute());
                                        track.putExtra("latitute", dp.getLatitute());
                                        startActivity(track);
                                    }
                                });
                            }
                        }
                    }else {
                        for(final DataPesanan dp : rdp.getData()){
                            if (dp.getStatus_brg().equals("Selesai Proses")){
                                final Marker lokasi = mMap.addMarker(new MarkerOptions().title(dp.getUser_plg()).
                                        position(new LatLng(Double.parseDouble(dp.getLatitute()),
                                                Double.parseDouble(dp.getLongitute())))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location)));
                                lokasi.showInfoWindow();
                                //mMap.setOnMarkerClickListener(this);

                                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                    @Override
                                    public void onInfoWindowClick(Marker marker) {
                                        Intent track = new Intent(LokasiPelanggan.this, Navigasi.class);
                                        track.putExtra("filter", "track");
                                        track.putExtra("user", dp.getUser_plg());
                                        track.putExtra("id", dp.getId_pesanan());
                                        Log.d("KIRIM", "onInfoWindowClick: "+ dp.getId_pesanan());
                                        track.putExtra("longitute", dp.getLongitute());
                                        Log.d("KIRIM", "onInfoWindowClick: "+ dp.getLongitute());
                                        track.putExtra("latitute", dp.getLatitute());
                                        startActivity(track);
                                    }
                                });
                            }
                        }
                    }

                }else {
                    Toast.makeText(LokasiPelanggan.this, "Data tidak ada", Toast.LENGTH_SHORT).show();
                }

            }
        });

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(-7.573649, 110.814449))      // Sets the center of the map to Mountain View
                .zoom(12)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LOKASI", "onLocationChanged: "+location.getProvider());
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //mMap.clear();
        //mMap.addMarker(new MarkerOptions().title("Pilih Lokasi Anda!").position(new LatLng(location.getLatitude(), location.getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location)));

    }



    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(500);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
