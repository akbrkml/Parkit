package com.example.isyandra.parkit.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isyandra.parkit.R;
import com.example.isyandra.parkit.view.DetailPromo;
import com.example.isyandra.parkit.model.PromoData;
import com.example.isyandra.parkit.utils.AppConstant;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Arief11 on 5/7/2017.
 */

public class AdapterPromo extends RecyclerView.Adapter<AdapterPromo.AdapterHolder> {

    private Context mContext;
    private List<PromoData.Result> mListPromo;

    public AdapterPromo(Context mContext, List<PromoData.Result> mListPromo) {
        this.mContext = mContext;
        this.mListPromo = mListPromo;
    }

    public class AdapterHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.textPromoTitle)TextView mTextPromoTitle;
        @BindView(R.id.gambarPromo)ImageView mImagePromo;

        public AdapterHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mImagePromo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    @Override
    public AdapterPromo.AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(mContext).inflate(R.layout.list_item_promo, parent, false);
        AdapterHolder holderMovies = new AdapterHolder (rowView);
        return holderMovies;
    }

    @Override
    public void onBindViewHolder(final AdapterPromo.AdapterHolder holder, int position) {
        PromoData.Result promoData = mListPromo.get(position);

        holder.mTextPromoTitle.setText(mListPromo.get(position).judul);

        final String dataJsonMovie = new Gson().toJson (promoData, PromoData.Result.class);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailPromo.class);
                intent.putExtra(AppConstant.PROMO_JSON_INTENT, dataJsonMovie);
                view.getContext().startActivity(intent);
            }
        });

        Picasso.with(mContext).load(AppConstant.PROMO_IMAGE_URL + mListPromo.get(position).gambar)
                .placeholder(R.drawable.promo).into(holder.mImagePromo);
    }

    @Override
    public int getItemCount() {
        return mListPromo.size();
    }
}
