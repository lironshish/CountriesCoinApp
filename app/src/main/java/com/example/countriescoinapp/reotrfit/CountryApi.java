package com.example.countriescoinapp.reotrfit;


import com.example.countriescoinapp.model.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CountryApi {

    @GET("/country/all")
    Call<List<Country>> getAllCountries();

    @GET("/country/find_country/{id}")
    Call<Country> getCountryById(@Path("id") Long id);

    @GET("/country/find_coin/{name}")
    Call<String> getCoinByName(@Path("name") String name);

    @GET("/country/{name}")
    Call<Country> getCountry(@Path("name") String name);


    @POST("/country/add")
    Call<Country> addCountry(@Body Country country);

    @PUT("/country/update/{id}")
    Call<Country> updateCountry(@Path("id") Long id, @Body Country country);

    @DELETE("/country/delete/{id}")
    Call<Void> deleteCountry(@Path("id") Long id);


}
