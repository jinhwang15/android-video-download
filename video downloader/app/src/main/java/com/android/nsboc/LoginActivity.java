package com.android.nsboc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    private SharedPreferences mSharedPrefs;

    private RotateAnimation mLoadingAimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailEditText = (EditText) findViewById(R.id.email_edittext);
        mPasswordEditText = (EditText) findViewById(R.id.password_edittext);

        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        setCustomTypeFace();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSharedPrefs.edit()
                .putString("email", mEmailEditText.getText().toString().trim())
                .putString("password", mPasswordEditText.getText().toString().trim())
                .apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
//todo uncomment        mEmailEditText.setText(mSharedPrefs.getString("email", ""));
//        mPasswordEditText.setText(mSharedPrefs.getString("password", ""));

        if (!mEmailEditText.getText().toString().isEmpty()) {
            mEmailEditText.requestFocus();
            mEmailEditText.setSelection(mEmailEditText.length());
        }
        if (!mPasswordEditText.getText().toString().isEmpty()) {
            mPasswordEditText.requestFocus();
            mPasswordEditText.setSelection(mPasswordEditText.length());
        }
    }

    private void setCustomTypeFace() {
        ImageView sealImageView = (ImageView) findViewById(R.id.seal_icon);
        sealImageView.setMinimumHeight(sealImageView.getWidth());

        Typeface nsbocTypeface = Typeface.createFromAsset(getAssets(), "fonts/Heuristica-Regular.otf");
        TextView headerTextView = (TextView)findViewById(R.id.header_textview);
        headerTextView.setTypeface(nsbocTypeface);
    }

    public void login(View view) {
        hideKeyboard();

        if (containsEmptyFields()) return;



        String username = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();

        showLoadingAnimation();

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                mLoadingAimation.setRepeatCount(0);
                if (user != null) {
                    // Hooray! The user is logged in.
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    showErrorToast(e.getCode());
                }
            }
        });
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showLoadingAnimation() {
        View sealImageView = findViewById(R.id.seal_icon);

        mLoadingAimation = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mLoadingAimation.setRepeatCount(-1);
        mLoadingAimation.setDuration(1000);
        sealImageView.startAnimation(mLoadingAimation);
    }

    private boolean containsEmptyFields() {
        EditText emailEditText = (EditText) findViewById(R.id.email_edittext);
        EditText passwordEditText = (EditText) findViewById(R.id.password_edittext);

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            if (password.isEmpty()) {
                passwordEditText.setError("Password is required");
                passwordEditText.requestFocus();
            }
            if (email.isEmpty()) {
                emailEditText.setError("Username is required");
                emailEditText.requestFocus();
            }
            return true;
        }
        return false;
    }

    private void showErrorToast(int errorCode) {
        String toastMessage;
        switch (errorCode) {
            case 101:
                toastMessage = "Username or password is not correct";
                break;
            case 125:
                toastMessage = "Enter valid email address";
                break;
            default:
                toastMessage = "Invalid login parameters";
        }
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
    }
}
