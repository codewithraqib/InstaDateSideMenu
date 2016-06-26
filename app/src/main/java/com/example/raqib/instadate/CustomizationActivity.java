package com.example.raqib.instadate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CustomizationActivity extends AppCompatActivity {

    ListView list;
    String[] web = {
            "Sports",
            "Technology",
            "Health",
            "Business",
            "Education",
            "University Updates",
            "Politics",
            "Weather"
    };
    Integer[] imageId = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);

        CustomList adapter = new CustomList(CustomizationActivity.this, web, imageId);
        list = (ListView) findViewById(R.id.list);
        final Button SAVE = (Button)findViewById(R.id.SaveButton);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CustomizationActivity.this, "You clicked at" + web[+position], Toast.LENGTH_LONG).show();
            }
        });
    }
    public void SaveChanges(View view){
        Log.i("STATUS", "saving the data");
        AlertDialog.Builder builder=new AlertDialog.Builder(CustomizationActivity.this);
        builder.setMessage("Do you really want to save changes!");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Status", "About to Save the Changes");
                CustomizationActivity.this.finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Status", "cancelling the dialog");
                dialog.cancel();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
