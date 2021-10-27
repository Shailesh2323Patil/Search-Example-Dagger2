package com.example.androidexmaple2.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.androidexmaple2.data.database.CountryDao;
import com.example.androidexmaple2.data.database.Database;
import com.example.androidexmaple2.data.model.CountryModel;
import com.example.androidexmaple2.di.DaggerMyComponent;
import com.example.androidexmaple2.di.MyComponent;
import com.example.androidexmaple2.network_config.ApiClient;
import com.example.androidexmaple2.utils.Resource;
import java.util.List;
import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RepoCountries {

    private static final String TAG = "RepoCountries";

    @Inject
    public ApiClient apiClient;

    private MutableLiveData<Resource<List<CountryModel>>> _mutableLiveDataCountry = new MutableLiveData<Resource<List<CountryModel>>>();
    private CompositeDisposable disposable = new CompositeDisposable();

    MyComponent myComponent;

    private CountryDao countryDao;

    public RepoCountries(Application application) {
        myComponent = DaggerMyComponent.create();
        myComponent.inject(RepoCountries.this);

        Database database = Database.getInstance(application);
        countryDao = database.countryDao();
    }

    public MutableLiveData<Resource<List<CountryModel>>> getMutableLiveDataCountryAPI() {
        return fetchFromAPICountries();
    }

    public MutableLiveData<Resource<List<CountryModel>>> getMutableLiveDataCountryDB(String countryName) {
        return fetchFromDBCountries(countryName);
    }

    private MutableLiveData<Resource<List<CountryModel>>> fetchFromAPICountries() {
        _mutableLiveDataCountry.setValue(Resource.loading(null));

        disposable.add(
                apiClient.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<CountryModel>>() {
                    @Override
                    public void onSuccess(@NonNull List<CountryModel> listResource) {
                        _mutableLiveDataCountry.setValue(Resource.success(listResource));

                        insertAllData(listResource);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _mutableLiveDataCountry.setValue(Resource.error(e.getMessage().toString(),null));
                    }
                })
        );

        return _mutableLiveDataCountry;
    }

    private void insertAllData(List<CountryModel> countryModelList) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                countryDao.deleteAllCountries();
                countryDao.insertAllOrders(countryModelList);
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new CompletableObserver() {
              @Override
              public void onSubscribe(@NonNull Disposable d) {
                  Log.e(TAG, "onSubscribe: Called");
              }

              @Override
              public void onComplete() {
                  Log.e(TAG, "onComplete: Called");
              }

              @Override
              public void onError(@NonNull Throwable e) {
                  Log.e(TAG, "onError: "+ e.getMessage());
              }
          });
    }

    private MutableLiveData<Resource<List<CountryModel>>> fetchFromDBCountries(String countryName) {

        _mutableLiveDataCountry.setValue(Resource.loading(null));

        disposable.add(
            countryDao.getAllCountries(countryName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<List<CountryModel>>() {
                        @Override
                        public void onSuccess(@androidx.annotation.NonNull List<CountryModel> countryModels) {
                            _mutableLiveDataCountry.setValue(Resource.success(countryModels));
                        }

                        @Override
                        public void onError(@androidx.annotation.NonNull Throwable e) {
                            _mutableLiveDataCountry.setValue(Resource.error(e.getMessage().toString(),null));
                        }
                    }));

        return _mutableLiveDataCountry;
    }
}
