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

public class DeleteActivity extends AppCompatActivity {

    private MaterialTextView title, message;
    private Spinner spinner;
    private MaterialButton delete_button;
    private FloatingActionButton back_button;

    private ArrayList<String> countriesNames = new ArrayList<>();
    private ArrayList<Country> getCountries = new ArrayList<>();
    private String selectedOption = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        initViews();
        init();
    }

    private void initViews() {
        title = findViewById(R.id.title);
        message = findViewById(R.id.message);
        spinner = findViewById(R.id.spinner);
        delete_button = findViewById(R.id.delete_button);
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
                    getCountries.add(countries.get(i));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Country>> allCountriesCall, Throwable t) {

            }
        });


        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long id = Long.valueOf(0);
                selectedOption = spinner.getSelectedItem().toString();
                for (int i = 0; i < getCountries.size(); i++) {
                    if (getCountries.get(i).getName().equals(selectedOption)) {
                        id = getCountries.get(i).getId();
                    }
                }
                Log.d("pttt", "countries size " + getCountries.size());

                Call<Void> deleteCountryCall = countryApi.deleteCountry(id);
                deleteCountryCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        message.setText("The country " + selectedOption + " has been successfully deleted");
                        adapter.notifyDataSetChanged();
                        Log.d("pttt", "countries size " + getCountries.size());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }


                });
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}