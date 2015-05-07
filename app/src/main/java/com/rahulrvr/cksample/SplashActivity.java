package com.rahulrvr.cksample;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;
import com.rahulrvr.cksample.retrofit.interfaces.LoginService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SplashActivity extends ActionBarActivity implements Callback<JsonObject>{

    ProgressBar mProgressBar;
   // TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mProgressBar = (ProgressBar) findViewById(R.id.login_progress);
      //  mTextView = (TextView) findViewById(R.id.auth_status);
        new AuthenticationTask(this).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
    public void success(JsonObject jsonObject, Response response) {
        String jSessionId = response.getHeaders().get(9).getValue().split(";")[0];
        CKApplication.getInstance().setJSessionID(jSessionId);

        JsonObject view = jsonObject.getAsJsonObject("view");
        if(view != null) {
            String type = view.getAsJsonPrimitive("type").toString();
//            if(type.contains("PATH_CHOICE")) {
//                JsonArray pathChoices = view.getAsJsonArray("pathChoices");
//                Type listType = new TypeToken<List<PathChoice>>(){}.getType();
//                ArrayList<PathChoice> pathChoiceList = new Gson().fromJson(pathChoices.toString(), listType);
//                CKApplication.getInstance().setPathChoiceList(pathChoiceList);
//                DialogFragment dialog = new PathChoiceDialog();
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("pathChoices",pathChoiceList);
//                dialog.setArguments(bundle);
//                dialog.show(getFragmentManager(), "NoticeDialogFragment");
//            } else {


            new android.os.Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }, 600);

            //}
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void failure(RetrofitError error) {
        mProgressBar.setVisibility(View.GONE);

    }

    public  class AuthenticationTask  extends AsyncTask<Void,Void,Void> {

        Callback<JsonObject> mCallback;
        public AuthenticationTask(Callback<JsonObject> callback) {
            mCallback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
           // mTextView.setText("Checking IP......");
            LoginService service = CKApplication.getInstance().getRestAdapter().create(LoginService.class);
            service.getIP(mCallback);
            return null;
        }


    }
}
