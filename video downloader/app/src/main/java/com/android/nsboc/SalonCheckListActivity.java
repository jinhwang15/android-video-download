package com.android.nsboc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.nsboc.adapters.SalonAdapter;

/**
 * Created by Administrator on 12/13/2015.
 */
public class SalonCheckListActivity extends AppCompatActivity {

    private static final String SHOW_ALL = "";
    private SalonAdapter mSalonAdapter;
    private int mMatchesCount;
    private String mMatchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list_salon);
        Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_LONG).show();
    }
    public void sanitationCompliance(View view) {
        startActivity(new Intent(this, ComplianceStartActivity.class));//ComplianceStartActivity
    }
}
