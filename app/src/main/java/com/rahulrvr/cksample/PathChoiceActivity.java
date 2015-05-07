package com.rahulrvr.cksample;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.JsonObject;
import com.rahulrvr.cksample.retrofit.interfaces.LoginService;
import com.rahulrvr.cksample.retrofit.interfaces.pojo.PathChoice;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class PathChoiceActivity extends Activity implements View.OnClickListener, Callback<JsonObject>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_choice);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.pathChoices);
        ArrayList<PathChoice> list = getIntent().getParcelableArrayListExtra("pathChoices");

        for(PathChoice pathChoice : list) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(pathChoice.getDescription());
            radioButton.setTag(pathChoice);
            radioButton.setOnClickListener(this);
            radioGroup.addView(radioButton);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_path_choice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        PathChoice choice = (PathChoice) v.getTag();
        new PathChoiceTask(this).execute(choice.getPathChoiceNumber());
    }


    @Override
    public void success(JsonObject jsonObject, Response response) {
        Log.d("res", response.getReason());
        String state = jsonObject.getAsJsonPrimitive("state").toString();
        if(state.contains("COMPLETED")) {
            startActivity(new Intent(this,MainActivity.class));
        } else {
            startActivity(new Intent(this,LoginActivity.class));
        }
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
