package com.example.androidexmaple2.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidexmaple2.data.model.CountryModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

@Dao
public interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CountryModel countryModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllOrders(List<CountryModel> listCountryModel);

    @Update
    void update(CountryModel countryModel);

    @Delete
    void deleteCountry(CountryModel countryModel);

    @Query("DELETE FROM tblCountryModel")
    void deleteAllCountries();

    @Query("SELECT * FROM tblCountryModel where name LIKE :countryName || '%' ")
    Single<List<CountryModel>> getAllCountries(String countryName);
}
