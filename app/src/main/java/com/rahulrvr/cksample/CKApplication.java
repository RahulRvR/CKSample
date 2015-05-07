package com.rahulrvr.cksample;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.rahulrvr.cksample.retrofit.interfaces.pojo.PathChoice;

import java.util.ArrayList;

import retrofit.RestAdapter;

/**
 * Copyright (c) 2015 Elsevier, Inc. All rights reserved.
 */
public class CKApplication extends Application implements Application.ActivityLifecycleCallbacks{


    public static final String END_POINT = "https://ck2-dev.clinicalkey.com/";

    RestAdapter mRestAdapter;



    ArrayList<PathChoice> mPathChoiceList;

    public PathChoice getSelectedPath() {
        return mSelectedPath;
    }

    public void setSelectedPath(PathChoice mSelectedPath) {
        this.mSelectedPath = mSelectedPath;
    }

    PathChoice mSelectedPath;

    String mJSessionID;

    private  static CKApplication singleton;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .build();
    }

    public RestAdapter getRestAdapter() {
        return mRestAdapter;
    }

    public static CKApplication getInstance(){
        return singleton;
    }

    public String getJSessionID() {
        return mJSessionID;
    }

    public void setJSessionID(String mJSessionID) {
        this.mJSessionID = mJSessionID;
    }

    public ArrayList<PathChoice> getPathChoiceList() {
        return mPathChoiceList;
    }

    public void setPathChoiceList(ArrayList<PathChoice> pathChoiceList) {
        this.mPathChoiceList = pathChoiceList;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
