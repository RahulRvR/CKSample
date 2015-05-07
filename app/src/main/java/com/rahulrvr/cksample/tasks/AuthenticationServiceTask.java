package com.rahulrvr.cksample.tasks;

import android.os.AsyncTask;

import com.google.gson.JsonObject;

import retrofit.Callback;

/**
 * Copyright (c) 2015 Elsevier, Inc. All rights reserved.
 */
public class AuthenticationServiceTask extends AsyncTask<String,Void,Void> {

    Callback<JsonObject> mCallback;
    public AuthenticationServiceTask(Callback<JsonObject> callback) {
        mCallback = callback;
    }

    @Override
    protected Void doInBackground(String... params) {
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
