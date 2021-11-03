package com.example.androidexmaple2.di.adapter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidexmaple2.adapter.AdapterCountryList;
import com.example.androidexmaple2.data.model.CountryModel;

import java.util.ArrayList;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {

    @Provides
    LinearLayoutManager provideLinearLayoutManager(@Named("context") Context context) {
        return new LinearLayoutManager(context);
    }

    @Provides
    AdapterCountryList provideAdapterCountryList(@Named("arrayCountryModel") ArrayList<CountryModel> countryModelList,
                                                 @Named("listOnClick") AdapterCountryList.CountryListOnClick listOnClick)  {
        return new AdapterCountryList(countryModelList,listOnClick);
    }

}
