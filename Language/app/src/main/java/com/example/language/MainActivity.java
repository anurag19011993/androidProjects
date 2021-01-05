package com.example.language;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView languageText;
    private LinearLayout selectLanguageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);



        ScrollView rootView = new ScrollView(this);
        ViewGroup.LayoutParams rootViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.setLayoutParams(rootViewParams);
        rootView.setBackgroundColor(Color.WHITE);
        setContentView(rootView);

        MySpinnerDialog mySpinnerDialog = new MySpinnerDialog(MainActivity.this);
        mySpinnerDialog.startLoadingDialog();

        selectLanguageLayout = new LinearLayout(this);
        selectLanguageLayout.setOrientation(LinearLayout.VERTICAL);
        selectLanguageLayout.setBackgroundColor(Color.WHITE);
        ViewGroup.LayoutParams selectLanguageLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        selectLanguageLayout.setLayoutParams(selectLanguageLayoutParams);
        rootView.addView(selectLanguageLayout);


    }

    public void viewData() {
        LinearLayout selectLanguageLayoutForText = new LinearLayout(this);
        selectLanguageLayoutForText.setOrientation(LinearLayout.VERTICAL);
        selectLanguageLayoutForText.setBackgroundColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            selectLanguageLayoutForText.setElevation(pixelToDp(5));
        }
        LinearLayout.LayoutParams selectLanguageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectLanguageLayoutParams.setMargins(pixelToDp(10), pixelToDp(5), pixelToDp(10), pixelToDp(5));
        selectLanguageLayoutForText.setLayoutParams(selectLanguageLayoutParams);

        selectLanguageLayout.addView(selectLanguageLayoutForText);

        languageText = new TextView(this);
        languageText.setTextSize(pixelToDp(10));
        languageText.setTextColor(Color.BLACK);
        languageText.setPadding(pixelToDp(5),pixelToDp(5),pixelToDp(5),pixelToDp(5));
        LinearLayout.LayoutParams strengthParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        languageText.setLayoutParams(strengthParams);
        selectLanguageLayoutForText.addView(languageText);


    }

    public void FetchData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://staging.bytehack.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Language>> callContent = apiService.getContent();

        callContent.enqueue(new Callback<List<Language>>() {
            @Override
            public void onResponse(Call<List<Language>> call, Response<List<Language>> response) {
                if (!response.isSuccessful()) {
                    languageText.setText("Code :" + response.code());
                    return;
                }

                List<Language> list = response.body();
                Language demoLang = new Language();
                demoLang.setLabel("Hindi");
                demoLang.setCode("hi");
                list.add(demoLang);
                for (Language content : list) {
                    String ContentReceived = "";
                    ContentReceived += content.getLabel() + "\n";
                    Log.d("getas", ContentReceived);
                    viewData();
                    languageText.setText(ContentReceived);

                }

            }

            @Override
            public void onFailure(Call<List<Language>> call, Throwable t) {
                languageText.setText(t.getMessage());
            }
        });
    }

    int pixelToDp(int pixel) {
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) ((pixel * scale) + 0.5f);
    }
}