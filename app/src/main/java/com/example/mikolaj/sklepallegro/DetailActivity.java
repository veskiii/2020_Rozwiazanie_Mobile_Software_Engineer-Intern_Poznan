package com.example.mikolaj.sklepallegro;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.text.HtmlCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Offer offer = (Offer) getIntent().getSerializableExtra("Offer");

        ImageView arrowBack = findViewById(R.id.arrow_back);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        ImageView thumbnail = findViewById(R.id.thumbnail);
        TextView detailPrice = findViewById(R.id.detailPrice);
        TextView description = findViewById(R.id.description);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbarTitle.setText(offer.getName());
        Glide.with(getApplicationContext()).load(offer.getThumbnailUrl()).into(thumbnail);

        String cena = "cena: ";
        String priceBold = String.format(Locale.getDefault(),"%.02f", offer.getPrice().getAmount()).replace('.', ',');
        String finalString= cena+priceBold+' '+offer.getPrice().getCurrency();

        final SpannableStringBuilder sb = new SpannableStringBuilder(finalString);
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        sb.setSpan(bss, cena.length(), finalString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        detailPrice.setText(sb);

        description.setText(HtmlCompat.fromHtml(offer.getDescription(),HtmlCompat.FROM_HTML_MODE_LEGACY));


    }
}
