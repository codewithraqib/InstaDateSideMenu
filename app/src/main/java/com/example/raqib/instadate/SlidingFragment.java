package com.example.raqib.instadate;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;


public class SlidingFragment extends Fragment {

    private AppData mAppData;
    DisplayImageOptions options;
    ImageLoader imageLoader;

    static String heading;
    static String body;
    static String imageUrl;
    static String link;


    public interface AppData {
        JSONObject getData(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAppData = (AppData) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.content_main, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateData();
    }

    private void populateData(){


        JSONObject jsonObject = mAppData.getData(getArguments().getInt("position"));
        try {
            heading = jsonObject.getString("heading");
            body = jsonObject.getString("body");
            imageUrl = jsonObject.getString("image_url");
            link = jsonObject.getString("link");

            ((TextView) getView().findViewById(R.id.newsTitle)).setText(heading);
            ((TextView) getView().findViewById(R.id.news)).setText(body);
            ((TextView) getView().findViewById(R.id.moreAtLink)).setText(link);

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext()).build();
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);

            final ImageView image = (ImageView)getView().findViewById(R.id.image);
            final ProgressBar indicator = (ProgressBar)getView().findViewById(R.id.progress);

//            ImageHelper.getInstance().init(getContext());
//            ImageHelper.getInstance().displayImage(imageUrl, (ImageView) getView().findViewById(R.id.image));

            //T0 KEEP THE DOWNLOADED IMAGES IN THE CACHE WE ARE IMPLEMENTING THE BELOW CODE
            options = new DisplayImageOptions.Builder()
                    .cacheInMemory()
                    .cacheOnDisc()
                    .build();

            indicator.setVisibility(View.VISIBLE);
            image.setVisibility(View.INVISIBLE);

            ImageLoadingListener listener = new ImageLoadingListener(){

                @Override
                public void onLoadingStarted(String arg0, View arg1) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onLoadingCancelled(String arg0, View arg1) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                    indicator.setVisibility(View.INVISIBLE);
                    image.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                    // TODO Auto-generated method stub

                }

            };
            imageLoader.displayImage(imageUrl, image,options, listener);



          //ImageHelper.getInstance().displayImage(imageUrl, (ImageView) getView().findViewById(R.id.image));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

        static String newsBody() {
            return body;
    }

}
