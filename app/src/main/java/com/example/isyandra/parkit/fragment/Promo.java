package com.example.isyandra.parkit.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.isyandra.parkit.R;
import com.example.isyandra.parkit.adapter.AdapterPromo;
import com.example.isyandra.parkit.model.PromoData;
import com.example.isyandra.parkit.utils.AppConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Promo extends Fragment {

    @BindView(R.id.rvListPromo)RecyclerView recyclerView;
    @BindView(R.id.tv_error_message_display)TextView mErrorMessageDisplay;
    @BindView(R.id.refresh)SwipeRefreshLayout refreshLayout;

    private PromoData mPromoData;
    private AdapterPromo mAdapterPromo;
    private GridLayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_promo, container, false);

        ButterKnife.bind(this, view);

        initComponents(view);

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

        return view;
    }

    private void initComponents(View view){
        layoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void jsonParser(String url) {
        refreshLayout.setRefreshing(true);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Main Activity", "Response " + response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                mPromoData = gson.fromJson(response, PromoData.class);
                mAdapterPromo = new AdapterPromo(getContext(), mPromoData.object_name);
                recyclerView.setAdapter(mAdapterPromo);

                refreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showErrorMessage();
                refreshLayout.setRefreshing(false);
            }
        });
        requestQueue.add(stringRequest);
    }

    private void loadPromoData() {
        showPromoDataView();

        jsonParser(AppConstant.PROMO_URL);
    }

    private void showPromoDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // COMPLETED (44) Show mRecyclerView, not mWeatherTextView
        /* Then, make sure the weather data is visible */
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        // COMPLETED (44) Hide mRecyclerView, not mWeatherTextView
        /* First, hide the currently visible data */
        recyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
