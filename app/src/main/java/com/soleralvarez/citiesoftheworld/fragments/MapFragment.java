package com.soleralvarez.citiesoftheworld.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.soleralvarez.citiesoftheworld.R;
import com.soleralvarez.citiesoftheworld.models.City;

import java.util.ArrayList;
import java.util.Objects;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private ArrayList<City> arrayListCityDB = new ArrayList<>();
    private TextView txtCityName, txtCountryName, txtLat, txtLng;
    private CardView card_view;
    private ImageView cardWallpaper;

    public MapFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        ((com.soleralvarez.citiesoftheworld.implementations.View) Objects.requireNonNull(getActivity())).getData(false, true);
        txtCityName = v.findViewById(R.id.txtCityName);
        txtCountryName = v.findViewById(R.id.txtCountryName);
        txtLat = v.findViewById(R.id.txtLat);
        txtLng = v.findViewById(R.id.txtLng);
        cardWallpaper = v.findViewById(R.id.cardWallpaper);
        card_view = v.findViewById(R.id.card_view);
        card_view.setVisibility(View.GONE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return v;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                Objects.requireNonNull(getContext()), R.raw.mapstyle));

        for (int j = 0; j < arrayListCityDB.size(); j++) {

            if (arrayListCityDB.get(j).getLat() != null)
                if (arrayListCityDB.get(j).getLng() != null) {
                    double lat = Double.parseDouble(arrayListCityDB.get(j).getLat());
                    double lng = Double.parseDouble(arrayListCityDB.get(j).getLng());
                    LatLng latLng = new LatLng(lat, lng);
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(arrayListCityDB.get(j).getName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
                }
        }
        googleMap.setOnMarkerClickListener(this);
    }

    public void addDataCache(ArrayList<City> arrayCity) {
        arrayListCityDB.addAll(arrayCity);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String cityName = marker.getTitle();
        for (int i = 0; i < arrayListCityDB.size(); i++) {
            if (cityName.equals(arrayListCityDB.get(i).getName())) {
                txtCityName.setText(arrayListCityDB.get(i).getName());
                txtCountryName.setText(arrayListCityDB.get(i).getCountry().getName());
                txtLat.setText(arrayListCityDB.get(i).getLat());
                txtLng.setText(arrayListCityDB.get(i).getLng());
                switch (arrayListCityDB.get(i).getCountry().getContinent_id()) {
                    case 1:
                        cardWallpaper.setImageResource(R.drawable.img1);
                        break;
                    case 2:
                        cardWallpaper.setImageResource(R.drawable.img2);
                        break;
                    case 3:
                        cardWallpaper.setImageResource(R.drawable.img3);
                        break;
                    case 4:
                        cardWallpaper.setImageResource(R.drawable.img4);
                        break;
                    case 7:
                        cardWallpaper.setImageResource(R.drawable.img5);
                        break;
                    default:
                        cardWallpaper.setImageResource(R.drawable.img2);
                        break;
                }
                card_view.setVisibility(View.VISIBLE);
            }
        }

        return false;
    }
}
