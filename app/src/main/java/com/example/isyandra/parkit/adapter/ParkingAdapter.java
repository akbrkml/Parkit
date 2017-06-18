package com.example.isyandra.parkit.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isyandra.parkit.R;
import com.example.isyandra.parkit.fragment.Parkir;
import com.example.isyandra.parkit.model.Data;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by akbar on 21/05/17.
 */

public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.ParkingHolder> {

    private Context mContext;
    private List<Data> mData;
    private DatabaseReference mDatabaseReference;
    private Data data;

    public static String nama = "";

    public ParkingAdapter(Context mContext) {
        this.mContext = mContext;
        this.mData = new ArrayList<>();
        this.mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Parkit").child("Jakarta").child("Senayan");
        this.mDatabaseReference.addChildEventListener(new DataChildEventListener());
    }

    private class DataChildEventListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Data data = dataSnapshot.getValue(Data.class);
            data.setKey(dataSnapshot.getKey());
            mData.add(0, data);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String key = dataSnapshot.getKey();
            Data newData = dataSnapshot.getValue(Data.class);
            for (Data dt : mData){
                if (dt.getKey().equals(key)){
                    dt.setValues(newData);
                    break;
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    @Override
    public ParkingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(mContext).inflate(R.layout.list_item_parkir, parent, false);
        ParkingHolder holderParking = new ParkingHolder(rowView);
        return holderParking;
    }

    @Override
    public void onBindViewHolder(ParkingHolder holder, int position) {

//        if (mData.get(position).getNama().equals("Parkir Timur")){
            data = mData.get(position);
//        }
//        if (mData.get(position).getNama().equals("Parkir Barat")){
//            data = mData.get(getItemCount() - position - 1);
//        }

//        String lokasi = data.getNama();
//        long sensor1 = data.getSensor1();
//        long sensor2 = data.getSensor2();

        holder.txtNama.setText(data.getNama());
        if (data.getSensor1() == 0){
            holder.txtSensor1.setBackgroundColor(Color.GREEN);
            holder.txtSensor1.setText(String.valueOf(data.getSensor1()));
            holder.txtSensor1.setTextColor(Color.GREEN);
        } else if (data.getSensor1() == 1){
            holder.txtSensor1.setBackgroundColor(Color.RED);
            holder.txtSensor1.setText(String.valueOf(data.getSensor1()));
            holder.txtSensor1.setTextColor(Color.RED);
        } if (data.getSensor2() == 0){
            holder.txtSensor2.setBackgroundColor(Color.GREEN);
            holder.txtSensor2.setText(String.valueOf(data.getSensor2()));
            holder.txtSensor2.setTextColor(Color.GREEN);
        } else if (data.getSensor2() == 1){
            holder.txtSensor2.setBackgroundColor(Color.RED);
            holder.txtSensor2.setText(String.valueOf(data.getSensor2()));
            holder.txtSensor2.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ParkingHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtSensor1)TextView txtSensor1;
        @BindView(R.id.txtSensor2)TextView txtSensor2;
        @BindView(R.id.txtNamaTempat)TextView txtNama;

        public ParkingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}