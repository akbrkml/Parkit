package com.example.isyandra.parkit.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.isyandra.parkit.view.ActivityPark;
import com.example.isyandra.parkit.fragment.Parkir;
import com.example.isyandra.parkit.R;
import com.example.isyandra.parkit.model.Data;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by akbar on 17/05/17.
 */

public class LokasiAdapter extends BaseAdapter {

    private Context mContext;
    private List<Data> mData;
    public static DatabaseReference mDatabaseReference;

    public static String lokasi = "";

    public static Long sensorsatu, sensordua;

    public LokasiAdapter(Context mContext) {
        this.mContext = mContext;
        this.mData = new ArrayList<>();
        this.mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Parkit").child("Jakarta").child(lokasi);
        this.mDatabaseReference.addChildEventListener(new DataChildEventListener());
    }

    public class DataChildEventListener implements ChildEventListener{

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
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final Data data = mData.get(position);

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_lokasi, parent, false);
        holder = new ViewHolder(view);

        long slot = data.getSlot();

        if (slot <= 0){
            holder.txtSlot.setText("No available slot");
        } else {
            holder.txtSlot.setText("Available slot: " + String.valueOf(data.getSlot()));
        }
        holder.txtLokasi.setText(String.valueOf(data.getNama()));

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Parkir.sensor1 = data.getSensor1();
//                Parkir.sensor2 = data.getSensor2();
//                Parkir.tempatParkir = data.getNama();
//                ParkingAdapter.nama = data.getNama();
//
//                Intent intent = new Intent(v.getContext(), ActivityPark.class);
//                mContext.startActivity(intent);
//            }
//        });

        return view;
    }

    static class ViewHolder{
        @BindView(R.id.txtLokasi)
        TextView txtLokasi;
        @BindView(R.id.txtSlot)
        TextView txtSlot;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
