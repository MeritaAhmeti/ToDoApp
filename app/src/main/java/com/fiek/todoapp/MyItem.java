package com.fiek.todoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyItem implements ClusterItem {
    private  LatLng position;
    private String title;
    private  String snippet;

    public MyItem(LatLng position) {
        this.position = position;
    }

    public MyItem(double lat,double lang, String title, String snippet) {
        this.position = new LatLng(lat,lang);
        this.title = title;
        this.snippet = snippet;
    }

    @NonNull
    @Override
    public LatLng getPosition()
    {
        return position;
    }

    @Nullable
    @Override
    public String getTitle()
    {
        return title;
    }

    @Nullable
    @Override
    public String getSnippet()
    {
        return snippet;
    }
}