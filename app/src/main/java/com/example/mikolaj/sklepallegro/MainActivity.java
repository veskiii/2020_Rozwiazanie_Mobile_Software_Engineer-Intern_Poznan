package com.example.mikolaj.sklepallegro;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.app.ActionBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView list;
    private Toolbar toolbar;

    ArrayList<Offer> offerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        offerList = new ArrayList<>();
        list = (ListView) findViewById(R.id.listView1);

        new GetOffers().execute();
    }

    private class GetOffers extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            //Making a request to url and getting response
            String url = "https://private-987cdf-allegromobileinterntest.apiary-mock.com/allegro/offers";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url:" + jsonStr);
            if(jsonStr != null){
                try{
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    //Getting JSON Array node
                    JSONArray offers = jsonObj.getJSONArray("offers");

                    //Looping through All Offers
                    for(int i=0; i<offers.length(); i++){
                        JSONObject o = offers.getJSONObject(i);
                        String id = o.getString("id");
                        String name = o.getString("name");
                        String thumbnailUrl = o.getString("thumbnailUrl");
                        String description = o.getString("description");

                        JSONObject price = o.getJSONObject("price");
                        Double amount = Double.parseDouble(price.getString("amount"));
                        String currency = price.getString("currency");

                        Offer offer = new Offer(id, name, thumbnailUrl, amount, currency, description);

                        offerList.add(offer);
                    }
                } catch (final JSONException e){
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            } else{
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), offerList);
            list.setAdapter(customAdapter);
        }
    }
}
