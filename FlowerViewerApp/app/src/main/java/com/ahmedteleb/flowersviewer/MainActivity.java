package com.ahmedteleb.flowersviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ListView listFlowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        listFlowers = (ListView) findViewById(R.id.list_flowers);
        getFlowersOnline();
    }

    private void getFlowersOnline() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://services.hanselandpetal.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(FlowerApi.class).getFlowers().enqueue(new Callback<ArrayList<Flower>>() {
            @Override
            public void onResponse(Call<ArrayList<Flower>> call, Response<ArrayList<Flower>> response) {
                progressBar.setVisibility(View.GONE);
                showFlowers(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Flower>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error In Connection", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                getFlowerOffline();
            }
        });
    }

    private void getFlowerOffline() {
        DatabaseHelper helper = new DatabaseHelper(this);
        try {
            showFlowers((ArrayList<Flower>) helper.getFlowerDao().queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showFlowers(ArrayList<Flower> flowers) {
        Log.d("API", "Flowers # " + flowers.size());

        FlowerAdapter adapter = new FlowerAdapter(flowers);
        listFlowers.setAdapter(adapter);

    }


}

