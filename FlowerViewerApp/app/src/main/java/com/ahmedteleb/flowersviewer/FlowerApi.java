package com.ahmedteleb.flowersviewer;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;


public interface FlowerApi {

    @GET("feeds/flowers.json")
    Call<ArrayList<Flower>> getFlowers();
}

