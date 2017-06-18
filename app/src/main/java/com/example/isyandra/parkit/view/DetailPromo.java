package com.example.isyandra.parkit.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isyandra.parkit.R;
import com.example.isyandra.parkit.model.PromoData;
import com.example.isyandra.parkit.utils.AppConstant;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

public class DetailPromo extends AppCompatActivity {

    @BindView(R.id.imagePromo)ImageView mImagePromo;
    @BindView(R.id.textPromoTitle)TextView mTextPromoTitle;
    @BindView(R.id.textPromoDate)TextView mTextPromoDate;

    private Bundle extras;
    private String mJson;
    private PromoData.Result promoData;
    PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promo);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mAttacher = new PhotoViewAttacher(mImagePromo);

        extras = getIntent().getExtras();
        if(extras !=null){
            mJson = extras.getString(AppConstant.PROMO_JSON_INTENT);
        }

        promoData = new Gson().fromJson(mJson, PromoData.Result.class);

        mTextPromoTitle.setText(promoData.judul);
        mTextPromoDate.setText(promoData.tanggal);
        Picasso.with(this).load(AppConstant.PROMO_IMAGE_URL + promoData.gambar)
                .placeholder(R.drawable.promo).into(mImagePromo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
