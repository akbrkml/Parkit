package com.example.isyandra.parkit.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isyandra.parkit.R;
import com.example.isyandra.parkit.adapter.LokasiAdapter;
import com.example.isyandra.parkit.model.Data;
import com.example.isyandra.parkit.view.about.About;
import com.example.isyandra.parkit.view.about.HelpActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private static final String FIREBASE_URL = "https://lightcontrol-5189d.firebaseio.com/Parkit/Jakarta";
    private static final String TAG = MapsActivity.class.getSimpleName();

    Handler UI_HANDLER;

    private Firebase firebaseRef;
    private ValueEventListener eventListener;
    private GoogleMap mMap;

    private int numData;
    private Boolean markerD[];
    private String[] namaData, namaLokasi;
    private Integer[] availableData;
    private Long[] sensor1, sensor2, slot;
    private Double[] rightLocationData, leftLocationData;

    private SearchView searchView;
    private LatLng latLng[];
    private Data data;
    private SupportMapFragment sMapFragment;
    private ActionBarDrawerToggle toggle;
    private DatabaseReference mDatabaseReference;

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.drawer_layout)DrawerLayout drawer;
    @BindView(R.id.nav_view)NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sMapFragment = SupportMapFragment.newInstance();
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase(FIREBASE_URL);
        setSupportActionBar(toolbar);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Parkit").child("Jakarta").child("Senayan");

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();
        if (!sMapFragment.isAdded())
            sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
        else
            sFm.beginTransaction().show(sMapFragment).commit();

        sMapFragment.getMapAsync(this);

        UI_HANDLER = new Handler();
        UI_HANDLER.postDelayed(UI_UPDATE_RUNNABLE, 30000);
    }

    Runnable UI_UPDATE_RUNNABLE = new Runnable() {
        @Override
        public void run() {
            firebaseRef.addValueEventListener(new DataValueListener());
//            mDatabaseReference.addChildEventListener(new DataChildEventListener());
            UI_HANDLER.postDelayed(UI_UPDATE_RUNNABLE, 30000);
        }
    };

    @OnClick(R.id.btn_refresh)
    public void onRefresh(){
        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
        overridePendingTransition(0,0);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.maps, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplication(), "Searching" ,Toast.LENGTH_LONG).show();
                List<Address> addressList = null;

                if (query != null || !query.equals("")) {
                    Geocoder geocoder = new Geocoder(getBaseContext());
                    try {
                        addressList = geocoder.getFromLocationName(query, 15);

                        for(int i=0;i<addressList.size();i++){
                            Address address = (Address) addressList.get(i);
                            if(address.getMaxAddressLineIndex() != -1)
                            {
                                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    Address address = addressList.get(0);

                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(this, HelpActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, About.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class DataValueListener implements ValueEventListener{

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            numData = (int) dataSnapshot.getChildrenCount();
            Log.d(TAG, String.valueOf(numData));
            for (DataSnapshot child : dataSnapshot.child("Senayan").getChildren()) {

                data = child.getValue(Data.class);
//                    numData = (int) dataSnapshot.getChildrenCount();

                Log.d(TAG, String.valueOf(numData));
                markerD = new Boolean[numData];
                rightLocationData = new Double[numData];
                leftLocationData = new Double[numData];
                namaData = new String[numData];
                availableData = new Integer[numData];
                latLng = new LatLng[numData];
                sensor1 = new Long[numData];
                sensor2 = new Long[numData];
                slot = new Long[numData];
                namaLokasi = new String[numData];

                for (int i = 0; i < numData; i++) {

                    rightLocationData[i] = Double.valueOf(data.getLongitude());
                    leftLocationData[i] = Double.valueOf(data.getLatitude());
                    namaData[i] = data.getLokasi();
                    availableData[i] = data.getAvailableSlot();
                    sensor1[i] = data.getSensor1();
                    sensor2[i] = data.getSensor2();
                    slot[i] = data.getSlot();
                    namaLokasi[i] = data.getNama();

                    Log.d(TAG, namaLokasi[i]);
                    latLng[i] = new LatLng(leftLocationData[i], rightLocationData[i]);
                    markerD[i] = false;
                    if (availableData[i] <= 0){
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng[i])
                                .title(namaData[i])
                                .snippet("No available slot")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_satu)));
                    } else {
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng[i])
                                .title(namaData[i])
                                .snippet("Available Slot: " + availableData[i])
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_satu)));
                    }

//                    mMap.addMarker(new MarkerOptions().position(cod));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng[i], 12.0f));
                }

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Log.d(TAG, "Marker clicked");
                        for (int i = 0; i < numData; i++) {

                            if (marker.getTitle().equals(namaData[i])) {
                                if (markerD[i]) {
                                    Log.d(TAG, "panggil activity");
                                    DetailMaps.lokasiParkir = namaData[i];
                                    LokasiAdapter.lokasi = namaData[i];

                                    Intent intent = new Intent(MapsActivity.this, DetailMaps.class);
                                    startActivity(intent);
                                    markerD[i] = false;
                                } else {
                                    Log.d(TAG, "show info");
                                    // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15.5f));
                                    markerD[i] = true;
                                    marker.hideInfoWindow();
                                    marker.showInfoWindow();
                                    Toast ts = Toast.makeText(MapsActivity.this, "Tap once again on marker, for detail Parkit", Toast.LENGTH_LONG);
                                    TextView v = (TextView) ts.getView().findViewById(android.R.id.message);
                                    if (v != null)
                                        v.setGravity(Gravity.CENTER);
                                    ts.show();
                                }
                                marker.hideInfoWindow();
                            } else {
                                markerD[i] = false;
                            }
                        }
                        return false;
                    }

                });
            }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            Log.d(TAG, "The read failed: " + firebaseError.getMessage());
        }
    }

