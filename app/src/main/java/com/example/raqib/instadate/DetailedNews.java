package com.example.raqib.instadate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class DetailedNews extends AppCompatActivity {
    TextView detailedNewsView;
   // private AppData mAppData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String detailedNewsfetched = SlidingFragment.newsBody();
        detailedNewsView = (TextView)findViewById(R.id.detailedNewsView);
        detailedNewsView.setText(detailedNewsfetched);

    }



//    public interface AppData {
//        JSONObject getData(int position);
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mAppData = (AppData) context;
//
//    }
//
//    private void populateDtailedData(){
//        mAppData = null;
//        JSONObject jsonObject = mAppData.getData(getArguments().getInt("position"));
//    }



    /* THIS IS BASICALLY AN EXAMPLE OF A FRAGMENT.... LETS CUT IT FOR NOW */
//     public static class PlaceholderFragment extends Fragment{
//
//         TextView detailedNewsView;
//
//        public PlaceholderFragment(){
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
//
//            String detailedNewsfetched = SlidingFragment.newsBody();
//            detailedNewsView.setText(detailedNewsfetched);
//
//            Log.e("DETAILED NEWS ", detailedNewsfetched);
//            Log.e("CRITICAL POINT 3333 ", "PASSED");
//
//            View rootView=inflater.inflate(R.layout.content_detailed_news,container,false);
//            detailedNewsView = (TextView)rootView.findViewById(R.id.detailedNewsView);
//
//
//            return rootView;
//
//        }
//    }

}

