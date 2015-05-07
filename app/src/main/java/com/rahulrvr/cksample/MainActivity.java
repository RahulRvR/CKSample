package com.rahulrvr.cksample;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rahulrvr.cksample.tasks.LoadUrl;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    public static String homepageCookieURL = "https://ck2-dev.clinicalkey.com";
    public static String homepage = "https://ck2-dev.clinicalkey.com/info/sqfreetrial/";
    public static  String savedContent = "https://ck2-dev.clinicalkey.com/#!/saved-content";
    public static  String bookUrl = "https://ck2-dev.clinicalkey.com/#!/content/3-s2.0-B9781416054498000421";
    public static  String journalUrl = "https://ck2-dev.clinicalkey.com/#!/browse/journal/00219355/1-s2.0-S0021935514X71559";
    public static  String multiMedia_images = "https://ck2-dev.clinicalkey.com/#!/browse/multimedia/%7B%22start%22:0,%22facetquery%22:%5B%22contenttype:%5C%22IM%5C%22%22%5D%7D";
    public static  String multiMedia_videos = "https://ck2-dev.clinicalkey.com/#!/browse/multimedia/%7B%22start%22:0,%22facetquery%22:%5B%22contenttype:%5C%22VD%5C%22%22%5D%7D";
    public static  String patientEducation = "https://ck2-dev.clinicalkey.com/#!/content/patient_handout/5-s2.0-pe_ExitCare_DI_1800_Calorie_Diet_for_Diabetes_Meal_Planning_en";
    public static String drugMonograph="https://ck2-dev.clinicalkey.com/#!/content/drug_monograph/6-s2.0-2332";
    public static String medLine = "https://ck2-dev.clinicalkey.com/#!/content/2-s2.0-25651787";
    public static String patientHandout = "https://ck2-dev.clinicalkey.com/#!/content/patient_handout/5-s2.0-pe_ExitCare_DI_Diet_1_5_Gram_Low_Sodium_en";
    public static String clinicalTrial = "https://ck2-dev.clinicalkey.com/#!/content/clinical_trial/24-s2.0-NCT02349958";
    public static String medicalProcedure = "https://ck2-dev.clinicalkey.com/#!/content/medical_procedure/19-s2.0-mp_GS-046";
    public static String medicalTopic = "https://ck2-dev.clinicalkey.com/#!/content/medical_topic/21-s2.0-1014649";

    public static String guidesTechniques = "https://ck2-dev.clinicalkey.com/#!/content/journal/52-s2.0-mt_fis_196";

    WebView mWebView;
    ProgressBar mProgressBar;

    private Toolbar toolbar;
    TextView loginStatus;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        mWebView = (WebView) findViewById(R.id.webView);
        mProgressBar = (ProgressBar) findViewById(R.id.loader);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close);


            mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.contentTypes)));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(this);
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        loginStatus = (TextView) findViewById(R.id.loginStatus);
        mWebView.setWebViewClient(new WebClient(this));
        WebSettings ws = mWebView.getSettings();
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        ws.setJavaScriptEnabled(true);
        new LoadUrl(mWebView, homepage).execute();
        CookieManager.getInstance().setCookie(homepageCookieURL, CKApplication.getInstance().getJSessionID());

        String firstName = getIntent().getStringExtra("firstName");
        String lastName = getIntent().getStringExtra("lastName");
        String name = "Logged In As ";
        if(firstName != null && lastName !=null ) {
            name += firstName + " " + lastName;
        } else {
            name += "Anonymous";
        }
        if(getIntent().getBooleanExtra("showPath",false)) {
            showPathSelection();
        }

        loginStatus.setText(name);

mDrawerToggle.syncState();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(CKApplication.getInstance().getSelectedPath() != null) {
            loginStatus.setText(CKApplication.getInstance().getSelectedPath().getDescription());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void showPathSelection() {
        DialogFragment dialog = new PathChoiceDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("pathChoices",CKApplication.getInstance().getPathChoiceList());
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), "NoticeDialogFragment");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        String url;
        switch (id) {

            case R.id.action_org:
                 showPathSelection();
                return  true;
            case R.id.action_saved_content:
                url =savedContent;
                break;
            case R.id.action_books:
                url =bookUrl;
                break;
            case R.id.action_journals :
                url =journalUrl;
                break;
            case R.id.action_multiMedia_images :
                url =multiMedia_images;
                break;
            case R.id.action_multiMedia_videos :
                url =multiMedia_videos;
                break;
            case R.id.action_patientEdu :
                url =patientEducation;
                break;
            case R.id.action_drug_monograph:
                url = drugMonograph;
                break;
            case R.id.action_medLine:
                url = medLine;
                break;
            case R.id.action_patientHandout:
                url = patientHandout;
                break;
            case R.id.action_clinicalTrial:
                url = clinicalTrial;
                break;
            case R.id.action_medicalProcedure:
                url = medicalProcedure;
                break;
            case R.id.action_medicalTopic:
                url = medicalTopic;
                break;
            case R.id.action_guidesTechniques :
                url = guidesTechniques;
                break;
            default:
                url = homepage;
                break;
        }


        new LoadUrl(mWebView,url).execute();

        return true;
    }
}
