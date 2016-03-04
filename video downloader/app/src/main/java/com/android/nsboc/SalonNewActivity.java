package com.android.nsboc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 12/13/2015.
 */

public class SalonNewActivity extends AppCompatActivity {

    private static final String SHOW_ALL = "";
    private SalonNewAdapter mSalonAdapter;
    private int mMatchesCount;
    private String mMatchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_salon);

        searchSalons(SHOW_ALL);

        EditText searchField = (EditText) findViewById(R.id.searchField);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                searchSalons(query);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (mMatchesCount == 1) {
                        openDetailView(mMatchId);
                    } else {
                        hideKeyboard();
                    }
                    return true;
                }
                return false;
            }
        });

        mSalonAdapter = new SalonNewAdapter(this);

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(mSalonAdapter);
        listView.requestFocus();
    }

    private void openDetailView(String salonId) {
        Intent intent = new Intent(this, SalonNewDetailActivity.class);
        intent.putExtra("salon_id", salonId);
        startActivity(intent);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void searchSalons(String query) {
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        ParseQuery<ParseObject> queryId = ParseQuery.getQuery("Salon");
        queryId.whereContains("license", query);

        ParseQuery<ParseObject> queryName = ParseQuery.getQuery("Salon");
        queryName.whereContains("name", query);

        List<ParseQuery<ParseObject>> queries = new ArrayList<>();
        queries.add(queryId);
        queries.add(queryName);

        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
        mainQuery.fromLocalDatastore();
        mainQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> salons, ParseException e) {
                if (e == null) {
                    mMatchesCount = salons.size();
                    if (mMatchesCount == 1) {
                        mMatchId = salons.get(0).getObjectId();
                    }
                    populateList(salons);
                } else {
                    Toast.makeText(SalonNewActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void populateList(List<ParseObject> salons) {
        mSalonAdapter.clear();
        mSalonAdapter.addAll(salons);

        findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    public void showNearest(View view) {
        Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
    }
}
