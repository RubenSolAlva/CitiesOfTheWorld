package com.soleralvarez.citiesoftheworld.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.soleralvarez.citiesoftheworld.adapter.ListAdapter;
import com.soleralvarez.citiesoftheworld.R;
import com.soleralvarez.citiesoftheworld.models.City;
import com.soleralvarez.citiesoftheworld.models.Country;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private ArrayList<City> arrayListCityDB = new ArrayList<>();
    private ArrayList<City> arrayListSearch = new ArrayList<>();
    private Boolean isScrolling = false;
    private int curretItems, totalItems, scrollOutItems;
    private Boolean isSearchingEditText = false;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = v.findViewById(R.id.progressBar);
        recyclerView = v.findViewById(R.id.recyclerView);
        EditText editText = v.findViewById(R.id.editText);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        listAdapter = new ListAdapter(arrayListCityDB, getContext());
        recyclerView.setAdapter(listAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                curretItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();

                if (isScrolling && (curretItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    if (!isSearchingEditText)
                        ((com.soleralvarez.citiesoftheworld.implementations.View) getActivity()).getData(true, false);
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Unused
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayListSearch.clear();
                if (charSequence == null || charSequence.length() == 0) {
                    isSearchingEditText = false;
                    listAdapter = new ListAdapter(arrayListCityDB, getContext());
                    recyclerView.setAdapter(listAdapter);
                } else {
                    isSearchingEditText = true;
                    for (int j = 0; j < arrayListCityDB.size(); j++) {
                        String cityName = arrayListCityDB.get(j).getName();
                        if (cityName.contains(charSequence)) {
                            City city = new City();
                            Country country = new Country();
                            city.setId(arrayListCityDB.get(j).getId());
                            city.setName(cityName);
                            city.setLat(arrayListCityDB.get(j).getLat());
                            city.setLng(arrayListCityDB.get(j).getLng());
                            country.setName(arrayListCityDB.get(j).getCountry().getName());
                            country.setContinent_id(arrayListCityDB.get(j).getCountry().getContinent_id());
                            city.setCountry(country);
                            arrayListSearch.add(city);
                        }
                    }
                    listAdapter = new ListAdapter(arrayListSearch, getContext());
                    recyclerView.setAdapter(listAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Unused
            }
        });
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void hideProgress() {
        if (progressBar != null)
            progressBar.setVisibility(View.INVISIBLE);
    }

    public void showProgress() {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    public void addData(ArrayList<City> arrayCity) {
        for (int i = 0; i < arrayCity.size(); i++) {
            arrayListCityDB.add(arrayCity.get(i));
            listAdapter.notifyDataSetChanged();
        }
    }

    public void addDataCache(ArrayList<City> arrayCity) {
        arrayListCityDB.addAll(arrayCity);
    }
}
