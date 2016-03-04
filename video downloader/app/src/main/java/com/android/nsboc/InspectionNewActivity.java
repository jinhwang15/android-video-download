package com.android.nsboc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Button;
import android.widget.CompoundButton;

public class InspectionNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_new);

        Toast.makeText(this, "Video Downloader", Toast.LENGTH_SHORT).show();

      ToggleButton proceedToggle = (ToggleButton) findViewById(R.id.Closed);
        proceedToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    findViewById(R.id.spinerIsSalon).setEnabled(false);
                    findViewById(R.id.salon_id).setEnabled(false);
                    findViewById(R.id.date).setEnabled(false);
                    findViewById(R.id.citation_id).setEnabled(false);
                    findViewById(R.id.spinerIsLicensing).setEnabled(false);
                    findViewById(R.id.violation_desc_id).setEnabled(false);
                    findViewById(R.id.violation_comm_id).setEnabled(false);
                    findViewById(R.id.violation_type_spinner).setEnabled(false);
                    findViewById(R.id.offence_to_individual).setEnabled(false);
                    findViewById(R.id.requested_amount).setEnabled(false);
                    findViewById(R.id.CommentID).setEnabled(false);
                    findViewById(R.id.violation_image_picker).setEnabled(false);
                    findViewById(R.id.email).setEnabled(false);
                    findViewById(R.id.new_email).setEnabled(false);
                    findViewById(R.id.salon_signature).setEnabled(false);
                    findViewById(R.id.inspector_signature).setEnabled(false);
                }
                else{
                    findViewById(R.id.spinerIsSalon).setEnabled(true);
                    findViewById(R.id.salon_id).setEnabled(true);
                    findViewById(R.id.date).setEnabled(true);
                    findViewById(R.id.citation_id).setEnabled(true);
                    findViewById(R.id.spinerIsLicensing).setEnabled(true);
                    findViewById(R.id.violation_desc_id).setEnabled(true);
                    findViewById(R.id.violation_comm_id).setEnabled(true);
                    findViewById(R.id.violation_type_spinner).setEnabled(true);
                    findViewById(R.id.offence_to_individual).setEnabled(true);
                    findViewById(R.id.requested_amount).setEnabled(true);
                    findViewById(R.id.CommentID).setEnabled(true);
                    findViewById(R.id.violation_image_picker).setEnabled(true);
                    findViewById(R.id.email).setEnabled(true);
                    findViewById(R.id.new_email).setEnabled(true);
                    findViewById(R.id.salon_signature).setEnabled(true);
                    findViewById(R.id.inspector_signature).setEnabled(true);
                }

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    public void submit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void uploadImage(View view) {
        startActivity(new Intent(this, SignatureActivity.class));
    }

    public void addPhoto(View view) {
        Toast.makeText(this, "Will be clickable in Phase 2", Toast.LENGTH_SHORT).show();
    }
}
