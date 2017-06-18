package com.example.isyandra.parkit.view;

import android.content.Intent;
import android.drm.DrmManagerClient;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.example.isyandra.parkit.R;
import com.example.isyandra.parkit.adapter.LokasiAdapter;
import com.example.isyandra.parkit.utils.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailMaps extends AppCompatActivity {

    @BindView(R.id.parkir_name)TextView parkirName;
    @BindView(R.id.listParkir)ListView listView;
    @BindView(R.id.refresh)SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tv_error_message_display)TextView mErrorMessageDisplay;

    private LokasiAdapter adapter;
    public static String lokasiParkir;
    private static final String TAG = DetailMaps.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_maps);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        loadPromoData();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPromoData();
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);

                loadPromoData();
            }
        });

    }

    @OnClick(R.id.btnDetailParkir)
    public void btnDetail(){
        Intent intent = new Intent(this, ActivityPark.class);
        startActivity(intent);
    }

    private void initComponents(){
        parkirName.setText(lokasiParkir);
        listView.setAdapter(new LokasiAdapter(this));

        refreshLayout.setRefreshing(false);
    }

    private void loadPromoData() {
        showPromoDataView();

        initComponents();
    }

    private void showPromoDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // COMPLETED (44) Show mRecyclerView, not mWeatherTextView
        /* Then, make sure the weather data is visible */
        listView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        // COMPLETED (44) Hide mRecyclerView, not mWeatherTextView
        /* First, hide the currently visible data */
        listView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
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
