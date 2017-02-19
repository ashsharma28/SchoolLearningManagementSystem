package com.example.ashishsharma.temporayappname;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ashishsharma.temporayappname.R;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class fullscreenPhoto extends ActionBarActivity {
    ProgressBar progressBar;
    String     url;
    Intent intent;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);

        intent = getIntent();
        url = intent.getStringExtra(SomeAdapter5.EXTRA_MESSAGE1);
        imageView = (ImageView) findViewById(R.id.imageView2);
        progressBar = (ProgressBar) findViewById(R.id.pBar);


        new getImage(imageView, progressBar).execute(url);

        imageView.setImageBitmap(null);



    }

    private class getImage extends AsyncTask<String, Void, Void>
    {
        ImageView img1;
        Bitmap bmp;  ProgressBar pgbar;
        InputStream newsBoyforImage;
        Bitmap resized; String keyForCache;


        public getImage(ImageView img1 , ProgressBar pgbar)
        {
                this.img1 = img1;
                this.pgbar = pgbar;
                Log.i("I reached at:", " GETIMAGE CONSTRUCTOR ");
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgbar.setVisibility(View.VISIBLE);
        }


        @Override
        protected Void doInBackground(String... urls)
        {
            Log.i("I reached at:" , " DO IN BACKGROUND ");

            newsBoyforImage =  downloadUrl(urls[0]);

            bmp = BitmapFactory.decodeStream(newsBoyforImage);


            return null;


        }


        @Override
        protected void onPostExecute(Void result)
        {

            pgbar.setVisibility(View.INVISIBLE);
            img1.setImageBitmap(bmp);

            Log.i("IMAGE", "has been assigned");
        }

        InputStream downloadUrl(String url)
        {
            try
            {

                long httpCacheSize = 5 * 1024 * 1024; // 5 MiB
                File httpCacheDir = new File( getApplicationContext().getCacheDir(), "http");
                Class.forName("android.net.http.HttpResponseCache")
                        .getMethod("install", File.class, long.class)
                        .invoke(null, httpCacheDir, httpCacheSize);

                URL urlMain = new URL(url);

                HttpURLConnection urlConnection = (HttpURLConnection) urlMain.openConnection();
                return urlConnection.getInputStream();
            }
            catch (Exception e)
            {
                String a =  new String("Unable to retrieve web page. ERROR URL may be invalid.");
                byte[] byteArray = a.getBytes() ;
                ByteArrayInputStream sendBackThis = new  ByteArrayInputStream(byteArray);
                return (sendBackThis);
            }

        }


    }

}
