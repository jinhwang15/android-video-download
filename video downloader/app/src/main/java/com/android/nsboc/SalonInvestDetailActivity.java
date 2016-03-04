package com.android.nsboc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Administrator on 12/14/2015.
 */

public class SalonInvestDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_invest_detail);

        final String salonId = getIntent().getStringExtra("salon_id");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Salon");
        query.fromLocalDatastore();
        query.getInBackground(salonId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject salon, ParseException e) {
                TextView nameView = (TextView) findViewById(R.id.name);
                nameView.setText(salon.getString("name"));

                TextView addressView = (TextView) findViewById(R.id.address);
                addressView.setText(salon.getString("address"));

                Button licenseView = (Button) findViewById(R.id.licenseButton);
                licenseView.setText(formatLicenseText(salon.getString("license")));
                licenseView.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent =
                                new Intent(SalonInvestDetailActivity.this, LicenseDetailActivity.class);
                        intent.putExtra("salon_id", salon.getObjectId());
                        startActivity(intent);
                    }
                });

                ImageButton contactsButton = (ImageButton) findViewById(R.id.contacts_button);
                contactsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SalonInvestDetailActivity.this, StoreContactsEditActivity.class);
                        intent.putExtra("salon_id", salon.getObjectId());
                        startActivity(intent);

                    }
                });
            }
        });

        ParseQuery<ParseObject> licenseeQuery = ParseQuery.getQuery("Licensee");
        licenseeQuery.fromLocalDatastore();
        // TODO: licenseeQuery.whereEqualTo("salon", salon);
        licenseeQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> licensees, ParseException e) {
                if (e == null) {
                    addAll(licensees);
                }
            }
        });

        ToggleButton proceedToggle = (ToggleButton) findViewById(R.id.proceed);
        proceedToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    findViewById(R.id.compliance_button).setEnabled(true);
                    findViewById(R.id.update).setEnabled(false);

                    findViewById(R.id.inspector_signature).setVisibility(View.GONE);
                    findViewById(R.id.salon_signature).setVisibility(View.GONE);
                    findViewById(R.id.refuse_sign_container).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.compliance_button).setEnabled(false);
                    findViewById(R.id.update).setEnabled(true);

                    findViewById(R.id.inspector_signature).setVisibility(View.VISIBLE);
                    findViewById(R.id.salon_signature).setVisibility(View.VISIBLE);
                    findViewById(R.id.refuse_sign_container).setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.linearLayout_focus).requestFocus();
    }

    private void addAll(List<ParseObject> licensees) {
        LinearLayout licenseeContainer = (LinearLayout) findViewById(R.id.licensee_container);

        for (ParseObject licensee : licensees) {
            View rootView = getLayoutInflater().inflate(R.layout.licensee_list_item, null);

            ((TextView) rootView.findViewById(R.id.name)).setText(licensee.getString("name"));
            ((TextView) rootView.findViewById(R.id.licenseeId)).setText(licensee.getString("licenseeId"));
            ((ToggleButton) rootView.findViewById(R.id.inCharge)).setChecked(licensee.getBoolean("inCharge"));

            licenseeContainer.addView(rootView);
        }
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

    public void navigateToMainScreen(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void showLicensee(View view) {
        String licenseeId = ((Button)view).getText().toString();
        if (licenseeId.contains("/")) {
            licenseeId = "A-1000";
        }
        Intent intent = new Intent(this, LicenseeDetailActivity.class);
        intent.putExtra("licensee_id", licenseeId);
        startActivity(intent);
    }

    public void removeLicensee(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to remove Jane Doe from this salon?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SalonInvestDetailActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getWindow().setLayout(
                convertDpToPx(500),
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private int convertDpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return ((int) (dp * density));
    }

    public void sanitationCompliance(View view) {
        Intent i = new Intent(this, ComplianceStartActivity.class);
        i.putExtra("flag", "invest");
        startActivity(i);//ComplianceStartActivity
    }

    public void addLicensee(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Licensee");

        /*
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(convertDpToPx(16), 0, convertDpToPx(16), 0);
        */

        EditText idField = new EditText(this);
        idField.setHint("Licensee's ID");
        idField.setHeight(convertDpToPx(56));
        idField.setPadding(convertDpToPx(24), 0, convertDpToPx(24), 0);
        builder.setView(idField);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SalonInvestDetailActivity.this, "Database access needed", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        showVirtualKeyboard();

        dialog.getWindow().setLayout(
                convertDpToPx(500),
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void showVirtualKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, 0);
    }

    public void expiredLicensee(View view) {
        TextView idView = (TextView) view.getRootView().findViewById(R.id.licenseeId);
        String licenseeId = idView.getText().toString();
        Intent intent = new Intent(this, IssueCitationActivity.class);
        intent.putExtra("licensee_id", licenseeId);
        startActivity(intent);
    }

    public void newInspection(View view) {
        startActivity(new Intent(this, InspectionNewActivity.class));
    }

    public void uploadImage(View view) {
        startActivity(new Intent(this, SignatureActivity.class));
    }
}
