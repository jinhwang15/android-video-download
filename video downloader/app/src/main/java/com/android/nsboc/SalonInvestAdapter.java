package com.android.nsboc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by Administrator on 12/14/2015.
 */

public class SalonInvestAdapter extends ArrayAdapter<ParseObject> {

    public SalonInvestAdapter(Context context) {
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
                Intent intent = new Intent(activity, SalonInvestDetailActivity.class);
                intent.putExtra("salon_id", salon.getObjectId());
                activity.startActivity(intent);
            }
        });

        ParseFile image = salon.getParseFile("image");
        if (image != null) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.salon_image);
            Glide.with(getContext()).load(image.getUrl()).fitCenter().centerCrop().into(imageView);
        }

        return rootView;
    }
}
