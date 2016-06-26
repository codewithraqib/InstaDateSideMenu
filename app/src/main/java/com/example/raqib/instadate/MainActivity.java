package com.example.raqib.instadate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  SlidingFragment.AppData {

    public List<StackSite> news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (isNetworkAvailable()) {
            Log.i("StackSites", "starting download Task");
            SitesDownloadTask download = new SitesDownloadTask();
            download.execute();
        } else {
            displayNews();
        }

    }


    //HELPER METHOD TO DETERMINE WHETHER NETWORK IS AVAILABLE OR NOT
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //Updated Way
    private class SitesDownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            //Download the file
            try {
                Downloader.DownloadFromUrl("https://dl.dropboxusercontent.com/u/5724095/XmlParseExample/stacksites.xml", openFileOutput("StackSites.xml", Context.MODE_PRIVATE));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //setup our Adapter and set it to the ListView.
//            mAdapter = new SitesAdapter(MainActivity.this, -1, SitesXmlPullParser.getStackSitesFromFile(MainActivity.this));
//            sitesList.setAdapter(mAdapter);
//            Log.i("StackSites", "adapter size = "+ mAdapter.getCount());
            displayNews();

        }
    }

    private void displayNews() {

        ScreenSlidePagerAdapter screenSlidePagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());

        VerticalViewPager verticalViewPager = (VerticalViewPager) findViewById(R.id.pager123);

        verticalViewPager.setAdapter(screenSlidePagerAdapter);

    }

    @Override
    public JSONObject getData(int position) {

        JSONObject jsonObject = new JSONObject();
        try {
            news = SitesXmlPullParser.getStackSitesFromFile(MainActivity.this);

            String heading = news.get(position).getName();
            String body = news.get(position).getAbout();
            String imageUrl = news.get(position).getImgUrl();
            String link = news.get(position).getLink();

            jsonObject.put("heading", heading);
            jsonObject.put("body", body);
            jsonObject.put("image_url", imageUrl);
            jsonObject.put("link", link);

        } catch (Exception ignored) {
        }
        return jsonObject;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void UserCustomization(MenuItem item) {
        startActivity(new Intent(MainActivity.this, CustomizationActivity.class));
    }

    public void RegisterUser(MenuItem item) {
        startActivity(new Intent(MainActivity.this, Login.class));
    }

    public void about(MenuItem item) {
        startActivity(new Intent(MainActivity.this, AboutInstaDate.class));
    }

    public void closeApp(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do You want to exit :( ?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void detailedNews(View view) {
        startActivity(new Intent(getApplicationContext(), DetailedNews.class));
    }

    public void shareWith(View view) {

        String shareBody = SlidingFragment.newsBody();

        Intent intent = new Intent();
//        intent.putExtra(shareBody, 1);

        //TO CHECK WHETHER IS ANY APP IN PHONE THAT CAN RECEIVE THE INTENT
        PackageManager packageManager = getPackageManager();

        //GET A LIST OF ACTIVITIES IF PRESENT WHICH CAN HANDLE THE PARTICULAR INTENT
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);

        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            startActivity(intent);
        }
    }
}