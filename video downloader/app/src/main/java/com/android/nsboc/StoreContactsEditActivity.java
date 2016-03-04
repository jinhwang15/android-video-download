package com.android.nsboc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class StoreContactsEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_edit_contacts);

        final String salonId = getIntent().getStringExtra("salon_id");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Salon");
        query.fromLocalDatastore();
        query.getInBackground(salonId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject salon, ParseException e) {
                TextView nameView = (TextView) findViewById(R.id.name);
                nameView.setText(salon.getString("name"));

                TextView addressView = (TextView) findViewById(R.id.address);
                addressView.setText(salon.getString("address"));

                Button licenseButton = (Button) findViewById(R.id.licenseButton);
                licenseButton.setText(formatLicenseText(salon.getString("license")));
                licenseButton.setClickable(false);
            }
        });

        EditText phoneField = (EditText) findViewById(R.id.phone_field);
        phoneField.setSelection(phoneField.length());
    }

    private Spanned formatLicenseText(String license) {
        return Html.fromHtml(String.format("License %s " +
                        "<big><font color=\"#00AD4C\">(current)</font></big><br>" +
                        "Expires on 9/30/2015",
                license));
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
}
