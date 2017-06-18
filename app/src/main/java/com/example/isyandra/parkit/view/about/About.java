package com.example.isyandra.parkit.view.about;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.isyandra.parkit.adapter.Custom;
import com.example.isyandra.parkit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class About extends AppCompatActivity {

    @BindView(R.id.list1)ListView listView;

    private Custom customAdapter;

    private String text1[] = {
            "Current Version",
            "New Version",
            "Term & Conditions"
    };

    private  String text2[] = {
            "1.0.0",
            "1.0.0",
            ""
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customAdapter = new Custom(this,text1,text2);

        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        Intent i = new Intent(About.this, Version.class);
                        startActivity(i);
                        break;
                }
            }
        });
    }

    public static void openFacebookIntent(Context context, String facebookID) {

        Intent intent;

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/"+facebookID));
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+facebookID));
        }

        context.startActivity(intent);
    }

    public static void openInstagramIntent(Context context, String instagramID) {

        Uri uri = Uri.parse("http://instagram.com/_u/"+instagramID);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        intent.setPackage("com.instagram.android");

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/"+instagramID)));
        }
    }

    @OnClick(R.id.facebook)
    public void openFb(){
        openFacebookIntent(About.this,"538648003191096");
    }

    @OnClick(R.id.instagram)
    public void openIg(){
        openInstagramIntent(About.this,"parkit_id");
    }

    @OnClick(R.id.gmail)
    public void openGm(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:parkit@gmail.com"));
        startActivity(emailIntent);
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
