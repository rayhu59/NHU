package com.example.raych.nhu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback
        , GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMarkerClickListener
,Event_Info_frag.OnFragmentInteractionListener{


    DatabaseReference mRef;


    protected GoogleMap mMap;
    protected Marker mCurrentLocationMarker;
    protected Marker mClickedLocationMarker;
    protected static final String TAG = "Location Updates";

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // Keys for storing activity state in the Bundle.
    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;
    DatabaseReference mRef_Event;
    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    protected Boolean mRequestingLocationUpdates = true;

    /**
     * Time when the location was updated represented as a String.
     */
    protected String mLastUpdateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateValuesFromBundle(savedInstanceState);

        // Kick off the process of building a GoogleApiClient and requesting the LocationServices
        // API.
        buildGoogleApiClient();

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Lat: "+ mCurrentLocation.getLatitude() + " Long: "+ mCurrentLocation.getLongitude(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                double currentLatitude = (double) mCurrentLocation.getLatitude();
                double currentLongitude = (double) mCurrentLocation.getLongitude();
                LatLng currentLocation = new LatLng(
                        currentLatitude,
                        currentLongitude);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(13));

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            //might wanna leave blank? - prevent user from going to login screen.
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.toolbar_home:
                Intent zero = new Intent(this, Home.class);
                startActivity(zero);
                return true;
            case R.id.toolbar_create:
                Intent one = new Intent(this, CreateEvent.class);
                startActivity(one);
                return true;
            case R.id.toolbar_host:
                Intent two = new Intent(this, HostingEvent.class);
                startActivity(two);
                return true;
            case R.id.toolbar_join:
                Intent three = new Intent(this, JoinedEvents.class);
                startActivity(three);
                return true;
            case R.id.toolbar_hitup:
                Intent four = new Intent(this, FindHitUp.class);
                startActivity(four);
                return true;
            case R.id.toolbar_logout:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent logout = new Intent(this, login.class);
                startActivity(logout);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                Intent zero = new Intent(this, Home.class);
                startActivity(zero);
                return true;
            case R.id.nav_create:
                Intent one = new Intent(this, CreateEvent.class);
                startActivity(one);
                return true;
            case R.id.nav_host:
                Intent two = new Intent(this, HostingEvent.class);
                startActivity(two);
                return true;
            case R.id.nav_join:
                Intent three = new Intent(this, JoinedEvents.class);
                startActivity(three);
                return true;
            case R.id.nav_hitup:
                Intent four = new Intent(this, FindHitUp.class);
                startActivity(four);
                return true;
            case R.id.nav_logout:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent logout = new Intent(this, login.class);
                startActivity(logout);
                return true;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(TAG, "Updating values from bundle");
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
            }

            // Update the value of mCurrentLocation from the Bundle and update the UI to show the
            // correct latitude and longitude.
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                // Since LOCATION_KEY was found in the Bundle, we can be sure that mCurrentLocation
                // is not null.
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
            }

            Log.e("UpdateValueFromBundle", mCurrentLocation.toString());
            updateUI();
            Log.e("UpdateValueFromBundle X", mCurrentLocation.toString());
        }
    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
     * LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();

        Log.i(TAG, "GoogleApiClient Finished Building");
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        } catch (SecurityException e) {
            Log.e("LocationActivity", e.getMessage());
        }
    }

    /**
     * Updates the latitude, the longitude, and the last location time in the UI.
     */
    private void updateUI() {
        Log.e(TAG, mCurrentLocation.toString());
        Log.e(TAG, "LNG: " + String.valueOf(mCurrentLocation.getLongitude()));
        Log.e(TAG, "LAT: " + String.valueOf(mCurrentLocation.getLatitude()));

        // get lat and long for current location
        // TODO: harcoding values below works, getting values from mCurrentLocation doesn't
        // makes no sense because they are the exact same values and types
        // but blank map activity shows up when using mCurrentLocation
        double currentLatitude = (double) mCurrentLocation.getLatitude();        //43.0392;
        double currentLongitude = (double) mCurrentLocation.getLongitude();      //-76.1351;
        LatLng currentLocation = new LatLng(
                currentLatitude,
                currentLongitude);

        // remove the old location marker
        if (mCurrentLocationMarker != null) {
            mCurrentLocationMarker.remove();
        }

        // update the marker location on the map
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocation);
        markerOptions.title("Current Location");
        markerOptions.snippet("You are here");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        mCurrentLocationMarker = mMap.addMarker(markerOptions);

        // Move the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(13));


        Log.e(TAG, "Maps Markers Updated");
    }



    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.

        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();

        super.onStop();
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");

        // need to request initial locations
        startLocationUpdates();

        if (mCurrentLocation == null) {
            try {

                if (LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient) == null) {
                    mCurrentLocation = new Location("");
                    mCurrentLocation.setLatitude(43.0392);
                    mCurrentLocation.setLongitude(-76.3351);
                } else {
                    mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    Log.e(TAG, LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).toString());
                }

                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                Log.e(TAG, DateFormat.getTimeInstance().format(new Date()).toString());

                Log.e("OnConnected", mCurrentLocation.toString());
                updateUI();
            } catch (SecurityException e) {
                Log.e("LocationActivity", e.getMessage());
            }
        }


    }

    /**
     * Callback that fires when the location changes.
     */
    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        Log.e("OnLocationChanged Start", mCurrentLocation.toString());
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();
        Log.e("OnLocationChanged Done", mCurrentLocation.toString());


    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    //    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    //    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    //    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }


    /**
     * Stores activity data in the Bundle.
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Syracuse, NY.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mRef = FirebaseDatabase.getInstance().getReference().child("eventdata").getRef();
        final Location currentLocation = new Location("Current Location");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    HashMap e = (HashMap) snapshot.getValue();
                    Double lat = Double.valueOf(e.get("lat").toString());
                    Double lng = Double.valueOf(e.get("lng").toString());
                    String eventName = e.get("name").toString();
                    Location point = new Location(eventName);
                    point.setLatitude(lat);
                    point.setLongitude(lng);
               //     float DistanceInMeters = currentLocation.distanceTo(point);
                //    Log.d("Distance", eventName + " : " + DistanceInMeters);
                    String Location = e.get("location").toString();
                    LatLng loc = new LatLng(lat,lng);
                    mMap.addMarker(new MarkerOptions().position(loc).title(eventName)
                    .snippet(Location)
                    );
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mMap.setOnMarkerClickListener(this);
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View info = getLayoutInflater().inflate(R.layout.event_info_window , null);
                LatLng current = marker.getPosition();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                TextView event_name = (TextView)info.findViewById(R.id.info_name);
                TextView event_loc = (TextView)info.findViewById(R.id.info_address);
                String name = marker.getTitle();
                String loc = marker.getSnippet();
                event_name.setText(name);
                event_loc.setText(loc);
                return info;
            }
        });

        mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                mRef_Event = FirebaseDatabase.getInstance().getReference().child("eventdata").getRef();
                final DatabaseReference event = mRef_Event.child(marker.getTitle());
                event.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap e = (HashMap) dataSnapshot.getValue();

                        Event event2 = new Event();
                        event2.setName(e.get("name").toString());
                        event2.setLocation(e.get("location").toString());
                        event2.setCost(e.get("cost").toString());
                        event2.setDate(e.get("date").toString());
                        event2.setTime(e.get("time").toString());
                        event2.setDescription(e.get("description").toString());
                        event2.setYoutubeLink(e.get("youtubeLink").toString());
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.home_container, Event_Info_frag.newInstance(event2))
                                .addToBackStack(null).commit();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });





            }
        });
    }





    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen for landscape and portrait
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
