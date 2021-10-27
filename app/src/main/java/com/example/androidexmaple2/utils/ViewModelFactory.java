package com.example.androidexmaple2.utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidexmaple2.data.repository.RepoCountries;
import com.example.androidexmaple2.ui.main.ViewModelMain;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private Object[] mParams;

    public ViewModelFactory(Application application, Object... params) {
        mApplication = application;
        mParams = params;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == ViewModelMain.class) {
            return (T) new ViewModelMain(mApplication, (RepoCountries) mParams[0]);
        } else {
            return super.create(modelClass);
        }
    }
}
