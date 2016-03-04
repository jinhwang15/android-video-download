package com.android.nsboc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LicenseeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licensee_detail);

        final String licenseeId = getIntent().getStringExtra("licensee_id");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Licensee");
        query.fromLocalDatastore();
        query.whereEqualTo("licenseeId", licenseeId);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject licensee, ParseException e) {
                TextView nameView = (TextView) findViewById(R.id.name);
                nameView.setText(licensee.getString("name"));
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
}
