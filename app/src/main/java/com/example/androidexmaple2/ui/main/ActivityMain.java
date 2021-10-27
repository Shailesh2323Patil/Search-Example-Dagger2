package com.example.androidexmaple2.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.androidexmaple2.R;
import com.example.androidexmaple2.adapter.AdapterCountryList;
import com.example.androidexmaple2.data.model.CountryModel;
import com.example.androidexmaple2.data.repository.RepoCountries;
import com.example.androidexmaple2.databinding.ActivityMainBinding;
import com.example.androidexmaple2.utils.Resource;
import com.example.androidexmaple2.utils.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity implements AdapterCountryList.CountryListOnClick, View.OnClickListener {

    ActivityMainBinding binding;

    ViewModelMain viewModelMain;

    private AdapterCountryList adapterCountryList;
    private ArrayList<CountryModel> arrayListCountryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialization();
        setObservables();
    }

    private void initialization() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        RepoCountries repoCountries = new RepoCountries(getApplication());

        viewModelMain = new ViewModelProvider(this, new ViewModelFactory(getApplication(), repoCountries)).get(ViewModelMain.class);

        arrayListCountryModel = new ArrayList<CountryModel>();
        adapterCountryList = new AdapterCountryList(arrayListCountryModel, this);
        binding.recyleCountryName.setLayoutManager(new LinearLayoutManager(this));
        binding.recyleCountryName.setAdapter(adapterCountryList);

        binding.btnSearch.setOnClickListener(this);
    }

    private void setObservables() {

        // apiFetchCountry
        viewModelMain.apiFetchCountry().observe(this, new Observer<Resource<List<CountryModel>>>() {
            @Override
            public void onChanged(Resource<List<CountryModel>> listResource) {
                switch (listResource.status) {
                    case LOADING: {
                        binding.loadingView.setVisibility(View.VISIBLE);
                        binding.txtError.setVisibility(View.GONE);
                        binding.recyleCountryName.setVisibility(View.GONE);
                    }
                    break;

                    case SUCCESS: {
                        binding.loadingView.setVisibility(View.GONE);
                        binding.txtError.setVisibility(View.GONE);
                        binding.recyleCountryName.setVisibility(View.VISIBLE);

                        arrayListCountryModel = (ArrayList<CountryModel>) listResource.data;
                        adapterCountryList.updateCountries(arrayListCountryModel);
                    }
                    break;

                    case ERROR: {
                        binding.loadingView.setVisibility(View.GONE);
                        binding.txtError.setVisibility(View.VISIBLE);
                        binding.recyleCountryName.setVisibility(View.GONE);

                        String errorMessage = listResource.data.toString();
                        binding.txtError.setText(errorMessage);
                    }
                    break;
                }
            }
        });
    }

    @Override
    public void onItemClickListener() {

    }

    @Override
    public void onClick(View view) {
        if (view == binding.btnSearch) {
            String countryName = binding.editSearch.getText().toString();

            // databaseFetchCountry
            viewModelMain.databaseFetchCountry(countryName).observe(this, new Observer<Resource<List<CountryModel>>>() {
                @Override
                public void onChanged(Resource<List<CountryModel>> listResource) {
                    switch (listResource.status) {
                        case LOADING: {
                            binding.loadingView.setVisibility(View.VISIBLE);
                            binding.txtError.setVisibility(View.GONE);
                            binding.recyleCountryName.setVisibility(View.GONE);
                        }
                        break;

                        case SUCCESS: {
                            binding.loadingView.setVisibility(View.GONE);
                            binding.txtError.setVisibility(View.GONE);
                            binding.recyleCountryName.setVisibility(View.VISIBLE);

                            arrayListCountryModel = (ArrayList<CountryModel>) listResource.data;
                            adapterCountryList.updateCountries(arrayListCountryModel);
                        }
                        break;

                        case ERROR: {
                            binding.loadingView.setVisibility(View.GONE);
                            binding.txtError.setVisibility(View.VISIBLE);
                            binding.recyleCountryName.setVisibility(View.GONE);

                            String errorMessage = listResource.data.toString();
                            binding.txtError.setText(errorMessage);
                        }
                        break;
                    }
                }
            });
        }
    }
}