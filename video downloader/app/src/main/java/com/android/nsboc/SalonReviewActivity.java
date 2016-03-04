package com.android.nsboc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.nsboc.adapters.SalonAdapter;

public class SalonReviewActivity extends AppCompatActivity {

    private static final String SHOW_ALL = "";
    private SalonAdapter mSalonAdapter;
    private int mMatchesCount;
    private String mMatchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_salon);

        ToggleButton citation_iss = (ToggleButton)findViewById(R.id.citation_issued_toggle);
        citation_iss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked){
                    startActivity(new Intent(SalonReviewActivity.this, InspectionNewActivity.class));
                } else {

                }
            }
        });
    }


    public void showNearest(View view) {
        Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
    }
}
