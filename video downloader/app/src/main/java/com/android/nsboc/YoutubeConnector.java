package com.android.nsboc;

/**
 * Created by Administrator on 2/21/2016.
 */
import android.content.Context;
import android.util.Log;


import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;

import java.io.IOException;


public class YoutubeConnector {
    private YouTube youtube;
    private YouTube.Search.List query;

    // Your developer key goes here
    public static final String KEY
            = "AIzaSQZZQWQQWMGziK9H_qRxz8g-V6eDL3QW_Us";

    public YoutubeConnector(Context context) {
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {}
        }).setApplicationName(content.getString(R.string.app_name)).build();

        try{
            query = youtube.search().list("id,snippet");
            query.setKey(KEY);
            query.setType("video");
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        }catch(IOException e){
            Log.d("YC", "Could not initialize: " + e);
        }
    }
}