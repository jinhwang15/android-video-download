package com.android.nsboc;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_FIND_SALON = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
//        toolbar2.setTitle("Issue Citation");

        new PinAllSalonsTask().execute();
        new PinAllLicenseesTask().execute();
    }

    public void issueCitation(View view) {
        startActivity(new Intent(this, InspectionNewActivity.class));//IssueCitationActivity
    }

    private class PinAllSalonsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                ParseObject.unpinAll("Salon");
                ParseObject.pinAll(listAllSalons());
            } catch (ParseException ignored) {
            }
            return null;
        }
    }

    private List<ParseObject> listAllSalons() throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Salon");
        return query.find();
    }


    private class PinAllLicenseesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                ParseObject.unpinAll("Licensee");
                ParseObject.pinAll(listAllLicensees());
            } catch (ParseException ignored) {
            }
            return null;
        }
    }

    private List<ParseObject> listAllLicensees() throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Licensee");
        return query.find();
    }

    public void onFormSelected(View view) {
        switch (view.getId()) {
            case R.id.routine:{
                Intent intent = new Intent(this, SalonFindActivity.class);
                intent.putExtra("is_routine", "YES");
                startActivity(intent);
                return;
            }
            case R.id.new_salon:{
                Intent intent = new Intent(this, SalonFindActivity.class);
                intent.putExtra("is_routine", "NO");
                startActivity(intent);
                return;
            }
//            case R.id.new_salon:
//                startActivityForResult(new Intent(this, SalonNewActivity.class), REQUEST_FIND_SALON);
//                return;
            case R.id.review:
                startActivityForResult(new Intent(this, SalonReviewActivity.class), REQUEST_FIND_SALON);
                return;
            case R.id.investigation:
                startActivityForResult(new Intent(this, SalonInvestigationActivity.class), REQUEST_FIND_SALON);
                return;
            default:
                startActivityForResult(new Intent(this, SalonFindActivity.class), REQUEST_FIND_SALON);
                return;
        }
    }


}
