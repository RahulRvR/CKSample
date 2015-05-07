package com.rahulrvr.cksample;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.rahulrvr.cksample.retrofit.interfaces.LoginService;
import com.rahulrvr.cksample.retrofit.interfaces.pojo.PathChoice;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Copyright (c) 2015 Elsevier, Inc. All rights reserved.
 */
public class PathChoiceDialog extends DialogFragment implements View.OnClickListener, Callback<JsonObject> {

    PathChoice mSelectedPath;
    ProgressBar mProgressBar;
    RadioGroup radioGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_path_choice, container);
         radioGroup = (RadioGroup) view.findViewById(R.id.pathChoices);
        mProgressBar = (ProgressBar) view.findViewById(R.id.loader);
        ArrayList<PathChoice> list = getArguments().getParcelableArrayList("pathChoices");

        for(PathChoice pathChoice : list) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setText(pathChoice.getDescription());
            radioButton.setTag(pathChoice);
            radioButton.setOnClickListener(this);
            radioGroup.addView(radioButton);
        }

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        mSelectedPath = (PathChoice) v.getTag();
        mProgressBar.setVisibility(View.VISIBLE);
        radioGroup.setVisibility(View.GONE);
        new PathChoiceTask(this).execute(mSelectedPath.getPathChoiceNumber());
    }


    @Override
    public void success(JsonObject jsonObject, Response response) {
        Log.d("res", response.getReason());
        String state = jsonObject.getAsJsonPrimitive("state").toString();
        CKApplication.getInstance().setSelectedPath(mSelectedPath);
        ((TextView)getActivity().findViewById(R.id.loginStatus)).setText(mSelectedPath.getDescription());
        getDialog().dismiss();
    }

    @Override
    public void failure(RetrofitError error) {

    }

    public  class PathChoiceTask  extends AsyncTask<String,Void,Void> {

        Callback<JsonObject> mCallback;
        public PathChoiceTask(Callback<JsonObject> callback) {
            mCallback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            String pathNumber = params[0];
            LoginService service = CKApplication.getInstance().getRestAdapter().create(LoginService.class);
            service.setPathChoice(CKApplication.getInstance().getJSessionID(),pathNumber,"CANDIDATE_PATH",mCallback);
            return null;
        }


    }
}
