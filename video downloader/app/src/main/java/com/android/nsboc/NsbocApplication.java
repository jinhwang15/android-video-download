package com.android.nsboc;

import android.app.Application;

import com.parse.Parse;

public class NsbocApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "R1nyeSUwE5EXFkC2xz2FaX2fSLBHb07SDmbdEWOs", "PwuQhZnt8eTJq1lhizPVnWCoBE1Aw4zADHBgP6Xq");

    }
}
