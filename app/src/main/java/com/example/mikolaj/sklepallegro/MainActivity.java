package com.example.mikolaj.sklepallegro;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView list;

    ArrayList<Offer> offerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        offerList = new ArrayList<>();
        list = (ListView) findViewById(R.id.listView1);

        new GetOffers().execute();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Offer selectedItem = (Offer) parent.getItemAtPosition(position);
                Log.e(TAG, selectedItem.getName());
                //Toast.makeText(getApplicationContext(),selectedItem.getName(), Toast.LENGTH_LONG).show();

                switchActivity(selectedItem);
            }
        });
    }

    private class GetOffers extends AsyncTask<Void, Void, Void>{
        /*@Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();
        }*/

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            String url = "https://private-987cdf-allegromobileinterntest.apiary-mock.com/allegro/offers";
            String jsonStr = sh.makeServiceCall(url); //Read response from URL

            Log.e(TAG, "Response from url:" + jsonStr);
            if(jsonStr != null){
                try{
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray offers = jsonObj.getJSONArray("offers");

                    //Create java objects from JSON objects
                    for(int i=0; i<offers.length(); i++){
                        JSONObject o = offers.getJSONObject(i);

                        JSONObject price = o.getJSONObject("price");
                        Double amount = Double.parseDouble(price.getString("amount"));
                        String currency = price.getString("currency");

                        if(amount <= 50 || amount > 1000) continue; //filter for given price range

                        String id = o.getString("id");
                        String name = o.getString("name");
                        String thumbnailUrl = o.getString("thumbnailUrl");
                        String description = o.getString("description");

                        Offer offer = new Offer(id, name, thumbnailUrl, amount, currency, description);

                        offerList.add(offer);
                    }
                } catch (final JSONException e){
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            } else{
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(), "Couldn't get json from server.",Toast.LENGTH_LONG).show();
                    }
                });
            }

            //sort by price ascending
            offerList = quickSort(offerList);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), offerList);
            list.setAdapter(customAdapter);
        }

        private ArrayList<Offer> quickSort(ArrayList<Offer> offerList){
            if(offerList.size() <= 1) return offerList;

            ArrayList<Offer> lesser = new ArrayList<>();
            ArrayList<Offer> greater = new ArrayList<>();
            Offer pivot = offerList.get(offerList.size() - 1);

            for(int i = 0; i < offerList.size() - 1; i++){
                if(offerList.get(i).getPrice().getAmount() < pivot.getPrice().getAmount()){
                    lesser.add(offerList.get(i));
                }
                else{
                    greater.add(offerList.get(i));
                }
            }

            lesser = quickSort(lesser);
            greater = quickSort(greater);

            lesser.add(pivot);
            lesser.addAll(greater);

            return lesser;
        }
    }

    public void switchActivity(Offer selectedItem){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("Offer", selectedItem);
        startActivity(intent);
    }
}
