package com.example.ashishsharma.temporayappname;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;


public class NewsViewer extends ActionBarActivity
{
    NewsViewer thisObject = this;
    byte[] byteArray = new byte[0];
    TextView txt1;
    String theText;


   /* String[] imageURLArray = {
            "http://ssl.gstatic.com/onebox/weather/64/sunny.png",
            "http://blackboard.svkm.ac.in/images/ci/logos/Powered_Bb_050.png",
            //"http://www.nmims.edu/images/img3.jpg",
            "http://www.nmims.edu/images/nmims-logo.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/d/d9/Narendra_Damodardas_Modi.jpg"};
*/

        private LruCache<String, Bitmap> mMemoryCache ;
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_viewer);

        txt1 = (TextView) findViewById(R.id.textView123);


        Intent intent = getIntent();
        String fetchedTitleFromExtra = intent.getStringExtra(SomeAdapter3.EXTRA_MESSAGE1);
        Log.i("fetchedTitlFromExtra is" ,fetchedTitleFromExtra );

        //HERE WE ARE FOR SAMPLE USE BRINGING THE POSITION OF THE BUTTON ON
        // EVENT ACTIVITY(THAT IS  CLICKED) AND THEN ACCORDINGLY THE DATA OF
        // IMAGE AND DESCRIPTION IS DOWNLOADED AND POPULATE IT TO THE FRONT END.


        ConnectivityManager cnmgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cnmgr.getActiveNetworkInfo();

        if (info == null) {
            Toast toast = Toast.makeText(this, "No Network Connection!!!", Toast.LENGTH_LONG);
            toast.show();
        } else if (info.isConnected() == false) {
            Toast toast = Toast.makeText(this, "No Network Connection!!!", Toast.LENGTH_LONG);
            toast.show();

        } else {


            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // The cache size will be measured in kilobytes rather than
                    // number of items.
                    return bitmap.getByteCount() / 1024;
                }
            };

            new doDownload().execute(fetchedTitleFromExtra);


            //new getImage(img , position).execute(imageURLArray);
        }
    }







    private class doDownload extends AsyncTask<String, Void, String >
    {

        String theText;

        @Override
        protected String doInBackground(String... urls)
        {
            Log.i("I reached at: " , "1");

            theText = CommomUtilites.getDescription(urls[0]);


            if(theText == null)
                Log.i("Asdswddsdsdd" , "EMPTYTTTTT") ;

            return theText;

        }

        @Override
        protected void onPostExecute(String result){
            txt1.setText(theText);
        }




    }






}



