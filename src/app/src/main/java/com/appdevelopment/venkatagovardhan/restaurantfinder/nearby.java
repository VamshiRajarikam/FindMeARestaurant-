package com.appdevelopment.venkatagovardhan.restaurantfinder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Venkata Govardhan on 3/15/2016.
 */
public class nearby extends AppCompatActivity {
    private String lat;
    private String lng;
    private Marker[] placeMarkers;
    private final int MAX_PLACES = 20;
    private MarkerOptions[] places;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_maps2);



       String url="https://maps.googleapis.com/maps/api/place/textsearch/json?key=AIzaSyD7uGytKgesC33dUte5xqh6JEbd3vZFDRk&query=food+in+64110";

        placeMarkers = new Marker[MAX_PLACES];
        new GetPlaces().execute(url);
    }

     private class GetPlaces extends AsyncTask<String, Void, String> {
         public String result = "";
        @Override
         protected String doInBackground(String... placesURL) {
            StringBuilder placesBuilder = new StringBuilder();
            for (String placeSearchURL : placesURL) {
                HttpClient placesClient = new DefaultHttpClient();
                try {
                    //try to fetch the data
                    HttpGet placesGet = new HttpGet(placeSearchURL);
                    HttpResponse placesResponse = placesClient.execute(placesGet);
                    StatusLine placeSearchStatus = placesResponse.getStatusLine();
                    if (placeSearchStatus.getStatusCode() == 200) {
                        //we have an OK response
                        HttpEntity placesEntity = placesResponse.getEntity();

                        InputStream placesContent = placesEntity.getContent();
                        InputStreamReader placesInput = new InputStreamReader(placesContent);
                        BufferedReader placesReader = new BufferedReader(placesInput);
                        String lineIn;
                        while ((lineIn = placesReader.readLine()) != null) {
                            placesBuilder.append(lineIn);
                        }
                         result = placesBuilder.toString();
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
            return null;
        }
//fetch and parse place data
     }
    protected void onPostExecute(String result) {



        //parse place data returned from Google Places
        if(placeMarkers!=null){
            for(int pm=0; pm<placeMarkers.length; pm++){
                if(placeMarkers[pm]!=null)
                    placeMarkers[pm].remove();
            }
        }
        try {
            //parse JSON
            JSONObject resultObject = new JSONObject(result);
            JSONArray placesArray = resultObject.getJSONArray("results");
            places = new MarkerOptions[placesArray.length()];
            for (int p=0; p<placesArray.length(); p++) {
                boolean missingValue=false;
                LatLng placeLL=null;
                String placeName="";
                String vicinity="";
                int currIcon = R.drawable.icon;
                try{
                    missingValue=false;
                    JSONObject placeObject = placesArray.getJSONObject(p);
                    JSONObject loc = placeObject.getJSONObject("geometry").getJSONObject("location");
                    placeLL = new LatLng(
                            Double.valueOf(loc.getString("lat")),
                            Double.valueOf(loc.getString("lng")));
                    JSONArray types = placeObject.getJSONArray("types");
                    for(int t=0; t<types.length(); t++){
                        //what type is it
                        String thisType=types.get(t).toString();
                        if(thisType.contains("food")){
                            currIcon = R.drawable.icon;
                            break;
                        }


                    }
                    vicinity = placeObject.getString("vicinity");
                    placeName = placeObject.getString("name");


                    //attempt to retrieve place data values
                }
                catch(JSONException jse){
                    missingValue=true;
                    if(missingValue)    places[p]=null;
                    else
                        places[p]=new MarkerOptions()
                                .position(placeLL)
                                .title(placeName)
                                .icon(BitmapDescriptorFactory.fromResource(currIcon))
                                .snippet(vicinity);

                    jse.printStackTrace();
                }
                //parse each place
            }



        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(places!=null && placeMarkers!=null){
            for(int p=0; p<places.length && p<placeMarkers.length; p++){
                //will be null if a value was missing
                GoogleMap theMap = null;
                if(places[p]!=null)
                    placeMarkers[p]=theMap.addMarker(places[p]);
            }
        }
    }
}
