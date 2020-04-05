package com.example.mikolaj.sklepallegro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Offer> offers;
    LayoutInflater inflater;

    public CustomAdapter(Context applicationContext, ArrayList<Offer> offers){
        this.context = applicationContext;
        this.offers = offers;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return offers.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.list_row, null);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView price = (TextView) convertView.findViewById(R.id.price);

        Glide.with(this.context).load(offers.get(position).getThumbnailUrl()).into(icon);

        //icon.setImageBitmap(thumbnail);
        name.setText(offers.get(position).getName());
        String price_text = "cena: " +"<b>"+ String.format("%.02f", offers.get(position).getPrice().getAmount()) +
                ' '+offers.get(position).getPrice().getCurrency()+"</b>";
        price.setText(Html.fromHtml(price_text)); //TODO: aby zachować kompatybilność z nowymi wersjami trzeba inaczej tego html zrobić
        return convertView;
    }


}
