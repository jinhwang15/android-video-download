package com.android.nsboc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class ComposeActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_FROM_GALLERY = 2;
    static final int REQUEST_SIGNATURE = 3;

    private ComposeFragment mFragment;

    private int mImageSelectorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        setStatusBarColor();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);

        if (savedInstanceState == null) {
            // Create the compose fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(ComposeFragment.ARG_ITEM_ID,
                    getIntent().getIntExtra(ComposeFragment.ARG_ITEM_ID, -1));
            mFragment = new ComposeFragment();
            mFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.form_detail_container, mFragment)
                    .commit();
        }
    }

    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }
    }

    @SuppressWarnings("unused")
    public void uploadImage(View view) {
        mImageSelectorId = view.getId();

        TextView pickerView = (TextView) view;
        String pickerText = pickerView.getText().toString();
        if (pickerText.contains("Signature")) {
            Intent intent = new Intent(this, SignatureActivity.class);
            intent.putExtra("title", pickerText);
            startActivityForResult(intent, REQUEST_SIGNATURE);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose image from...");
            builder.setSingleChoiceItems(
                    new String[]{"Gallery", "Take photo"},
                    -1,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            switch (position) {
                                case 0:
                                    dispatchGalleryPickerIntent();
                                    dialog.cancel();
                                    break;
                                case 1:
                                    dispatchTakePictureIntent();
                                    dialog.cancel();
                                    break;
                            }
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "Install camera app to take a picture", Toast.LENGTH_LONG).show();
        }
    }

    private void dispatchGalleryPickerIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_FROM_GALLERY);
        } else {
            Toast.makeText(this, "Install gallery app to choose an image", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            data.putExtra("image_selector_id", mImageSelectorId);
            mFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
