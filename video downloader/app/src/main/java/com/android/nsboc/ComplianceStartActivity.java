package com.android.nsboc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class ComplianceStartActivity extends AppCompatActivity {

    public String flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compliance_start);

        findViewById(R.id.linearLayout_focus).requestFocus();

        Intent getVal = getIntent();
        flag = getVal.getStringExtra("flag");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    public void goBack(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to dismiss photo?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ComplianceStartActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void uploadImage(View view) {
        startActivity(new Intent(this, SignatureActivity.class));
    }

    public void addPhoto(View view) {
//        if (flag.equalsIgnoreCase("invest")){
//            startActivity(new Intent(this, ComplianceGridActivity.class));
//        } else {
            startActivity(new Intent(this, ComplianceFinishActivity.class));
//        }
    }

    public void submit(View view) {
        startActivity(new Intent(this, ComplianceSummaryActivity.class));
    }
}
