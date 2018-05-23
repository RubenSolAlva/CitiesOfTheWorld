package com.soleralvarez.citiesoftheworld.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soleralvarez.citiesoftheworld.R;
import com.soleralvarez.citiesoftheworld.models.City;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements View.OnClickListener {
    private View.OnClickListener listener;
    private ArrayList<City> arrayListCity;
    private Context context;

    public ListAdapter(ArrayList<City> arrayListCityBBDD, Context context) {
        this.arrayListCity = arrayListCityBBDD;
        this.context = context;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        City city = arrayListCity.get(position);
        holder.txtCityName.setText(city.getName());
        holder.txtCountryName.setText(city.getCountry().getName());
        holder.txtId.setText(String.valueOf(city.getId()));
        holder.txtLat.setText(city.getLat());
        holder.txtLng.setText(city.getLng());
        switch (city.getCountry().getContinent_id()) {
            case 1:
                holder.cardWallpaper.setImageResource(R.drawable.img1);
                break;
            case 2:
                holder.cardWallpaper.setImageResource(R.drawable.img2);
                break;
            case 3:
                holder.cardWallpaper.setImageResource(R.drawable.img3);
                break;
            case 4:
                holder.cardWallpaper.setImageResource(R.drawable.img4);
                break;
            case 7:
                holder.cardWallpaper.setImageResource(R.drawable.img5);
                break;
            default:
                holder.cardWallpaper.setImageResource(R.drawable.img2);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (arrayListCity != null)
            return arrayListCity.size();
        else
            return 0;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.onClick(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCityName, txtCountryName, txtId, txtLat, txtLng;
        private ImageView cardWallpaper;

        ViewHolder(View view) {
            super(view);
            txtCityName = view.findViewById(R.id.txtCityName);
            txtCountryName = view.findViewById(R.id.txtCountryName);
            txtId = view.findViewById(R.id.txtId);
            txtLat = view.findViewById(R.id.txtLat);
            txtLng = view.findViewById(R.id.txtLng);
            cardWallpaper = view.findViewById(R.id.cardWallpaper);
        }
    }
}
