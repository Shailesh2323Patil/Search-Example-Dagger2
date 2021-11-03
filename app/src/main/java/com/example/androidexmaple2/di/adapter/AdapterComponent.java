package com.example.androidexmaple2.di.adapter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidexmaple2.adapter.AdapterCountryList;
import com.example.androidexmaple2.data.model.CountryModel;

import java.util.ArrayList;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {AdapterModule.class})
public interface AdapterComponent {

    AdapterCountryList getAdapterList();

    LinearLayoutManager getLinearLayoutManager();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder setContext(@Named("context") Context context);

        @BindsInstance
        Builder setCountryModel(@Named("arrayCountryModel") ArrayList<CountryModel> countryModelArrayList);

        @BindsInstance
        Builder setListOnClick(@Named("listOnClick") AdapterCountryList.CountryListOnClick listOnClick);

        AdapterComponent build();
    }
}
