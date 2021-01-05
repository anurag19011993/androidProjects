package com.example.language;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private String URL="http://staging.bytehack.io";
    private static ApiClient instance=null;


    private ApiClient(){
        buildRetrofit(URL);
    }

    private void buildRetrofit(String url){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
