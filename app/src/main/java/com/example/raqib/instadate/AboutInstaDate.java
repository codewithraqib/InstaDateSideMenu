package com.example.raqib.instadate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class AboutInstaDate extends AppCompatActivity {
    private static String email = "raqib850@gmail.com";
    private static String twitter = "http://www.twitter.com/ra_qib";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_insta_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] addresses = {email};
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Information Request");
                intent.putExtra(Intent.EXTRA_TEXT, "Please Send Some Information!");
                if(intent.resolveActivity(getPackageManager())!= null)
                    startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_followMe:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitter));
                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
