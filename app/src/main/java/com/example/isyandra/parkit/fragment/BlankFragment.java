package com.example.isyandra.parkit.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isyandra.parkit.R;
import com.example.isyandra.parkit.adapter.ParkingAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    @BindView(R.id.rv_parking)RecyclerView rvParking;

    private GridLayoutManager layoutManager;
    private ParkingAdapter parkingAdapter;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        ButterKnife.bind(this, view);
        initComponents(view);

        return view;
    }

    private void initComponents(View view){
        layoutManager = new GridLayoutManager(view.getContext(), 1);
        rvParking.setLayoutManager(layoutManager);
        rvParking.setAdapter(new ParkingAdapter(view.getContext()));
    }

}
