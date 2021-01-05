package com.example.demo2;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("/zodiac")
    Call<Map<String,List<String>>> getData();
}