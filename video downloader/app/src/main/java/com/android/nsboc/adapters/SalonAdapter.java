package com.android.nsboc.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nsboc.R;
import com.android.nsboc.SalonDetailActivity;
import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseObject;

public class SalonAdapter extends ArrayAdapter<ParseObject> {

    public String strIs_Routine; //KKK

    public SalonAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View rootView = inflater.inflate(R.layout.salon_list_item, null);

        final ParseObject salon = getItem(position);
//        ((TextView) rootView.findViewById(R.id.name)).setText(salon.getString("name"));
//        ((TextView) rootView.findViewById(R.id.license)).setText(salon.getString("license"));
//        ((TextView) rootView.findViewById(R.id.address)).setText(salon.getString("address"));
//        ((TextView) rootView.findViewById(R.id.phone)).setText(salon.getString("phone"));

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) getContext();
                Intent intent = new Intent(activity, SalonDetailActivity.class);

                intent.putExtra("salon_id", salon.getObjectId());
                intent.putExtra("is_routine", strIs_Routine); //KKK

                activity.startActivity(intent);
            }
        });

        ParseFile image = salon.getParseFile("image");
        if (image != null) {
  //          ImageView imageView = (ImageView) rootView.findViewById(id.salon_image);
  //          Glide.with(getContext()).load(image.getUrl()).fitCenter().centerCrop().into(imageView);
        }

        return rootView;
    }
}
