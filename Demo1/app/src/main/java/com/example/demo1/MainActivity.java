package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView result_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result_text = findViewById(R.id.text_result);
        fetchData();
    }
    public void fetchData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://staging.bytehack.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);

        Call<Map<String, List<String>>> call = service.getData();

        call.enqueue(new Callback<Map<String, List<String>>>() {
            @Override
            public void onResponse(Call<Map<String, List<String>>> call, Response<Map<String, List<String>>> response) {
                String content="";
                if (!response.isSuccessful()) {
                    result_text.setText("code: " + response.code());
                    return;
                }
                Map<String, List<String>> res =  response.body();
//                for(int i=0;i<res.size();i++){
//                    String getContent=""+response.body();
//                    res.put(getContent,List<getContent>)
//                }
            }

            @Override
            public void onFailure(Call<Map<String, List<String>>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}