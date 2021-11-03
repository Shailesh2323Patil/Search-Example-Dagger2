package com.example.androidexmaple2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidexmaple2.R;
import com.example.androidexmaple2.data.model.CountryModel;
import com.example.androidexmaple2.databinding.RowCountryListBinding;

import java.util.List;

public class AdapterCountryList extends RecyclerView.Adapter<AdapterCountryList.ViewHolder> {

    private List<CountryModel> countryModelList;
    CountryListOnClick listOnClick;

    public AdapterCountryList(List<CountryModel> countryModelList ,CountryListOnClick countryListOnClick ) {
        this.countryModelList = countryModelList;
        this.listOnClick = countryListOnClick;
    }

    public void updateCountries(List<CountryModel> newCountries ) {
        countryModelList.clear();
        countryModelList.addAll(newCountries);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowCountryListBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_country_list,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CountryModel countryModel = countryModelList.get(position);
        holder.bind(countryModel);
    }

    @Override
    public int getItemCount() {
        return countryModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RowCountryListBinding binding;

        public ViewHolder(@NonNull RowCountryListBinding rowCountryListBinding) {
            super(rowCountryListBinding.getRoot());

            binding = rowCountryListBinding;
        }

        void bind(CountryModel countryModel) {
            binding.txtCountry.setText(countryModel.getCountryName());
            binding.txtCapital.setText(countryModel.getCapital());

            Glide.with(binding.img.getContext())
                    .load(countryModel.getFlag())
                    .into(binding.img);

            binding.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listOnClick.onItemClickListener(countryModel.getCountryName());
                }
            });
        }
    }

    public interface CountryListOnClick {
        void onItemClickListener(String data);
    }

}
