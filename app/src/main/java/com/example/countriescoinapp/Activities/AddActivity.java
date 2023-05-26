package com.example.countriescoinapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.countriescoinapp.CountryService;
import com.example.countriescoinapp.R;
import com.example.countriescoinapp.model.Country;
import com.example.countriescoinapp.reotrfit.CountryApi;
import com.example.countriescoinapp.reotrfit.RetrofitService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {

    private MaterialTextView title, message;
    private TextInputEditText country_name, country_coin;
    private MaterialButton add_button;
    private FloatingActionButton back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        findViews();
        init();
    }

    private void findViews() {
        title = findViewById(R.id.title);
        country_name = findViewById(R.id.country_name);
        country_coin = findViewById(R.id.country_coin);
        add_button = findViewById(R.id.add_button);
        back_button = findViewById(R.id.back_button);
        message = findViewById(R.id.message);
    }

    private void init() {
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = country_name.getText().toString();
                String coin = country_coin.getText().toString();
                Country country = new Country();
                country.setCoin(coin);
                country.setName(name);


                CountryService countryService = new CountryService();
                countryService.addCountry(country, new CountryService.CountriesCallback() {
                    @Override
                    public void onCountriesReceived(List<Country> countries) {
                        for (Country country : countries) {
                            if (country.getName().equals(name)) {
                                message.setText("The country " + country.getName() + " has been successfully added");

                            }
                        }
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
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}