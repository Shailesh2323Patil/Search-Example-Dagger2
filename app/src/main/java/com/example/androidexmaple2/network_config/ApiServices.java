package com.example.androidexmaple2.network_config;

import com.example.androidexmaple2.data.model.CountryModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiServices {
    @GET("DevTides/countries/master/countriesV2.json")
    Single<List<CountryModel>> getCountries();
}
