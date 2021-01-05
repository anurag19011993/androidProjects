package com.example.language;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

public interface ApiService {
    @GET("languages")
    Call<List<Language>> getContent();


    @GET("Horoscope/{lang}")
    Call<Map<String ,String[]>> getHoroscope();
}
