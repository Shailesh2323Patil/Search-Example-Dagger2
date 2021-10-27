package com.example.androidexmaple2.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.androidexmaple2.data.model.CountryModel;
import com.example.androidexmaple2.data.repository.RepoCountries;
import com.example.androidexmaple2.utils.Resource;

import java.util.List;

public class ViewModelMain extends AndroidViewModel {

    private RepoCountries repoCountries;

    public ViewModelMain(Application application,RepoCountries repoCountries) {
        super(application);
        this.repoCountries = repoCountries;
    }

    public MutableLiveData<Resource<List<CountryModel>>> apiFetchCountry() {
        return repoCountries.getMutableLiveDataCountryAPI();
    }

    public MutableLiveData<Resource<List<CountryModel>>> databaseFetchCountry(String countryName) {
        return repoCountries.getMutableLiveDataCountryDB(countryName);
    }
}
