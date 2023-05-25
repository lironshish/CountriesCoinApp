package com.example.countriescoinapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.countriescoinapp.R;
import com.example.countriescoinapp.model.Country;
import com.example.countriescoinapp.reotrfit.CountryApi;
import com.example.countriescoinapp.reotrfit.RetrofitService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetActivity extends AppCompatActivity {


    private MaterialTextView title, country_name, country_coin;
    private Spinner spinner;
    private MaterialButton get_button;
    private FloatingActionButton back_button;
    private ArrayList<String> countriesNames = new ArrayList<>();
    private String selectedOption = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        findViews();
        init();
    }


    private void findViews() {
        title = findViewById(R.id.title);
        country_name = findViewById(R.id.country_name);
        country_coin = findViewById(R.id.country_coin);
        spinner = findViewById(R.id.spinner);
        get_button = findViewById(R.id.get_button);
        back_button = findViewById(R.id.back_button);
    }

    private void init() {
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


        get_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedOption = spinner.getSelectedItem().toString();

                Call<Country> getCountryDataCall = countryApi.getCountry(selectedOption);

                getCountryDataCall.enqueue(new Callback<Country>() {
                    @Override
                    public void onResponse(Call<Country> call, Response<Country> response) {
                        Country country = response.body();
                        country_name.setText("Country Name: " + country.getName());
                        country_coin.setText("Country Coin: " + country.getCoin());
                    }

                    @Override
                    public void onFailure(Call<Country> call, Throwable t) {

                    }
                });
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}