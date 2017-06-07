package com.appdevelopment.venkatagovardhan.restaurantfinder;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
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


public class foodSearch extends AppCompatActivity {


    TextView textview;
    String url;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_content);




        Button getData = (Button) findViewById(R.id.button_food);
        getData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText getWord = (EditText) findViewById(R.id.food);
                String search_word = getWord.getText().toString();
                //url = "http://rhymebrain.com/talk?function=getRhymes&word="+search_word;
                url="http://api.nal.usda.gov/ndb/search/?format=json&max=25&offset=0&api_key=NE0gbsSxO78qbSeRIE8l3ZPbAGze6rqXMWwwCwr9&q="+search_word;
                new BackgroundActivity(context).execute(url);

            }
        });

        textview = (TextView) findViewById(R.id.textViewtype);
        textview.setTextColor(Color.parseColor("#E0E6E2"));
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    static Context context;
    View v;







    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction2 = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "rest Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.appdevelopment.venkatagovardhan.restaurantfinder/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction2);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "rest Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.appdevelopment.venkatagovardhan.restaurantfinder/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public class BackgroundActivity extends AsyncTask<String, String, Void> {
        //Params,Progress,Result

        private Context context;
        public String resultConnection = "";
        public String result1 = "";
        public String result2 = "";
        public String TypeArray = "";

        public BackgroundActivity(Context c) {
            context = c;
        }


        @Override
        public Void doInBackground(String... params) {
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
        public void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {


                JSONObject resultObject = new JSONObject(resultConnection);
                JSONObject resultObject1 = resultObject.getJSONObject("list");
                JSONArray placesArray = resultObject1.getJSONArray("item");

                for (int i = 0; i < placesArray.length(); i++) {

                    JSONObject json = placesArray.getJSONObject(i);
                    result1 = result1 + json.getString("name") + "\n";
                    result2=result2+json.getString("ndbno");
                }

                textview.setText(result1);

            }
            catch (JSONException je) {
                je.printStackTrace();
            }
            //textview.setText(resultConnection);


        }

    }


}
