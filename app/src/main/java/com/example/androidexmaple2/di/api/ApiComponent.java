package com.example.androidexmaple2.di.api;


import com.example.androidexmaple2.data.repository.RepoCountries;
import com.example.androidexmaple2.network_config.ApiClient;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class})
public interface ApiComponent {

    void inject(ApiClient apiClient);

    void inject(RepoCountries repoCountries);
}
