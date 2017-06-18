package com.example.isyandra.parkit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.isyandra.parkit.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by akbar on 09/05/17.
 */

public class HelpAdapter extends BaseAdapter {

    private Context mContext;
    private SparseBooleanArray mCollapsedStatus;
    private String[] sampleStrings;

    public HelpAdapter(Context mContext) {
        this.mContext = mContext;
        mCollapsedStatus = new SparseBooleanArray();
        sampleStrings = mContext.getResources().getStringArray(R.array.sampleStrings);
    }

    @Override
    public int getCount() {
        return sampleStrings.length;
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

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_help, parent, false);
        holder = new ViewHolder(view);

        holder.expandableTextView.setText(sampleStrings[position], mCollapsedStatus, position);

        return view;
    }

    static class ViewHolder{

        @BindView(R.id.expand_text_view)
        ExpandableTextView expandableTextView;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
