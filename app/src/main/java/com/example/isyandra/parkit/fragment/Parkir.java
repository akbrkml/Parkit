package com.example.isyandra.parkit.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.isyandra.parkit.R;
import com.example.isyandra.parkit.adapter.LokasiAdapter;
import com.example.isyandra.parkit.view.ActivityPark;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Parkir extends Fragment {

    @BindView(R.id.txtSensor1)TextView btnSensor1;
    @BindView(R.id.txtSensor2)TextView btnSensor2;
    @BindView(R.id.txtNamaTempat)TextView txtNamaTempat;

    public static long sensor1, sensor2;
    public static String tempatParkir;

    Handler UI_HANDLER;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_parkir, container, false);

        ButterKnife.bind(this, view);

        UI_HANDLER = new Handler();
        UI_HANDLER.postDelayed(UI_UPDATE_RUNNABLE, 1000);
        initComponents();

        return view;
    }

    private void initComponents(){
        txtNamaTempat.setText(tempatParkir);

//        if (sensor1 == 0){
//            btnSensor1.setBackgroundColor(Color.GREEN);
//        }else if (sensor1 == 1){
//            btnSensor1.setBackgroundColor(Color.RED);
//        }
//
//        if (sensor2 == 0){
//            btnSensor2.setBackgroundColor(Color.GREEN);
//        }else if (sensor2 == 1){
//            btnSensor2.setBackgroundColor(Color.RED);
//        }

        if (LokasiAdapter.sensorsatu == 0){
            btnSensor1.setBackgroundColor(Color.GREEN);
        }else if (LokasiAdapter.sensorsatu == 1){
            btnSensor1.setBackgroundColor(Color.RED);
        }
        if (LokasiAdapter.sensordua == 0){
            btnSensor2.setBackgroundColor(Color.GREEN);
        }else if (LokasiAdapter.sensordua == 1){
            btnSensor2.setBackgroundColor(Color.RED);
        }


    }

    Runnable UI_UPDATE_RUNNABLE = new Runnable() {
        @Override
        public void run() {
            initComponents();
//            if (LokasiAdapter.sensorsatu == 0){
//                btnSensor1.setBackgroundColor(Color.GREEN);
//            }else if (LokasiAdapter.sensorsatu == 1){
//                btnSensor1.setBackgroundColor(Color.RED);
//            }
//            if (LokasiAdapter.sensordua == 0){
//                btnSensor2.setBackgroundColor(Color.GREEN);
//            }else if (LokasiAdapter.sensordua == 1){
//                btnSensor2.setBackgroundColor(Color.RED);
//            }
            UI_HANDLER.postDelayed(UI_UPDATE_RUNNABLE, 1000);
        }
    };
}
