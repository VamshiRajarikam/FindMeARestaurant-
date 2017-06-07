package com.appdevelopment.venkatagovardhan.restaurantfinder;

/**
 * Created by Venkata Govardhan on 5/3/2016.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;








/**
 * Created by Venkata Govardhan on 3/14/2016.
 */
public class calorieinfo extends AppCompatActivity {


    TextView textview;
    TextView textview1;
    TextView textview2;
    String url;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal_activity);





        Button getData = (Button) findViewById(R.id.button_food);
        getData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText getWord = (EditText) findViewById(R.id.food);

                String search_word = getWord.getText().toString();
                //url = "http://rhymebrain.com/talk?function=getRhymes&word="+search_word;
                url="http://api.nal.usda.gov/ndb/nutrients/?format=json&api_key=NE0gbsSxO78qbSeRIE8l3ZPbAGze6rqXMWwwCwr9&nutrients=203&nutrients=208&nutrients=291&nutrients=301&nutrients=303&nutrients=208&ndbno="+search_word;
                new BackgroundActivity(context).execute(url);

            }
        });

        textview = (TextView) findViewById(R.id.textViewtype);
        textview1 = (TextView) findViewById(R.id.textView);
        textview2 = (TextView) findViewById(R.id.textView2);
        textview.setTextColor(Color.parseColor("#E0E6E2"));
        textview1.setTextColor(Color.parseColor("#E0E6E2"));
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    static Context context;
    View v;









    public class BackgroundActivity extends AsyncTask<String, String, Void> {
        //Params,Progress,Result

        private Context context;
        public String resultConnection = "";
        public String result1 = "";
        public String result2 = "";
        public String result3 = "";
        public String TypeArray = "";

        public BackgroundActivity(Context c) {
            context = c;
        }


        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL url_new = new URL(url);


                urlConnection = (HttpURLConnection) url_new.openConnection();
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line = null;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                resultConnection = buffer.toString();
            } catch (MalformedURLException e) {
                Log.e("catch", e.toString());
            } catch (IOException e) {
                Log.e("catch", e.toString());
            } finally {


                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }

            return null;

        }


        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {


                JSONObject resultObject = new JSONObject(resultConnection);
                JSONObject resultObject1 = resultObject.getJSONObject("report");
                JSONArray placesArray = resultObject1.getJSONArray("foods");
                for (int i = 0; i <placesArray.length(); i++) {

                    JSONObject json = placesArray.getJSONObject(i);
                    result3 = result3 + json.getString("name") +"\n";

                }
                JSONObject nutrients=placesArray.getJSONObject(0);
                JSONArray nutrients1=nutrients.getJSONArray("nutrients");


                for (int i = 0; i <nutrients1.length(); i++) {

                    JSONObject json = nutrients1.getJSONObject(i);
                    result1 = result1 + json.getString("nutrient") +"\n";
                    result2 = result2 +json.getString("value")+json.getString("unit")+"\n";
                }

                textview.setText(result1);
                textview1.setText(result2);
                textview2.setText(result3);

            }
            catch (JSONException je) {
                je.printStackTrace();
            }
//            textview.setText(resultConnection);


        }

    }


}