//    private class DataChildEventListener implements ChildEventListener{
//        LokasiAdapter adapter = new LokasiAdapter(getApplicationContext());
//        @Override
//        public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
//            numData = (int) dataSnapshot.getChildrenCount();
//            Log.d(TAG, String.valueOf(numData));
//            for (com.google.firebase.database.DataSnapshot child : dataSnapshot.child("Senayan").getChildren()) {
//
//                data = child.getValue(Data.class);
////                    numData = (int) dataSnapshot.getChildrenCount();
//
//                Log.d(TAG, String.valueOf(numData));
//                markerD = new Boolean[numData];
//                rightLocationData = new Double[numData];
//                leftLocationData = new Double[numData];
//                namaData = new String[numData];
//                availableData = new Integer[numData];
//                latLng = new LatLng[numData];
//                sensor1 = new Long[numData];
//                sensor2 = new Long[numData];
//                slot = new Long[numData];
//                namaLokasi = new String[numData];
//
//                for (int i = 0; i < numData; i++) {
//
//                    rightLocationData[i] = Double.valueOf(data.getLongitude());
//                    leftLocationData[i] = Double.valueOf(data.getLatitude());
//                    namaData[i] = data.getLokasi();
//                    availableData[i] = data.getAvailableSlot();
//                    sensor1[i] = data.getSensor1();
//                    sensor2[i] = data.getSensor2();
//                    slot[i] = data.getSlot();
//                    namaLokasi[i] = data.getNama();
//
//                    Log.d(TAG, namaLokasi[i]);
//                    latLng[i] = new LatLng(leftLocationData[i], rightLocationData[i]);
//                    markerD[i] = false;
//                    mMap.addMarker(new MarkerOptions()
//                            .position(latLng[i])
//                            .title(namaData[i])
//                            .snippet("Available Slot: " + availableData[i])
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_satu)));
////                    mMap.addMarker(new MarkerOptions().position(cod));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng[i], 12.0f));
//
//                    List<Data> mData = new ArrayList<>();
//                    String key = dataSnapshot.getKey();
//                    Data newData = dataSnapshot.getValue(Data.class);
//                    for (Data dt : mData){
//                        if (dt.getKey().equals(key)){
//                            dt.setValues(newData);
//                            break;
//                        }
//                    }
//                }
//
//                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                    @Override
//                    public boolean onMarkerClick(Marker marker) {
//                        Log.d(TAG, "Marker clicked");
//                        for (int i = 0; i < numData; i++) {
//
//                            if (marker.getTitle().equals(namaData[i])) {
//                                if (markerD[i]) {
//                                    Log.d(TAG, "panggil activity");
//                                    DetailMaps.lokasiParkir = namaData[i];
//                                    LokasiAdapter.lokasi = namaData[i];
//
//                                    Intent intent = new Intent(MapsActivity.this, DetailMaps.class);
//                                    startActivity(intent);
//                                    markerD[i] = false;
//                                } else {
//                                    Log.d(TAG, "show info");
//                                    // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15.5f));
//                                    markerD[i] = true;
//                                    if(marker.isInfoWindowShown())
//                                    {
//                                        marker.hideInfoWindow();
//                                        marker.showInfoWindow();
//                                    }
//                                    Toast ts = Toast.makeText(MapsActivity.this, "Tap once again on marker, for detail Parkit", Toast.LENGTH_LONG);
//                                    TextView v = (TextView) ts.getView().findViewById(android.R.id.message);
//                                    if (v != null)
//                                        v.setGravity(Gravity.CENTER);
//                                    ts.show();
//                                }
//                            } else {
//                                markerD[i] = false;
//                            }
//                        }
//                        return false;
//                    }
//
//                });
//            }
//        }
//
//        @Override
//        public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
//            List<Data> mData = new ArrayList<>();
//            String key = dataSnapshot.getKey();
//            Data newData = dataSnapshot.getValue(Data.class);
//            for (Data dt : mData){
//                if (dt.getKey().equals(key)){
//                    dt.setValues(newData);
//                    break;
//                }
//            }
//        }
//
//        @Override
//        public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {
//
//        }
//
//        @Override
//        public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
//
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//
//        }
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        firebaseRef.addValueEventListener(new DataValueListener());
//        mDatabaseReference.addChildEventListener(new DataChildEventListener());
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
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
    }

    @Override
    protected void onStart() {
        super.onStart();
//        DataValueListener dataValueListener = new  DataValueListener();
//        firebaseRef.addValueEventListener(dataValueListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        DataValueListener dataValueListener = new  DataValueListener();
//        firebaseRef.addValueEventListener(dataValueListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        firebaseRef.removeEventListener(eventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        firebaseRef.removeEventListener(eventListener);
    }
}
