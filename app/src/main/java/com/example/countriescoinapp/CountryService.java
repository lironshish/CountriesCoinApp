package com.example.countriescoinapp;

import android.util.Log;

import com.example.countriescoinapp.model.Country;
import com.example.countriescoinapp.reotrfit.CountryApi;
import com.example.countriescoinapp.reotrfit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryService {
    //Retrofit
    private RetrofitService retrofitService = new RetrofitService();
    private CountryApi countryApi = retrofitService.getRetrofit().create(CountryApi.class);

    public interface CountriesCallback {
        void onCountriesReceived(List<Country> countries);

        void onFailure(Throwable t);
    }

    public void getAllCountries(final CountriesCallback callback) {
        Call<List<Country>> allCountriesCall = countryApi.getAllCountries();
        allCountriesCall.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                List<Country> countries = response.body();
                if (countries != null) {
                    callback.onCountriesReceived(countries);
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void deleteCountry(Long id, final CountriesCallback callback) {
        Call<Void> deleteCountryCall = countryApi.deleteCountry(id);
        deleteCountryCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                getAllCountries(callback);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void getCountryData(String name, final CountriesCallback callback) {
        Call<Country> getCountryDataCall = countryApi.getCountry(name);
        getCountryDataCall.enqueue(new Callback<Country>() {
            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                Country country = response.body();
                if (country != null) {
                    List<Country> countries = new ArrayList<>();
                    countries.add(country);
                    callback.onCountriesReceived(countries);
                }
            }

            @Override
            public void onFailure(Call<Country> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void addCountry(Country country,  final CountriesCallback callback){
        Call<Country> addCountryCall = countryApi.addCountry(country);
        addCountryCall.enqueue(new Callback<Country>() {
            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                Country country = response.body();
                if (country != null) {
                    List<Country> countries = new ArrayList<>();
                    countries.add(country);
                    callback.onCountriesReceived(countries);
                }

            }

            @Override
            public void onFailure(Call<Country> call, Throwable t) {

            }
        });

    }

    public void updateCountry(Country country, Long id, final CountriesCallback callback){
        Call<Country> updateCountryCall = countryApi.updateCountry(id, country);
        updateCountryCall.enqueue(new Callback<Country>() {
            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                Country country = response.body();
                if (country != null) {
                    List<Country> countries = new ArrayList<>();
                    countries.add(country);
                    callback.onCountriesReceived(countries);
                }
            }

            @Override
            public void onFailure(Call<Country> call, Throwable t) {

            }
        });

    }


}
