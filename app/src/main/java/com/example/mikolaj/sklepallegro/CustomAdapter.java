package com.example.mikolaj.sklepallegro;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

class CustomAdapter extends BaseAdapter {
    Context context;
    private ArrayList<Offer> offers;
    private LayoutInflater inflater;

    CustomAdapter(Context applicationContext, ArrayList<Offer> offers){
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
        return offers.get(position);
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

        name.setText(offers.get(position).getName());

        String cena = "cena: ";
        String priceBold = String.format(Locale.getDefault(),"%.02f", offers.get(position).getPrice().getAmount()).replace('.', ',');
        String finalString= cena+priceBold+' '+offers.get(position).getPrice().getCurrency();

        final SpannableStringBuilder sb = new SpannableStringBuilder(finalString);
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        sb.setSpan(bss, cena.length(), finalString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        price.setText(sb);
        return convertView;
    }


}
