package com.example.countriescoinapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.countriescoinapp.R;
import com.example.countriescoinapp.model.Country;
import com.example.countriescoinapp.reotrfit.CountryApi;
import com.example.countriescoinapp.reotrfit.RetrofitService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    private MaterialTextView title, message;
    private Spinner spinner;
    private TextInputEditText country_name, country_coin;
    private MaterialButton save_button, get_button;
    private FloatingActionButton back_button;

    private ArrayList<String> countriesNames = new ArrayList<>();
    private String selectedOption = "";
    private Country selectedCountry = new Country();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        findViews();
        init();
    }


    private void findViews() {
        title = findViewById(R.id.title);
        spinner = findViewById(R.id.spinner);
        country_name = findViewById(R.id.country_name);
        country_coin = findViewById(R.id.country_coin);
        save_button = findViewById(R.id.save_button);
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

                Call<Country> countryCoinCall = countryApi.getCountry(selectedOption);

                countryCoinCall.enqueue(new Callback<Country>() {
                    @Override
                    public void onResponse(Call<Country> call, Response<Country> response) {
                        Country country = response.body();
                        selectedCountry = country;
                        country_name.setText(country.getName());
                        country_coin.setText(country.getCoin());
                    }

                    @Override
                    public void onFailure(Call<Country> call, Throwable t) {

                    }
                });
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedCountry.setName(country_name.getText().toString());
                selectedCountry.setCoin(country_coin.getText().toString());
                Call<Country> updateCountryCall = countryApi.updateCountry(selectedCountry);
                updateCountryCall.enqueue(new Callback<Country>() {
                    @Override
                    public void onResponse(Call<Country> call, Response<Country> response) {
                        message.setText("The country " + selectedOption + " has been successfully updated");
                        adapter.notifyDataSetChanged();
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
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}