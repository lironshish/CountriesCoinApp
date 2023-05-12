package com.example.countriescoinapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.countriescoinapp.model.Country;
import com.example.countriescoinapp.reotrfit.CountryApi;
import com.example.countriescoinapp.reotrfit.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView coin;
    private Button button;
    ArrayList<String> countriesNames = new ArrayList<>();
    private String selectedOption = "", countryCoin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        //Views
        spinner = findViewById(R.id.spinner);
        coin = findViewById(R.id.coin);
        button = findViewById(R.id.button);

        //Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countriesNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Retrofit
        RetrofitService retrofitService = new RetrofitService();
        CountryApi countryApi = retrofitService.getRetrofit().create(CountryApi.class);

        Call<List<Country>> allCountriesCall = countryApi.getAllCountries();
        allCountriesCall.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> allCountriesCall, Response<List<Country>> response) {

                List<Country> countries = response.body();

                for (int i = 0; i < countries.size(); i++) {
                    adapter.add(countries.get(i).getName());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Country>> allCountriesCall, Throwable t) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedOption = spinner.getSelectedItem().toString();

                Log.d("pttt", selectedOption);
                Call<Country> countryCoinCall = countryApi.getCountry(selectedOption);

                countryCoinCall.enqueue(new Callback<Country>() {
                    @Override
                    public void onResponse(Call<Country> call, Response<Country> response) {
                        Country country = response.body();
                        Log.d("pttt", "1 "+country.getCoin());
                        coin.setText(country.getCoin());
                        Log.d("pttt", country.getCoin());
                    }

                    @Override
                    public void onFailure(Call<Country> call, Throwable t) {

                    }
                });
            }
        });


    }
}