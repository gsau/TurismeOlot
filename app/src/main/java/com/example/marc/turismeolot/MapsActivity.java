package com.example.marc.turismeolot;

import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marc.turismeolot.Model.DirectionsJSONParser;
import com.example.marc.turismeolot.Model.Pois;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, GoogleMap.OnMyLocationChangeListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private static final String URL_DATA = "http://www.infobosccoma.net/pmdm/pois.php";

    Spinner cmbTipusMapa;
    Button btnCercar,btnGPS, btnRuta;
    EditText textCercar;
    private ArrayList<Pois> dades;
    private DescarregarDades download;
    private boolean mapa = false;
    ArrayList<LatLng> markerPoints;
    LatLng latLng;

    //private static final LatLng INS_BOSC_DE_LA_COMA = new LatLng(42.1727,2.47631);
    //private static final LatLng CATALUNYA = new LatLng(41.58999460853233,2.3185251535156715);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        setUpGui();
        afegirPunts();
    }

    private void afegirPunts(){

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.18239766550217,2.4880627236411934))
                .title("Sant Esteve"));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.183359630538526,2.4924025378272896
                ))
                .title("El Carme"));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.182882624912196,2.487022026543415))
                .title("Can Solà Morales"));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.18123692787543
                        ,2.489618404869831
                ))
                .title("Hospici"));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.171592441323774
                        ,2.479581578736103
                ))
                .title("Parc Nou"));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.14790831311662
                        ,2.5159201464698677))
                .title("La Fageda"));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.18762072113449
                        ,2.4885079703376656))
                .title("Volcà Montsacopa"));


    }
    //maps.googleapis.com/maps/api/directions/json?origin=olot&destination=girona
    private void setUpGui() {
        cmbTipusMapa = (Spinner) findViewById(R.id.cmbTipusMapa);
        cmbTipusMapa.setOnItemSelectedListener(this);

        btnCercar = (Button)findViewById(R.id.btnCercar);
        btnCercar.setOnClickListener(this);

        btnGPS = (Button)findViewById(R.id.btnGPS);
        btnGPS.setOnClickListener(this);

        btnRuta = (Button)findViewById(R.id.btnRuta);
        btnRuta.setOnClickListener(this);



        textCercar = (EditText)findViewById(R.id.txtCercar);
        markerPoints = new ArrayList<LatLng>();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                // Already 10 locations with 8 waypoints and 1 start location and 1 end location.
                // Upto 8 waypoints are allowed in a query for non-business users
                if(markerPoints.size()>=10){
                    return;
                }

                // Adding new item to the ArrayList
                markerPoints.add(point);

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(point);

                /**
                 * For the start location, the color of marker is GREEN and
                 * for the end location, the color of marker is RED and
                 * for the rest of markers, the color is AZURE
                 * */

                if(markerPoints.size()==1){
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }else if(markerPoints.size()==2){
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }else{
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                }
                //options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                // Add new marker to the Google Map Android API V2
                mMap.addMarker(options);
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng point) {
                // Removes all the points from Google Map
                mMap.clear();

                // Removes all the points in the ArrayList
                markerPoints.clear();
            }
        });


    }

    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Waypoints
        String waypoints = "";
        for(int i=2;i<markerPoints.size();i++){
            LatLng point  = (LatLng) markerPoints.get(i);
            if(i==2)
                waypoints = "waypoints=";
            waypoints += point.latitude + "," + point.longitude + "|";
        }

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+waypoints;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            //Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private void refreshData() {
        if(dades==null) {
            dades = new ArrayList<Pois>();
        }
        //adapter = new TitularsAdapter(this, dades);
        //listTitulars.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMap.setMyLocationEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(this);
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(this);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String tipus = (String)parent.getItemAtPosition(position);

        if(position==0){
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }else if(position==1){
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }else if(position==2){
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }else if(position==3){
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCercar:
                markerPoints.removeAll(markerPoints);
                mMap.clear();
                download = new DescarregarDades();
                try {
                    download.execute(URL_DATA);
                }catch(IllegalStateException ex) {
                }
                break;
            case R.id.btnGPS:
                LatLng myPos = new LatLng(mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos, 17));
                break;
            case R.id.btnRuta:

                clicaRuta();
                break;
        }
    }

    //TODO No funciona
    @Override
    public void onMyLocationChange(Location lastKnownLocation) {
        CameraUpdate myLoc = CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder().target(new LatLng(lastKnownLocation.getLatitude(),
                        lastKnownLocation.getLongitude())).zoom(6).build());


        mMap.moveCamera(myLoc);
        mMap.setOnMyLocationChangeListener(null);

        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude()),12));
    }

    public void clicaRuta(){
        if(markerPoints.size() >= 2){
            LatLng origin = markerPoints.get(0);
            LatLng dest = markerPoints.get(1);

            // Getting URL to the Google Directions API
            String url = getDirectionsUrl(origin, dest);

            DownloadTask downloadTask = new DownloadTask();

            // Start downloading json data from Google Directions API
            downloadTask.execute(url);
        }
        else{
            double latt = mMap.getMyLocation().getLatitude();
            double lngg = mMap.getMyLocation().getLongitude();
            LatLng origen = new LatLng(latt,lngg);
            LatLng desti = markerPoints.get(0);
            String url = getDirectionsUrl(origen, desti);

            DownloadTask downloadTask = new DownloadTask();

            // Start downloading json data from Google Directions API
            downloadTask.execute(url);


        }
    }

    class DescarregarDades extends AsyncTask<String, Void, ArrayList<Pois>> {


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }


        @Override
        protected ArrayList<Pois> doInBackground(String... params) {
            ArrayList<Pois> llistaPois = null;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            //HttpPost httppostreq = new HttpPost(URL_DATA);
            HttpResponse httpresponse = null;
            String ciutat = textCercar.getText().toString();


            return llistaPois;
        }

        @Override
        protected void onPostExecute(ArrayList<Pois> llista) {
            dades = llista;


            if(dades.size()==0){
                String location = textCercar.getText().toString();
                if(location.equals("")){

                }
                else{
                    new GeocoderTask().execute(location);
                }

            }else{
                LatLng augment = new LatLng(dades.get(0).getLatitude(),dades.get(0).getLongitude());
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                for (int i = 0; i < dades.size(); i++) {
                    Pois poi = dades.get(i);
                    double lat = poi.getLatitude();
                    double lng = poi.getLongitude();
                    LatLng posicio = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions()
                            .position(posicio)
                            .snippet(poi.getCity())
                            .title(poi.getName()));
                    builder.include(posicio);
                }


                //LatLngBounds bounds = new LatLngBounds();
                LatLngBounds tmpBounds = builder.build();
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(tmpBounds,200));
            }


            /*
            if(mapa){
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(CATALUNYA, 8));
            }
            else{
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(augment,12));
            }*/

            mapa = false;
        }

        private ArrayList<Pois> tractarJSON(String json) {
            Gson converter = new Gson();
            return converter.fromJson(json, new TypeToken<ArrayList<Pois>>(){}.getType());
        }
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service

            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
                /*double latt = mMap.getMyLocation().getLatitude();
                double lngg = mMap.getMyLocation().getLongitude();
                LatLng pos = new LatLng(latt,lngg);
                points.add(pos);*/

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                    builder.include(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.RED);
                LatLngBounds tmpBounds = builder.build();
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(tmpBounds,150));
            }

            // Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }

    private class GeocoderTask extends AsyncTask<String, Void, List<android.location.Address>> {

        @Override
        protected List<android.location.Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<android.location.Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<android.location.Address> addresses) {

            if (addresses == null || addresses.size() == 0) {
                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
            }

            // Clears all the existing markers on the map
            mMap.clear();

            // Adding Markers on Google Map for each matching address
            for (int i = 0; i < addresses.size(); i++) {

                android.location.Address address = (android.location.Address) addresses.get(i);

                // Creating an instance of GeoPoint, to display in Google Map
                latLng = new LatLng(address.getLatitude(), address.getLongitude());

                String addressText = String.format("%s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getCountryName());


                mMap.addMarker(new MarkerOptions()
                        .position(latLng));
                markerPoints.add(latLng);

                // Locate the first location
                if (i == 0)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
            }
        }

    }}