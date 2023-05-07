package com.example.countriescoinapp.reotrfit;


import com.example.countriescoinapp.model.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CountryApi {

  @GET("/employee/get-all")
  Call<List<Country>> getAllCountries();

  @POST("/employee/save")
  Call<Country> save(@Body Country country);
}
