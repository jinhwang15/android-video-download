package com.android.nsboc;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.nsboc.data.FormContent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ComposeFragment extends Fragment {

    private LinearLayout mAnotherComplianceLayout;

    private int mSectionsCount = 1;

    public static final String ARG_ITEM_ID = "item_id";

    private FormContent.FormItem mItem;

    private View mRootView;

    public ComposeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = FormContent.ITEM_MAP.get(getArguments().getInt(ARG_ITEM_ID) + "");
        }
    }

    public CompoundButton.OnCheckedChangeListener getNextToggleListener(final LinearLayout complianceLayout) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                if (isChecked) {

                    mAnotherComplianceLayout = (LinearLayout) View.inflate(
                            getActivity(), R.layout.compliance_layout, null);

                    TextView imagePickerLabel = (TextView) mAnotherComplianceLayout.findViewById(R.id.violation_image_picker);
                    imagePickerLabel.setText(imagePickerLabel.getText().toString().replace(" " + mSectionsCount, " " + (mSectionsCount + 1)));

                    EditText commentEditText = (EditText) mAnotherComplianceLayout.findViewById(R.id.comment_edittext);
                    commentEditText.setHint(commentEditText.getText().toString().replace(" " + mSectionsCount, " " + (mSectionsCount + 1)));

                    TextView isAnotherLabel = (TextView) mAnotherComplianceLayout.findViewById(R.id.is_another_label);
                    isAnotherLabel.setText(isAnotherLabel.getText().toString().replace((mSectionsCount + 1) + "?", (mSectionsCount + 2) + "?"));

                    ToggleButton anotherToggle = (ToggleButton) mAnotherComplianceLayout.findViewById(R.id.compliance_toggle);
                    anotherToggle.setOnCheckedChangeListener(getNextToggleListener(mAnotherComplianceLayout));

                    complianceLayout.addView(mAnotherComplianceLayout);
                    mSectionsCount++;
                } else {
                    mSectionsCount--;
                    mAnotherComplianceLayout.setVisibility(View.GONE);
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mItem != null) {
            switch (mItem.id + "") {
                case "1":
                    mRootView = inflater.inflate(R.layout.form_unlicensed, container, false);
                    break;
                case "2":
                    mRootView = inflater.inflate(R.layout.form_salon, container, false);
                    break;
            }
        }

        showCurrentDateInEditText();

        ToggleButton complianceToggle = (ToggleButton) mRootView.findViewById(R.id.compliance_toggle);
        if (complianceToggle != null) {
            final LinearLayout complianceContainer = (LinearLayout) mRootView.findViewById(R.id.compliance_container);

            complianceToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        complianceContainer.setVisibility(View.VISIBLE);

                        final LinearLayout complianceLayout = (LinearLayout) View.inflate(
                                getActivity(), R.layout.compliance_layout, null);

                        Spinner violationTypeSpinner = (Spinner) complianceLayout.findViewById(R.id.violation_type_spinner);
                        violationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                switch (position) {
                                    case 0:
                                        complianceLayout.findViewById(R.id.type_citation_container).setVisibility(View.VISIBLE);
                                        break;
                                    case 1:
                                        complianceLayout.findViewById(R.id.type_citation_container).setVisibility(View.GONE);
                                        break;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                        ToggleButton anotherToggle = (ToggleButton) complianceLayout.findViewById(R.id.compliance_toggle);
                        anotherToggle.setOnCheckedChangeListener(getNextToggleListener(complianceLayout));

                        complianceContainer.addView(complianceLayout);
                    } else {
                        complianceContainer.setVisibility(View.GONE);
                    }
                }
            });
        }

        ToggleButton emailToggle = (ToggleButton) mRootView.findViewById(R.id.emailTo_toggle);
        if (emailToggle != null) {
            final LinearLayout emailLayout = (LinearLayout) mRootView.findViewById(R.id.emailTo_layout);

            emailToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        emailLayout.setVisibility(View.VISIBLE);
                    } else {
                        emailLayout.setVisibility(View.GONE);
                    }
                }
            });
        }

        final TextView noticeTextView = (TextView) mRootView.findViewById(R.id.notice_textview);
        if (noticeTextView != null) {
            noticeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (noticeTextView.getCurrentTextColor() == Color.BLACK) {
                        noticeTextView.setText(R.string.civil_citation_header);
                        noticeTextView.setTextColor(Color.parseColor("#17405e"));
                        noticeTextView.setText(Html.fromHtml("<u>" + noticeTextView.getText() + "</u>"));
                        mRootView.findViewById(R.id.civil_citation).requestFocus();
                    } else {
                        noticeTextView.setText(R.string.civil_citation_notice);
                        noticeTextView.setMaxLines(Integer.MAX_VALUE);
                        noticeTextView.setTextColor(Color.BLACK);
                        noticeTextView.setText(noticeTextView.getText().toString().replace("<u>", "").replace("</u>", ""));
                    }
                }
            });
        }

        Spinner stateSpinner = (Spinner) mRootView.findViewById(R.id.state);
        if (stateSpinner != null) {
            stateSpinner.setSelection(27);
        }

        return mRootView;
    }

    private void showCurrentDateInEditText() {
        EditText dateField = (EditText) mRootView.findViewById(R.id.date);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateField.setText(formatter.format(new Date()));
        dateField.setSelection(dateField.length());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ComposeActivity.RESULT_OK) {
            if (requestCode == ComposeActivity.REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                int selectorId = data.getIntExtra("image_selector_id", -1);
                if (selectorId != -1) {
                    TextView selectorLabel = (TextView) mRootView.findViewById(selectorId);
                    if (selectorLabel != null) {
                        Drawable imagePreview = new BitmapDrawable(getResources(), imageBitmap);
                        selectorLabel.setCompoundDrawablesWithIntrinsicBounds(imagePreview, null, null, null);
                    }
                }

            } else if (requestCode == ComposeActivity.REQUEST_FROM_GALLERY
                    || requestCode == ComposeActivity.REQUEST_SIGNATURE) {
                Uri imageUri = data.getData();
                String imagePath = getPath(imageUri);
                Drawable imagePreview = Drawable.createFromPath(imagePath);

                int selectorId = data.getIntExtra("image_selector_id", -1);
                if (selectorId != -1) {
                    TextView selectorLabel = (TextView) mRootView.findViewById(selectorId);
                    if (selectorLabel != null) {
                        selectorLabel.setCompoundDrawablesWithIntrinsicBounds(imagePreview, null, null, null);
                    }
                }
            }
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }
}
