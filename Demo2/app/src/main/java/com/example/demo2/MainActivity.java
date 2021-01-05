package com.example.demo2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int CARD_MARGIN_START = 8;
    public static final int CARD_MARGIN_TOP = 8;
    public static final int CARD_MARGIN_BOTTOM = 0;
    public static final int CARD_MARGIN_RIGHT = 8;

    public static final int HEADING_PADDING = 16;

    private LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ScrollView scrollView = new ScrollView(this);
        ViewGroup.LayoutParams scrollParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(scrollParams);
        setContentView(scrollView);
        rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setBackgroundColor(Color.WHITE);
        ViewGroup.LayoutParams rootLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayout.setLayoutParams(rootLayoutParams);
        scrollView.addView(rootLayout);
        fetchData();
    }

    public void addCard(Card card) {
        LinearLayout cardLayout = new LinearLayout(this);
        cardLayout.setOrientation(LinearLayout.VERTICAL);
        cardLayout.setBackgroundColor(Color.WHITE); 
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(pixelToDp(CARD_MARGIN_START), pixelToDp(CARD_MARGIN_RIGHT), pixelToDp(CARD_MARGIN_TOP), pixelToDp(CARD_MARGIN_BOTTOM));
        cardLayout.setPadding(pixelToDp(HEADING_PADDING), pixelToDp(HEADING_PADDING), pixelToDp(HEADING_PADDING), pixelToDp(HEADING_PADDING));
        cardLayout.setLayoutParams(params);


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            cardLayout.setElevation(pixelToDp(4));
        }
        rootLayout.addView(cardLayout);

        TextView heading = new TextView(this);
        heading.setText(card.heading);
        heading.setTextSize(24);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setTextColor(Color.parseColor("#000000"));
        LinearLayout.LayoutParams strengthParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        strengthParams.setMargins(pixelToDp(0), pixelToDp(0), pixelToDp(0), pixelToDp(8));
        heading.setLayoutParams(strengthParams);
        cardLayout.addView(heading);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        contentParams.setMargins(pixelToDp(9), pixelToDp(0), pixelToDp(0), pixelToDp(16));
        content.setLayoutParams(contentParams);
        for (int i = 0; i < card.content.size(); i++) {
            TextView strengthContent = new TextView(this);
            strengthContent.setText(card.content.get(i));
            strengthContent.setTextColor(Color.parseColor("#000000"));
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lParams.setMargins(0, pixelToDp(8), 0, 0);
            strengthContent.setLayoutParams(lParams);
            content.addView(strengthContent);
        }
        cardLayout.addView(content);
    }

    public List<Card> generateData() {
        List<String> strengths = new ArrayList<String>();
        strengths.add("adsadad");
        strengths.add("sdsadsa");
        Card card = new Card("Strengths", strengths);
        List<String> weakness = new ArrayList<>();
        weakness.add("dfsfdsf");
        Card card1 = new Card("Weakness", weakness);
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(card);
        cards.add(card1);
        return cards;
    }

    public void fetchData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://staging.bytehack.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);

        Call<Map<String, List<String>>> call = service.getData();

        call.enqueue(new Callback<Map<String, List<String>>>() {
            @Override
            public void onResponse(Call<Map<String, List<String>>> call, Response<Map<String, List<String>>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Map<String, List<String>> res = response.body();
                ArrayList<Card> cards = new ArrayList<Card>();
                for (Map.Entry<String, List<String>> getResponse : res.entrySet()) {
                    List<String> valueResponse = getResponse.getValue();
                    Card card = new Card(getResponse.getKey(), valueResponse);
                    cards.add(card);
                }

                for (int j = 0; j < cards.size(); j++) {
                    addCard(cards.get(j));
                }
            }
            @Override
            public void onFailure(Call<Map<String, List<String>>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    int pixelToDp(int pixel) {
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) ((pixel * scale) + 0.5f);
    }
}
