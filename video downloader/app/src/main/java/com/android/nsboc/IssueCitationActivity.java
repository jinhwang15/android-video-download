package com.android.nsboc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class IssueCitationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_citation);

        if (getIntent().hasExtra("licensee_id")) {
            EditText idField = (EditText) findViewById(R.id.licensee_id);
            idField.setText(getIntent().getStringExtra("licensee_id"));
        }

        ToggleButton citationToggle = (ToggleButton) findViewById(R.id.citation_to);
        citationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggle, boolean checked) {
                toggle.setChecked(true);
                Toast.makeText(IssueCitationActivity.this, "Will be clickable in Phase 2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadImage(View view) {
        startActivity(new Intent(this, SignatureActivity.class));
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
        onBackPressed();
    }

    public void addPhoto(View view) {
        Toast.makeText(this, "Will be clickable in Phase 2", Toast.LENGTH_SHORT).show();
    }
}
