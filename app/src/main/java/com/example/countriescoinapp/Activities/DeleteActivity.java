package com.example.countriescoinapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.countriescoinapp.CountryService;
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
    private ArrayList<Country> countries = new ArrayList<>();
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

        CountryService countryService = new CountryService();
        // Call getAllCountry and provide a callback implementation
        countryService.getAllCountries(new CountryService.CountriesCallback() {
            @Override
            public void onCountriesReceived(List<Country> countriesResponse) {
                for (int i = 0; i < countriesResponse.size(); i++) {
                    countries.add(countriesResponse.get(i));
                    adapter.add(countriesResponse.get(i).getName());
                    Log.d("pttt", countriesResponse.get(i).getName());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                // Handle the failure case
                // Display an error message or perform any other necessary operations
                Log.e("pttt", "Failed to retrieve countries: " + t.getMessage());
            }
        });


        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long id = Long.valueOf(0);
                selectedOption = spinner.getSelectedItem().toString();
                for (int i = 0; i < countries.size(); i++) {
                    if (countries.get(i).getName().equals(selectedOption)) {
                        id = countries.get(i).getId();
                    }
                }

                Long finalId = id;
                countryService.deleteCountry(id, new CountryService.CountriesCallback() {
                    @Override
                    public void onCountriesReceived(List<Country> countries) {
                        message.setText("The country " + selectedOption + " has been successfully deleted");
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Throwable t) {

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