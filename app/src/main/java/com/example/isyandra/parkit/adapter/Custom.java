package com.example.isyandra.parkit.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.isyandra.parkit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by isyandra on 23/04/17.
 */

public class Custom extends ArrayAdapter<String> {

    private String[] text1;
    private String[] text2;
    private Activity context;
    private LayoutInflater inflater;
    private View view;

    @BindView(R.id.textview1)TextView textView1;
    @BindView(R.id.textView2)TextView textView2;

    public Custom(Activity context,String[] text1,String[] text2){
        super(context, R.layout.listitem_about,text1);
        this.context = context ;
        this.text1 = text1;
        this.text2 = text2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        inflater = context.getLayoutInflater();
        view = inflater.inflate(R.layout.listitem_about, null, true);

        ButterKnife.bind(this, view);

        textView1.setText(text1[position]);
        textView2.setText(text2[position]);

        return view;
    }
}