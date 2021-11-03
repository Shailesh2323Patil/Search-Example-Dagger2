package com.example.androidexmaple2.network_config;

import com.example.androidexmaple2.data.model.CountryModel;
import com.example.androidexmaple2.di.api.DaggerApiComponent;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ApiClient {
    private static ApiClient apiClient;

    @Inject
    public ApiServices apiServices;

    private ApiClient() {
        DaggerApiComponent.create().inject(this);
    }

    public static ApiClient getInstance() {
        if(apiClient == null) {
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    public Single<List<CountryModel>> getCountries()
    {
        return apiServices.getCountries();
    }
}
